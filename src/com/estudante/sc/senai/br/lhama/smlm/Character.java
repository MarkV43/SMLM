package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.HashMap;

public abstract class Character extends Sprite {

	private double jumpSpeed;
	private boolean left;
	private boolean right;
	private int dir = 0;
	private int termVelocity;
	private double speed;

	private static AnimationChanger getAniChanger() {
		return spr -> {
			if(spr.isOnGround()) {
				if(Math.abs(spr.getSpeedX()) > 0) {
					return "walk";
				} else {
					return "idle";
				}
			} else if(spr.getSpeedY() > 0) {
				return "fall";
			} else {
				return "jump";
			}
		};
	}

	public Character(HashMap<String, String> paths, double x, double y, double w, double h, int termVelocity, double speed, double jumpHeight) {
		super(paths, getAniChanger(), "idle", x, y, w, h);
		jumpSpeed = -(Math.sqrt(2 * SMLM.GRAVITY * SMLM.TILE_SIZE * jumpHeight) + 0.5); // link: 0.15  megaman: 1  sonic: 2  mario: 3
		System.out.println(jumpSpeed);
		this.termVelocity = termVelocity;
		this.speed = speed;
	}

	public boolean update(TileLayer lyr, ZKeyboard kb, ZMouse mouse, Camera c) {
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
		if (kb.SPACE) {
			special();
		}
		super.update(lyr);
		return contains(mouse, c.x, c.y) && mouseOver();
	}

	public abstract void special();

	public boolean mouseOver() {
		// Implementation only on Link. (Easteregg)
		return false;
	}

	public ZPoint getLL() {
		return new ZPoint(x, y + h);
	}

	public void setLL(ZPoint p) {
		x = p.x;
		y = p.y - h;
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
		super.draw(g2d);

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

	//abstract public void mouseOver();

	//abstract public void special();

}
