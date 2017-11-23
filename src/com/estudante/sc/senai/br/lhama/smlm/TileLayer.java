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
		setName((String) layer.get("name"));
		setOpacity(((Number) layer.get("opacity")).floatValue());
		setVisible((boolean) layer.get("visible"));

		load(layer);
		setAc(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	protected void load(JSONObject layer) {
		JSONArray data = (JSONArray) layer.get("data");
		long w = (long) layer.get("width");
		long h = (long) layer.get("height");

		tiles = new ArrayList<>((int) h);

		for (int i = 0; i < data.size(); i++) {
			int x = (int) Math.floorMod(i, w);
			int y = (int) Math.floorDiv(i, w);
			if(x == 0) {
				tiles.add(y, new ArrayList<>((int) w));
			}
			long val = (Long) data.get(i);
			tiles.get(y).add(x, tileMap.get((int) val));
		}
	}

	@Override
	public void draw(Graphics2D g2d, ZRect rect) {
		if (visible) {
			g2d.setComposite(ac);

			for (int i = 0; i < tiles.size(); i++) {

				ArrayList<ZTile> rows = tiles.get(i);

				for (int j = 0; j < rows.size(); j++) {

					ZTile tile = rows.get(j);

					if (tile != null && intersecting(i, j, rect)) {
						tile.draw(g2d, j, i, tileSize);
					}

					if(SMLM.DEBUG_MODE) {
						new ZRect(j * tileSize, i * tileSize, tileSize).drawBorder(g2d, Color.YELLOW);
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

	private boolean intersecting(int i, int j, ZRect rect) {
		Rectangle r = new Rectangle(j * tileSize, i * tileSize, tileSize, tileSize);
		return r.intersects(rect.getRect());
	}
}
