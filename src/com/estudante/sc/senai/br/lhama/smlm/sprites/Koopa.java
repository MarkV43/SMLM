package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;

import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Koopa extends Sprite {

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/koopa_walk#2");
		return hm;
	}

	private static String change(Sprite spr) {
		if (spr.getSpeedX() != 0) {
			return "walk";
		}
		return "walk";
	}

	@Override
	public void collide(Sprite s) {
	}

	@Override
	public void collide(Character c) {
		c.damage(1);
	}

	@Override
	public int framesPerFrame() {
		return 10;
	}

	public Koopa(double x, double y) {
		super(getPaths(), Koopa::change, "walk", x, y, 64, 47);
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
