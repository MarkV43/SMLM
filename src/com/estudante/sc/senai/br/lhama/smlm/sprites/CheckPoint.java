package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Sprite;

import java.util.HashMap;

public class CheckPoint extends Sprite {

	private int index;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("swing", "sprites/flag#1");
		return hm;
	}

	public static String change(Sprite spr) {
		return "swing";
	}


	public CheckPoint(double x, double y) {
		super(getPaths(), CheckPoint::change, "swing", x, y, 32, 32);
	}

	public CheckPoint(Sprite cp, double x, double y, int num) {
		super(cp, x, y);
		index = num;
	}

	public int getIndex() {
		return index;
	}
}
