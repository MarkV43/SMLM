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
//			if (x <= )
		});
	}

	private ArrayList<ZRect> getRangeX(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int x1 = Math.floorDiv((int) this.x, tileSize);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		int y1 = Math.floorDiv((int) this.y, tileSize);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);

		ArrayList<ZRect> range = new ArrayList<>();
		for (int i = y1; i <= y2; i++) {
			for(int j = x1; j <= x2; j++) {
				range.add(new ZRect(j, i, j + tileSize, i + tileSize));
			}
//			range.add(ts.get(x1).get(i));
//			range.add(new ZRect(x1, i, x1 + tileSize, i + tileSize));
//			range.add(ts.get(x2).get(i));
		}

		return range;
	}

	private void collisionY(TileLayer lyr, int tileSize) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZTile> range = getRangeY(ts, tileSize);

		range.forEach(tile -> {

		});
	}

	private ArrayList<ZTile> getRangeY(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int x1 = Math.floorDiv((int) this.x, tileSize);
		int x2 = Math.floorDiv((int) (this.x + this.w), tileSize);
		int y1 = Math.floorDiv((int) this.y, tileSize);
		int y2 = Math.floorDiv((int) (this.y + this.h), tileSize);

		ArrayList<ZTile> range = new ArrayList<>();
		for (int i = x1; i <= x2; i++) {
			range.add(ts.get(i).get(y1));
			range.add(ts.get(i).get(y2));
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
