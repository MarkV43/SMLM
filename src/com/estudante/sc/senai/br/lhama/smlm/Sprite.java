package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Sprite extends ZRect implements Drawable {
	private HashMap<String, ZStrip> animations;
	private ArrayList<Bullet> bullets;
	private String animation;
	private double speedX;
	private double speedY;
	private boolean onGround;
	private AnimationChanger aniChanger;
	private int frames = 0;
	private boolean facingRight = true;
	private HashMap<String, ZClip> clips;

	public boolean falls() {
		return true;
	}

	public int framesPerFrame() {
		return 1;
	}

	public boolean canShoot() {
		return false;
	}

	public Sprite(HashMap<String, String> paths, AnimationChanger aniChanger, String defaultAnimation, double x, double y, double w, double h) {
		super(x, y, w, h);
		loadAnimations(paths);
		this.aniChanger = aniChanger;
		animation = defaultAnimation;
		bullets = new ArrayList<>();
		clips = new HashMap<>();
	}

	public Sprite(Sprite sprite, double x, double y) {
		animations = sprite.animations;
		animation = sprite.animation;
		aniChanger = sprite.aniChanger;
		this.x = x;
		this.y = y;
		w = sprite.w;
		h = sprite.h;
		speedX = sprite.speedX;
		speedY = sprite.speedY;
	}

	public void add(String name, ZClip clip) {
		clips.put(name, clip);
	}

	public void add(String name, String path) {
		add(name, new ZClip(path));
	}

	public int play(String name) {
		return clips.get(name).play();
	}

	public int loop(String name, int times) {
		return clips.get(name).loop(times);
	}

	public void stop(String name, int i) {
		clips.get(name).stop(i);
	}

	public void addBullet(Bullet b) {
		if (canShoot()) {

			bullets.add(b);
		}
	}

	public void collide(Sprite s) {
	}

	public void collide(Character c) {
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs) {

		ArrayList<ZRect> rs = (ArrayList<ZRect>) ZUtils.<ArrayList<Sprite>, Sprite, ZRect>cast(sprs);

		if (canShoot()) {
			bullets.forEach(bullet -> {
				if (bullet != null) {
					bullet.update(lyr, sprs);
				}
			});
		}

		x += speedX;

		collisionX(lyr);
		collisionX(rs);

		if (falls()) {
			speedY += SMLM.GRAVITY;
			y += speedY;

			collisionY(lyr);
			collisionY(rs);
		}

		frames++;
		if (frames % framesPerFrame() == 0) {
			animations.get(animation).next();
			frames = 0;
		}

		String next = aniChanger.change(this);
		setAnimation(next);

	}

	protected void collisionX(ArrayList<ZRect> ts) {
		ts.forEach(t -> {
			if (intersects(t)) {
				if (x < t.x && x + w > t.x) {
					x = t.x - w;
				} else {
					x = t.x + t.w;
				}
				speedX = 0;
			}
		});
	}

	private void collisionX(TileLayer lyr) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZRect> range = getRangeX(ts, lyr.getTileSize());

		collisionX(range);
	}

	protected void collisionY(ArrayList<ZRect> ts) {
		ts.forEach(t -> {
			if (touching(t) || intersects(t)) {
				if (y < t.y && y + h >= t.y) {
					y = t.y - h;
					onGround = true;
				} else {
					y = t.y + t.h;
				}
				speedY = 0;
			}
		});
	}

	private void collisionY(TileLayer lyr) {
		ArrayList<ArrayList<ZTile>> ts = lyr.getTiles();
		ArrayList<ZRect> range = getRangeY(ts, lyr.getTileSize());

		onGround = false;

		collisionY(range);
	}

	private boolean touching(ZRect t) {
		return (Math.floor(t.y) == Math.floor(y + h) && speedY >= 0)
				&&
				(
					(x < t.x + t.w && x > t.x)
					||
					(x + w > t.x && x + w < t.x + t.w)
					||
					(x <= t.x && x + w >= t.x + t.w)
				);
	}

	@SuppressWarnings("Duplicates")
	private ArrayList<ZRect> getRangeX(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int sy = ts.size();
		int sx = ts.get(0).size();

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
			if (ts.get(y).get(x1) != null) {
				range.add(new ZRect(x1 * tileSize, y * tileSize, tileSize));
			}
			if (x1 != x2 && ts.get(y).get(x2) != null) {
				range.add(new ZRect(x2 * tileSize, y * tileSize, tileSize));
			}
		}

		return range;
	}

	@SuppressWarnings("Duplicates")
	private ArrayList<ZRect> getRangeY(ArrayList<ArrayList<ZTile>> ts, int tileSize) {
		int sy = ts.size();
		int sx = ts.get(0).size();

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
			if (ts.get(y1).get(x) != null) {
				range.add(new ZRect(x * tileSize, y1 * tileSize, tileSize));
			}
			if (y1 != y2 && ts.get(y2).get(x) != null) {
				range.add(new ZRect(x * tileSize, y2 * tileSize, tileSize));
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

	@Override
	public void draw(Graphics2D g2d) {
		drawBullets(g2d);
		if(!animation.equals("none")) {
			ZStrip img = animations.get(animation);
			img.draw(g2d, this, !facingRight);
		}
		if (SMLM.DEBUG_MODE) {
			drawBorder(g2d, Color.RED);
			g2d.setColor(Color.BLACK);
			g2d.drawString(getAnimation(), (float) getX(), (float) (getY() - 5));
		}
	}

	public void drawBullets(Graphics2D g2d) {
		if (canShoot()) {
			bullets.forEach(bullet -> {
				if (bullet != null) {
					bullet.draw(g2d);
				}
			});
		}
	}

	private int range(int num, int max) {
		return Math.max(Math.min(num, max - 1), 0);
	}

	private void setAnimation(String name) {
		if (animations.containsKey(name) && !name.equals(animation)) {
			animations.get(animation).set(0);
			animation = name;
		}
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public String getAnimation() {
		return animation;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public boolean isFacingRight() {
		return facingRight;
	}
}
