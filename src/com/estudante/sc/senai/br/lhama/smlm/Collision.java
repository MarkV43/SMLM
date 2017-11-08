package com.estudante.sc.senai.br.lhama.smlm;

/**
 * Created by Marcelo Vogt on 06/08/2017.
 */
public class Collision {

	private int num;

	public Collision(int col) {
		this.num = col;
	}

	public boolean top() {
		return (num & 1) == 1;
	}

	public boolean bottom() {
		return (num & 2) == 2;
	}

	public boolean left() {
		return (num & 4) == 4;
	}

	public boolean right() {
		return (num & 8) == 8;
	}

	public void top(boolean v) {
		if (v && (num & 1) != 1) {
			num += 1;
		} else if (!v && (num & 1) == 1) {
			num -= 1;
		}
	}

	public void bottom(boolean v) {
		if (v && (num & 2) != 2) {
			num += 2;
		} else if (!v && (num & 2) == 2) {
			num -= 2;
		}
	}

	public void left(boolean v) {
		if (v && (num & 4) != 4) {
			num += 4;
		} else if (!v && (num & 4) == 4) {
			num -= 4;
		}
	}

	public void right(boolean v) {
		if (v && (num & 8) != 8) {
			num += 8;
		} else if (!v && (num & 8) == 8) {
			num -= 8;
		}
	}

	@Override
	public String toString() {
		return String.valueOf(num);
	}
}
