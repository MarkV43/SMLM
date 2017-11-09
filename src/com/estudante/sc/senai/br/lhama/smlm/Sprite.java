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
		ArrayList<ZRect> range = getRangeX(tileSize);

		range.forEach(t -> {
			if(ts.get(Math.floorDiv((int) t.x, tileSize)).get(Math.floorDiv((int) t.y, tileSize)) != null) {
				if (x <= t.x + t.w && x <= t.x) {
					x = t.x + t.w;
				} else if (x + w >= t.x && x + w <= t.x + t.w) {
					x = t.x - w;
				}
			}
		});
	}

	private ArrayList<ZRect> getRangeX(int tileSize) {
		int x1 = Math.floorDiv((int) this.x, tileSize);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		int y1 = Math.floorDiv((int) this.y, tileSize);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);

		ArrayList<ZRect> range = new ArrayList<>();
		for (int i = y1; i <= y2; i++) {
			for(int j = x1; j <= x2; j++) {
				range.add(new ZRect(j * tileSize, i * tileSize, (j + 1) * tileSize, (i + 1) * tileSize));
			}
		}

		return range;
	}

	private void collisionY(TileLayer lyr, int tileSize) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZRect> range = getRangeY(tileSize);

		range.forEach(t -> {
			if(ts.get(Math.floorDiv((int) t.x, tileSize)).get(Math.floorDiv((int) t.y, tileSize)) != null) {
				if (y <= t.y + t.h && y <= t.y) {
					y = t.y + t.h;
				} else if (y + h >= t.y && y + h <= t.y + t.h) {
					y = t.y - h;
				}
			}
		});
	}

	private ArrayList<ZRect> getRangeY(int tileSize) {
		int x1 = Math.floorDiv((int) this.x, tileSize);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		int y1 = Math.floorDiv((int) this.y, tileSize);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);

		ArrayList<ZRect> range = new ArrayList<>();
		for (int i = x1; i <= x2; i++) {
			for(int j = y1; j <= y2; j++) {
				range.add(new ZRect(i * tileSize, j * tileSize, (i + 1) * tileSize, (j + 1) * tileSize));
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
}
