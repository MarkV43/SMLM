package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.HashMap;

public class Character extends Sprite {

	private double jumpSpeed;
	private boolean left;
	private boolean right;
	private int dir = 0;
	private int termVelocity;
	private double speed;

	public Character(HashMap<String, String> paths, AnimationChanger aniChanger, String defaultAnimation, double x, double y, double w, double h, int termVelocity, double speed, double jumpHeight) {
		super(paths, aniChanger, defaultAnimation, x, y, w, h);
		jumpSpeed = -(Math.sqrt(2 * SMLM.GRAVITY * SMLM.TILE_SIZE * jumpHeight) + 0.5); // link: 0.15  megaman: 1  sonic: 2  mario: 3
		System.out.println(jumpSpeed);
		this.termVelocity = termVelocity;
		this.speed = speed;
	}

	public void update(TileLayer lyr, ZKeyboard kb, ZMouse mouse) {
		double change = dir * speed;
		if(Math.signum(change) == -Math.signum(getSpeedX())) {
			change *= 3d / 4d;
		}
		if(!isOnGround() && Math.signum(getSpeedX()) == dir) {
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
			//special();
		}
		if (mouse.in(this)) {
//			mouseOver();
		}
		super.update(lyr);
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
		if (SMLM.DEBUG_MODE) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("velX: " + getSpeedX(), 5, 15);
			g2d.drawString("velY: " + getSpeedY(), 5, 30);
			g2d.drawString("onGr: " + isOnGround(), 5, 45);
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

	//abstract public void mouseOver();

	//abstract public void special();

}
