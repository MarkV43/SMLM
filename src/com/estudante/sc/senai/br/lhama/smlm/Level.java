package com.estudante.sc.senai.br.lhama.smlm;

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

	public static final double GRAVITY = 1d / 6d;

	private ArrayList<Sprite> sprites;
	private HashMap<String, Layer> layers;
	private ZTileMap tileMap;
	private Character character;
	private int tileSize;

	public Level(String levelName) throws ParserConfigurationException, IOException, SAXException {
		importLevel("levels/" + levelName);

		HashMap<String, String> paths = new HashMap<>();
		paths.put("idle", "characters/idle#3");

		character = new Character(paths, "idle", 0, 0, 48, 96);
	}

	private void importLevel(String levelName) throws ParserConfigurationException, IOException, SAXException {
		JSONObject level = (JSONObject) ZFile.readJSON(levelName.concat(".json"));

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

		JSONArray layers = (JSONArray) level.get("layers");

		this.layers = new HashMap<>(layers.size());

		for (Object lyr : layers) {
			Layer layer = Layer.getInstance(tileMap, (JSONObject) lyr, tileSize);
			this.layers.put(layer.getName(), layer);
		}

	}

	public void update() {
		character.update((TileLayer) layers.get("Camada de Tiles 1"), tileSize);
	}

	public void draw(Graphics2D g2d) {
		layers.forEach(
				(s, layer) -> layer.draw(g2d)
		);
		character.draw(g2d);
	}

}
