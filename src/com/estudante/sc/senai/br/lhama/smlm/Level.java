package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;
import com.estudante.sc.senai.br.lhama.smlm.characters.Link;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;
import com.estudante.sc.senai.br.lhama.smlm.characters.Megaman;
import com.estudante.sc.senai.br.lhama.smlm.characters.Sonic;
import com.estudante.sc.senai.br.lhama.smlm.sprites.CheckPoint;
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

public class Level {

	private ArrayList<Sprite> sprites;
	private HashMap<String, Layer> layers;
	private ZTileMap tileMap;
	private ArrayList<Character> characters;
	private int characterIndex = 0;
	private int tileSize;
	private Camera camera;
	private long width;
	private long height;
	private ArrayList<CheckPoint> checkPoints;
	private int checkpoint = 0;
	private double d = 0;
	private int energy;
	private int maxEnergy;
	private int lives;
	private int maxLives;

	public Level(String levelName) throws ParserConfigurationException, IOException, SAXException {
		sprites = new ArrayList<>();

		importLevel("levels/" + levelName);


		characters = new ArrayList<>();

		long w = width * SMLM.TILE_SIZE;
		characters.add(0, new Sonic(0,0, w));
		characters.add(1, new Mario(0,0, w));
		characters.add(2, new Link(0,0, w));
		characters.add(3, new Megaman(0,0, w));

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
			Layer layer = Layer.getInstance(tileMap, (JSONObject) lyr, tileSize);
			this.layers.put(layer.getName(), layer);
			if(layer instanceof SpriteLayer) {
				ArrayList<CheckPoint> cps = ((SpriteLayer) layer).getCheckpoints();
				for (CheckPoint cp : cps) {
					checkPoints.add(cp.getIndex(), cp);
				}
			}
		}
	}

	public boolean update(ZKeyboard kb, ZMouse mouse) {

		TileLayer collisionLayer = (TileLayer) layers.get("Camada de Tiles 1");

		layers.forEach((s, layer) -> {
			if(layer instanceof SpriteLayer) {
				((SpriteLayer) layer).update(collisionLayer);
			}
		});

		d += 0.05;
		double dist = Math.cos(d) * 5 + 10;
		boolean hover = getCharacter().update(collisionLayer, kb, mouse, camera, dist);

		camera.goTo(getCharacter().getCenter());
		changeCharacter(kb);

		return hover;
	}

	public void changeCharacter(ZKeyboard k) {
		Character current = getCharacter();
		if(current.getAnimation().equals("idle")) {
			ZPoint ll = current.getLL();
			if(k.B17 && characterIndex != 0) {
				int newIndex = 0;
				getCharacter(newIndex).setLL(ll);
				characterIndex = newIndex;
			} else if(k.B28 && characterIndex != 1) {
				int newIndex = 1;
				getCharacter(newIndex).setLL(ll);
				characterIndex = newIndex;
			} else if(k.B39 && characterIndex != 2) {
				int newIndex = 2;
				getCharacter(newIndex).setLL(ll);
				characterIndex = newIndex;
			} else if(k.B40 && characterIndex != 3) {
				int newIndex = 3;
				getCharacter(newIndex).setLL(ll);
				characterIndex = newIndex;
			}
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(new Color(0x3babff));
		g2d.fillRect(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
		g2d.translate(-camera.x, -camera.y);
		layers.forEach(
				(str, layer) -> layer.draw(g2d, camera)
		);
		sprites.forEach(
				s -> s.draw(g2d)
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

	public Character getCharacter() {
		return characters.get(characterIndex);
	}

	public Character getCharacter(int i) {
		return characters.get(i);
	}

	public int getCharacterIndex() {
		return characterIndex;
	}

	private ZRect getLimits() {
		ZRect ret = new ZRect();
		ret.w = width * tileSize;
		ret.y = height * tileSize;
		return ret;
	}

	private CheckPoint getCheckpoint() {
		return checkPoints.get(checkpoint);
	}

	private CheckPoint getCheckpoint(int i) {
		return checkPoints.get(i);
	}

	private void retry() {
		getCharacter();
	}

}
