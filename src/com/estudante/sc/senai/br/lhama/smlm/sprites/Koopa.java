package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Koopa extends Sprite {

	private boolean dead = false;
	private boolean sliding = false;
	private int inShell = 0;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/koopa_walk#2");
		return hm;
	}

	private static String change(Sprite spr) {
		Koopa k = (Koopa) spr;
		if (k.dead) {
			return "none";
		} else if (k.sliding) {
			return "slide";
		} else if (k.inShell == 0) {
			return "idle";
		} else {
			return "walk";
		}
	}

	@Override
	public void collide(Sprite s) {
	}

	@Override
	public void collide(Character c) {
		if (!dead) {
			if (c instanceof Mario && fromTop(c)) {
				if (((Mario) c).isSpinning()) {
					c.setSpeedY(0);
					dead = true;
				} else if (sliding) {
					c.setSpeedY(c.getJumpSpeed() / 2);
				} else if (inShell == 0) {
					inShell = 600;
				}
			} else if (inShell > 0 && !sliding) {
				setFacingRight(fromLeft(c));
				sliding = true;
			}
		}
	}

	@Override
	public int framesPerFrame() {
		return 10;
	}

	public Koopa(double x, double y) {
		super(getPaths(), Koopa::change, "walk", x, y, 48, 72);
	}

	@Override
	public String toString() {
		return "Koopa{" +
				"x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}

}
