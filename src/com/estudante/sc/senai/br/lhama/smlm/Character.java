package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Character extends Sprite {

	private static String[] n = {"sonic", "mario", "link", "megaman"};
	public static List<String> names = Arrays.asList(n);

	private HashMap<String, ZClip> sounds;

	@Override
	public boolean canShoot() {
		return true;
	}

	private double jumpSpeed;
	private boolean left;
	private boolean right;
	private int dir = 0;
	private int termVelocity;
	private double speed;
	private long width;
	private int life = 8;
	private int energy;
	private int coins = 0;
	private int invincibility = 0;
	private Level level;

	@Override
	public int framesPerFrame() {
		return 5;
	}

	public Character(HashMap<String, String> paths, AnimationChanger animationChanger, double x, double y, double w, double h, int termVelocity, double speed, double jumpHeight, long width, Level l) {
		super(paths, animationChanger, "idle", x, y, w, h);
		jumpSpeed = -(Math.sqrt(2 * SMLM.GRAVITY * SMLM.TILE_SIZE * jumpHeight) + 0.5); // link: 0.15  megaman: 1  sonic: 2  mario: 3
		this.termVelocity = termVelocity;
		this.speed = speed;
		this.width = width;
		level = l;
		sounds = new HashMap<>();
		add("stomp", "smw_stomp");
		add("jump", "smw_jump");
		add("damage", "smw_pipe");
		add("coin", "smw_coin");

	}

	public void addCoin() {
		coins++;
	}

	public int getCoins() {
		return coins;
	}

	public boolean update(TileLayer lyr, ArrayList<Sprite> sprites, ZKeyboard kb, ZMouse mouse, Camera c, double dist, boolean clouds) {

		invincibility = Math.max(invincibility - 1, 0);
		double change = dir * speed;
		if (Math.signum(change) == -Math.signum(getSpeedX())) {
			change *= 3d / 4d;
		}
		if (!isOnGround() && Math.signum(getSpeedX()) == dir) {
			change *= 2d / 3d;
		}
		setSpeedX(getSpeedX() + change);
		if (Math.abs(getSpeedX()) > termVelocity) {
			setSpeedX(Math.signum(getSpeedX()) * termVelocity);
		}
		if (Math.abs(getSpeedX()) < 0.2) {
			setSpeedX(0);
		}
		if (isOnGround()) {
			if (!(kb.A || kb.D)) {
				setSpeedX(getSpeedX() * 0.8);
			}
			if (!kb.pW && kb.W) {
				setSpeedY(jumpSpeed);
				play("jump");
			}
		}
		setLR(kb);
		special(kb.pSPACE, kb.SPACE);

		super.update(lyr, sprites, clouds);

		//double dist = Math.cos(d += 0.05) * 5 + 10;
		if (x < dist) {
			x = dist;
			setSpeedX(0);
		} else if (x + w > width - dist) {
			x = width - dist - w;
			setSpeedX(0);
		}

		for (Sprite spr : sprites) {
			if (spr.canShoot()) {
				ArrayList<Bullet> bullets = spr.getBullets();
				for (int j = 0; j < bullets.size(); j++) {
					Bullet b = bullets.get(j);
					if (b != null && intersects(b)) {
						if (vulnerable()) {
							bullets.set(j, null);
							die();
						}
					}
				}
			}
		}
		return contains(mouse, c.x, c.y) && mouseOver();
	}

	public void change(String name) {
		change(name, energy - 1);
	}

	public void change(String name, int energy) {
		level.setCharacter(names.indexOf(name), energy);
	}

	public abstract void special(boolean prev, boolean space);

	public boolean mouseOver() {
		// Implementation only on Link. (Easteregg)
		return false;
	}

	private boolean contains(ZPoint p, double x, double y) {
		ZRect t = new ZRect(this);
		t.x -= x;
		t.y -= y;
		return p.in(t);
	}

	private void setLR(ZKeyboard kb) {
		if (kb.A && kb.D) {
			if (!left || !right) {
				dir *= -1;
				setFacingRight(!isFacingRight());
			}
		} else {
			if (kb.A) {
				dir = -1;
				setFacingRight(false);
			} else if (kb.D) {
				dir = 1;
				setFacingRight(true);
			} else {
				dir = 0;
			}
		}

		left(kb.A);
		right(kb.D);
	}

	public void die() {
		if(vulnerable()) {
			if (life > 0) {
				play("damage");
			} else {
				play("death");
			}
			setLife(life - 1);
			invincibility = 120;
		}
	}

	public boolean vulnerable() {
		return invincibility == 0;
	}

	@Override
	public void draw(Graphics2D g2d) {
		int i = Math.floorDiv(invincibility, 4) % 2;

		if(i != 0) {
			drawBullets(g2d);
		} else {
			super.draw(g2d);
		}

		if(SMLM.DEBUG_MODE && invincibility != 0) {
			g2d.drawString(String.valueOf(invincibility), (int) x, (int) y - 15);
		}
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Level getLevel() {
		return level;
	}

	public boolean left() {
		return left;
	}

	public void left(boolean left) {
		this.left = left;
	}

	public boolean right() {
		return right;
	}

	public void right(boolean right) {
		this.right = right;
	}

	public double getJumpSpeed() {
		return jumpSpeed;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = ZMath.limit(life, 0, 8);
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = ZMath.limit(energy, 0, 16);
	}

	public void damage(int amount) {
		if(invincibility == 0) {
			setLife(life - amount);
			invincibility = 120;
		}
	}

	public int getTermVelocity() {
		return termVelocity;
	}

	public void setTermVelocity(int termVelocity) {
		this.termVelocity = termVelocity;
	}

	@Override
	public String toString() {
		return "Character{" +
				"life=" + life +
				", energy=" + energy +
				", invincibility=" + invincibility +
				", x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}

	public void bounce() {
		setSpeedY(-7.8394663844);
	}

	public void bounce(double plus) {
		setSpeedY(-7.8394663844 + plus);
	}

	public void setInvincibility(int n) {
		invincibility = n;
	}

	public int getInvincibility() {
		return invincibility;
	}
}
