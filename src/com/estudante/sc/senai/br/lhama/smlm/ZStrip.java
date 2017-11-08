package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by Marcelo Vogt on 15/10/2017.
 */
public class ZStrip extends ZTileMap {

	private int frame = 0;
	private ZTile[] tiles;

	public ZStrip(String path, int cols) throws InvalidParameterException {
		super(path, cols, 1);
		tiles = new ZTile[cols];
		for (int i = 0; i < cols; i++) {
			tiles[i] = get(i);
		}
	}

	public void next() {
		frame = (frame + 1) % getCols();
	}

	public void draw(Graphics2D g2d, ZRect r) {
		tiles[frame].draw(g2d, r);
	}

}
