package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;
import com.estudante.sc.senai.br.lhama.smlm.characters.Link;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;
import com.estudante.sc.senai.br.lhama.smlm.characters.Megaman;
import com.estudante.sc.senai.br.lhama.smlm.characters.Sonic;
import com.estudante.sc.senai.br.lhama.smlm.sprites.CheckPoint;
import com.estudante.sc.senai.br.lhama.smlm.sprites.Cloud;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Level {

	private HashMap<String, Layer> layers;
	private ZTileMap tileMap;
	private ArrayList<Character> characters;
	private int characterIndex = 0;
	private int tileSize;
	private Camera camera;
	private long width;
	private long height;
	private ArrayList<CheckPoint> checkPoints;
	private ArrayList<Sprite> sprites;
	private int checkpoint = 0;
	public boolean cloudColision = true;
	private double d = 0;

	public Level(String levelName) throws ParserConfigurationException, IOException, SAXException {

		importLevel("levels/" + levelName);

		characters = new ArrayList<>();

		long w = width * SMLM.TILE_SIZE;
		characters.add(0, new Sonic(0,0, w, this));
		characters.add(1, new Mario(0,0, w, this));
		characters.add(2, new Link(0,0, w, this));
		characters.add(3, new Megaman(0,0, w, this));

		setCharacter(getCPCharIndex(), getCheckpoint().getEnergy());
		getCharacter().x = getCheckpoint().x;
		getCharacter().y = getCheckpoint().y;

		camera = new Camera(getLimits(), 0.1, getCharacter().getCenter());

	}

	private void importLevel(String levelName) throws ParserConfigurationException, IOException, SAXException {
		JSONObject level = (JSONObject) ZFile.readJSON(levelName.concat(".json"));

		assert level != null;
		JSONArray tilemaps = (JSONArray) level.get("tilesets");
		JSONObject tilemap = (JSONObject) tilemaps.get(0);
		String tileMapPath = (String) tilemap.get("source");

		String tmpath = tileMapPath.replaceFirst("\\.\\./", "");

		String tileMapContent = ZFile.readFile(tmpath);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(
				new StringReader(tileMapContent)
		));
		doc.getDocumentElement().normalize();

		Element tileset = (Element) doc.getElementsByTagName("tileset").item(0);
		tileSize = Integer.parseInt(tileset.getAttribute("tilewidth"));

		Element img = (Element) doc.getElementsByTagName("image").item(0);

		String tilemapName = img.getAttribute("source").split("\\.")[0];

		tileMap = new ZTileMap(tilemapName, tileSize);

		width = (long) level.get("width");
		height = (long) level.get("height");

		JSONArray layers = (JSONArray) level.get("layers");

		this.layers = new HashMap<>(layers.size());
		checkPoints = new ArrayList<>();

		for (Object lyr : layers) {
			Layer layer = Layer.getInstance(this, tileMap, (JSONObject) lyr, tileSize);
			this.layers.put(layer.getName(), layer);
			if(layer instanceof SpriteLayer) {
				sprites = ((SpriteLayer) layer).getSprites();
				ArrayList<CheckPoint> cps = ((SpriteLayer) layer).getCheckpoints();
				for (CheckPoint cp : cps) {
					cp.setChange(this::setCheckpoint);
					checkPoints.add(cp.getIndex(), cp);
				}
			}
		}
	}

	public boolean update(ZKeyboard kb, ZMouse mouse) {
		TileLayer collisionLayer = (TileLayer) layers.get("Camada de Tiles 1");

		layers.forEach((s, layer) -> {
			if(layer instanceof SpriteLayer) {
				((SpriteLayer) layer).update(collisionLayer, sprites, getCharacter(), kb, cloudColision);
			}
		});

		d += 0.05;
		double dist = Math.cos(d) * 5 + 10;
		boolean hover = getCharacter().update(collisionLayer, sprites, kb, mouse, camera, dist, cloudColision);

		camera.goTo(getCharacter().getCenter());
		changeCharacter(kb);

		return hover;
	}

	private void changeCharacter(ZKeyboard k) {
		Character current = getCharacter();
		if(current.getAnimation().equals("idle") && current.getEnergy() > 0) {
			if(k.B17 && characterIndex != 0) {
				setCharacter(0);
			} else if(k.B28 && characterIndex != 1) {
				setCharacter(1);
			} else if(k.B39 && characterIndex != 2) {
				setCharacter(2);
			} else if(k.B40 && characterIndex != 3) {
				setCharacter(3);
			}
		}
	}

	private void setCharacter(int i) {
		setCharacter(i, getCharacter().getEnergy() - 1);
	}

	/**
	 * @param i
	 *
	 * 0 - Sonic
	 * 1 - Mario
	 * 2 - Link
	 * 3 - Megaman
	 *
	 */
	public void setCharacter(int i, int e) {
		Character c = getCharacter();
		characterIndex = i;
		getCharacter().setLL(c.getLL());
		getCharacter().setEnergy(e);
		getCharacter().setLife(c.getLife());
		getCharacter().setSpeedX(0);
		getCharacter().setSpeedY(0);
		getCharacter().setCoins(c.getCoins());
		getCharacter().setBullets(c.getBullets());
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(new Color(0x3babff));
		g2d.fillRect(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
		g2d.translate(-camera.x, -camera.y);
		layers.forEach(
				(str, layer) -> layer.draw(g2d, camera)
		);

		getCharacter().draw(g2d);

		if(SMLM.DEBUG_MODE) {
			ZPoint c1 = camera.getCenter();
			ZPoint c2 = getCharacter().getCenter();

			g2d.setColor(Color.GRAY);
			g2d.drawLine((int) c1.x, (int) c1.y, (int) c2.x, (int) c2.y);

			int dist = (int) (Math.cos(d) * 5 + 10);
			g2d.setColor(Color.CYAN);
			g2d.drawLine(dist, (int) camera.y, dist, (int) (camera.y + camera.h));
			g2d.drawLine((int) (width * SMLM.TILE_SIZE - dist), (int) camera.y, (int) (width * SMLM.TILE_SIZE - dist), (int) (camera.y + camera.h));
		}
	}

	private int getCPCharIndex() {
		return Character.names.indexOf(getCheckpoint().getCharacter());
	}

	public Character getCharacter() {
		return characters.get(characterIndex);
	}

	public Character getCharacter(int i) {
		return characters.get(i);
	}

	public int getCharacterIndex() {
		return characterIndex;
	}

	public void resetCamera() {
		camera.setCenter(getCharacter().getCenter());
	}

	private ZRect getLimits() {
		ZRect ret = new ZRect();
		ret.w = width * tileSize;
		ret.y = height * tileSize;
		return ret;
	}

	public CheckPoint getCheckpoint() {
		return checkPoints.get(checkpoint);
	}

	public CheckPoint getCheckpoint(int i) {
		return checkPoints.get(i);
	}

	public void setCheckpoint(int i) {
		checkpoint = i;
	}

	public void toggleCloudColision() {
		cloudColision ^= true;
	}

	public boolean isCloudColision() {
		return cloudColision;
	}

	public void reset(int cpIndex) {
		layers.forEach((s, layer) -> {
			if(layer instanceof SpriteLayer)
				((SpriteLayer) layer).reset();
		});
		CheckPoint cp = getCheckpoint(cpIndex);
		Character ch = getCharacter();

		ch.setLL(cp.getLL());
		String chr = cp.getCharacter();
		int i = Character.names.indexOf(chr);
		setCharacter(i, cp.getEnergy());
	}

	public void reset() {
		layers.forEach((s, layer) -> {
			if(layer instanceof SpriteLayer)
				((SpriteLayer) layer).reset();
		});
		CheckPoint cp = getCheckpoint();
		Character ch = getCharacter();

		ch.setLL(cp.getLL());
		String chr = cp.getCharacter();
		int i = Character.names.indexOf(chr);
		setCharacter(i, cp.getEnergy());
		camera.setCenterC(ch.getCenter());
	}
}
