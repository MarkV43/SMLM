package com.estudante.sc.senai.br.lhama.smlm;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 14/10/2017.
 */
public class ZTileMap extends ZImage {

	private int cols;
	private int rows;
	private int width;
	private int height;

	private ArrayList<ArrayList<ZTile>> tiles;

	public ZTileMap(String name, int cols, int rows) throws InvalidParameterException {
		super(name.concat(".png"));

		if(getWidth() % cols != 0 || getHeight() % rows != 0) {
			throw new InvalidParameterException("Parameters given are invalid");
		}

		this.cols = cols;
		this.rows = rows;
		width = getWidth() / cols;
		height = getHeight() / rows;

		setTiles();
	}

	public ZTileMap(String path, int tileSize) {
		super("tilemaps/" + path.concat(".png"));

		if(getWidth() % tileSize != 0 || getHeight() % tileSize != 0) {
			throw new InvalidParameterException("Parameters given are invalid");
		}

		this.cols = getWidth() / tileSize;
		this.rows = getHeight() / tileSize;
		width = height = tileSize;

		setTiles();
	}

	private void setTiles() {
		tiles = new ArrayList<>(rows);

		for (int i = 0; i < rows; i++) {
			ArrayList<ZTile> row = new ArrayList<>(cols);

			for (int j = 0; j < cols; j++) {
				row.add(j, get(j, i));
			}

			tiles.add(i, row);
		}
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	private ZTile get(int x, int y) {
		if(x < cols && y < rows && x >= 0 && y >= 0) {
			return new ZTile(getImage(), x, y, width, height);
		} else {
			throw new InvalidParameterException("Tilemap does not contain position solicited");
		}
	}

	public ZTile get(int index) {
		if(this.getClass().equals(ZTileMap.class)) {
			index--;
		}
		if(index == -1) {
			return null;
		}

		int x = Math.floorMod(index, cols);
		int y = Math.floorDiv(index, cols);

		return tiles.get(y).get(x);
	}

}
