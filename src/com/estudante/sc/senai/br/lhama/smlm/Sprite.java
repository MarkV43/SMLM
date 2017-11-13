package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Sprite extends ZRect {
	private HashMap<String, ZStrip> animations;
	private String animation;
	private double speedX;
	private double speedY;

	public Sprite(HashMap<String, String> paths, String defaultAnimation, double x, double y, double w, double h) {
		super(x, y, w, h);
		loadAnimations(paths);
		animation = defaultAnimation;
	}

	public Sprite(Sprite sprite) {
		animations = sprite.animations;
		animation = sprite.animation;
		x = sprite.x;
		y = sprite.y;
		w = sprite.w;
		h = sprite.h;
		speedX = sprite.speedX;
		speedY = sprite.speedY;
	}

	public void update(TileLayer lyr, int tileSize) {
		x += speedX;

		collisionX(lyr, tileSize);

		speedY += Level.GRAVITY;

		y += speedY;

		System.out.println(y);

		collisionY(lyr, tileSize);

		animations.get(animation).next();
	}

	private void collisionX(TileLayer lyr, int tileSize) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZRect> range = getRangeX(ts, tileSize);

		range.forEach(tile -> {
			//TODO
		});
	}

	private void collisionY(TileLayer lyr, int tileSize) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZRect> range = getRangeY(ts, tileSize);

		range.forEach(tile -> {
			//TODO
		});
	}

	private ArrayList<ZRect> getRangeX(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int sx = ts.size();
		int sy = ts.get(0).size();

		int x1 = Math.floorDiv((int) this.x, tileSize);
		x1 = range(x1, sx);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		x2 = range(x2, sx);

		int y1 = Math.floorDiv((int) this.y, tileSize);
		y1 = range(y1, sy);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);
		y2 = range(y2, sy);

		ArrayList<ZRect> range = new ArrayList<>();
		for (int y = y1; y <= y2; y++) {
			range.add(new ZRect(x1, y, x1 + tileSize, y + tileSize));
			if(x1 != x2) {
				range.add(new ZRect(x2, y, x2 + tileSize, y + tileSize));
			}
		}

		return range;
	}

	private ArrayList<ZRect> getRangeY(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int sx = ts.size();
		int sy = ts.get(0).size();

		int x1 = Math.floorDiv((int) this.x, tileSize);
		x1 = range(x1, sx);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		x2 = range(x2, sx);

		int y1 = Math.floorDiv((int) this.y, tileSize);
		y1 = range(y1, sy);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);
		y2 = range(y2, sy);

		ArrayList<ZRect> range = new ArrayList<>();
		for (int x = x1; x <= x2; x++) {
			range.add(new ZRect(x, y1, x + tileSize, y1 + tileSize));
			if(y1 != y2) {
				range.add(new ZRect(x, y2, x + tileSize, y2 + tileSize));
			}
		}

		return range;
	}

	private void loadAnimations(HashMap<String, String> paths) {
		animations = new HashMap<>();
		paths.forEach((name, path) -> {
			String[] ps = path.split("#");
			ZStrip strip = new ZStrip(ps[0], Integer.parseInt(ps[1]));

			animations.put(name, strip);
		});
	}

	public void draw(Graphics2D g2d) {
		animations.get(animation).draw(g2d, this);
	}

	private int range(int num, int max) {
		return Math.max(Math.min(num, max), 0);
	}
}
