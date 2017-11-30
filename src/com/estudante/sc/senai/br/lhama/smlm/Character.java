package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Character extends Sprite {

	private String[] n = {"sonic", "mario", "link", "megaman"};
	private List<String> names = Arrays.asList(n);

	private double jumpSpeed;
	private boolean left;
	private boolean right;
	private int dir = 0;
	private int termVelocity;
	private double speed;
	private long width;
	private int life = 8;
	private int energy;
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
	}

	public boolean update(TileLayer lyr, ZKeyboard kb, ZMouse mouse, Camera c, double dist) {
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
			if (kb.W) {
				setSpeedY(jumpSpeed);
			}
		}
		setLR(kb);
		special(kb.SPACE);

		super.update(lyr);

		//double dist = Math.cos(d += 0.05) * 5 + 10;
		if (x < dist) {
			x = dist;
			setSpeedX(0);
		} else if (x + w > width - dist) {
			x = width - dist - w;
			setSpeedX(0);
		}
		return contains(mouse, c.x, c.y) && mouseOver();
	}

	public void change(String name) {
		level.setCharacter(names.indexOf(name));
	}

	public abstract void special(boolean space);

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
			}
		} else {
			if (kb.A) {
				dir = -1;
			} else if (kb.D) {
				dir = 1;
			} else {
				dir = 0;
			}
		}

		left(kb.A);
		right(kb.D);
	}

	@Override
	public void draw(Graphics2D g2d) {
		int i = Math.floorDiv(invincibility, 4) % 2;
		Graphics2D g = (Graphics2D) g2d.create();
		if(i != 0) {
			g.setXORMode(new Color(0, 0, 0, 255));
		}
		super.draw(g);
		if(SMLM.DEBUG_MODE && invincibility != 0) {
			g2d.drawString(String.valueOf(invincibility), (int) x, (int) y - 15);
		}
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
}
