package com.estudante.sc.senai.br.lhama.smlm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 12/10/2017.
 */
public class TileLayer extends Layer {

	private ZTileMap tileMap;
	private int tileSize;
	private ArrayList<ArrayList<ZTile>> tiles;

	public TileLayer(ZTileMap tileMap, JSONObject layer, int tileSize) {
		this.tileSize = tileSize;
		this.tileMap = tileMap;
		name = (String) layer.get("name");
		opacity = ((Number) layer.get("opacity")).floatValue();
		visible = (boolean) layer.get("visible");
		collision = !layer.containsKey("collision") || (boolean) layer.get("collision");

		load(layer);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
	}

	private void load(JSONObject layer) {
		JSONArray data = (JSONArray) layer.get("data");
		long w = (long) layer.get("width");
		long h = (long) layer.get("height");

		tiles = new ArrayList<>();

		for (int i = 0; i < data.size(); i++) {
			int x = (int) Math.floorMod(i, w);
			int y = (int) Math.floorDiv(i, h);
			if(x == 0) {
				tiles.add(y, new ArrayList<>());
			}
			long val = (Long) data.get(i);

			tiles.get(y).add(x, tileMap.get((int) val));
		}
	}

	public void draw(Graphics2D g2d) {
		if (visible) {
			g2d.setComposite(ac);

			for (int i = 0; i < tiles.size(); i++) {

				ArrayList<ZTile> rows = tiles.get(i);

				for (int j = 0; j < rows.size(); j++) {

					ZTile tile = rows.get(j);

					if (tile != null) {
						tile.draw(g2d, j, i, tileSize);
					}
				}
			}
		}
	}

	public ArrayList<ArrayList<ZTile>> getTiles() {
		return tiles;
	}

	public int getTileSize() {
		return tileSize;
	}
}
