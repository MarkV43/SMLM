package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.characters.Link;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;
import com.estudante.sc.senai.br.lhama.smlm.characters.Megaman;
import com.estudante.sc.senai.br.lhama.smlm.characters.Sonic;
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

	public Level(String levelName) throws ParserConfigurationException, IOException, SAXException {
		importLevel("levels/" + levelName);

		sprites = new ArrayList<>();

		characters = new ArrayList<>();

		characters.add(0, new Sonic(0,0));
		characters.add(1, new Mario(0,0));
		characters.add(2, new Link(0,0));
		characters.add(3, new Megaman(0,0));

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

		for (Object lyr : layers) {
			Layer layer = Layer.getInstance(tileMap, (JSONObject) lyr, tileSize);
			this.layers.put(layer.getName(), layer);
		}

	}

	public void update(ZKeyboard kb, ZMouse mouse) {
		getCharacter().update((TileLayer) layers.get("Camada de Tiles 1"), kb, mouse);
		camera.goTo(getCharacter().getCenter());

		changeCharacter(kb);
	}

	public void changeCharacter(ZKeyboard k) {
		if(k.B17 && characterIndex != 0) {
			int newIndex = 0;
			getCharacter(newIndex).setLL(getCharacter().getLL());
			characterIndex = newIndex;
		} else if(k.B28 && characterIndex != 1) {
			int newIndex = 1;
			getCharacter(newIndex).setLL(getCharacter().getLL());
			characterIndex = newIndex;
		} else if(k.B39 && characterIndex != 2) {
			int newIndex = 2;
			getCharacter(newIndex).setLL(getCharacter().getLL());
			characterIndex = newIndex;
		} else if(k.B40 && characterIndex != 3) {
			int newIndex = 3;
			getCharacter(newIndex).setLL(getCharacter().getLL());
			characterIndex = newIndex;
		}
	}

	public void draw(Graphics2D g2d) {
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
		}
	}

	public Character getCharacter() {
		return characters.get(characterIndex);
	}

	public Character getCharacter(int i) {
		return characters.get(i);
	}

	private ZRect getLimits() {
		ZRect ret = new ZRect();
		ret.w = width * tileSize;
		ret.y = height * tileSize;
		return ret;
	}

}
