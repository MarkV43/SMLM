package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Sprite;

import java.util.HashMap;

public class CrabBot extends Sprite {

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/goomba#2");
		return hm;
	}

	private static String change(Sprite spr) {
		return "walk";
	}

	public CrabBot(double x, double y) {
		super(getPaths(), CrabBot::change, "walk", x, y, 32, 32);
	}
}
