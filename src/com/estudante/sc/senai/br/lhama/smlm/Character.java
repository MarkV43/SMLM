package com.estudante.sc.senai.br.lhama.smlm;

import java.util.HashMap;

public class Character extends Sprite {

	private double jumpSpeed;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	public Character(HashMap<String, String> paths, String defaultAnimation, double x, double y, double w, double h) {
		super(paths, defaultAnimation, x, y, w, h);
		jumpSpeed = -Math.sqrt(2 * SMLM.GRAVITY * SMLM.TILE_SIZE * jumpSpeed) / 30;
	}

	public boolean up() {
		return up;
	}

	public void up(boolean up) {
		this.up = up;
	}

	public boolean down() {
		return down;
	}

	public void down(boolean down) {
		this.down = down;
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

}
