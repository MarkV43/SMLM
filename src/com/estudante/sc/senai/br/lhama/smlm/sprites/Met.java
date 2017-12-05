package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Sprite;

import java.util.HashMap;

public class Met extends Sprite {

	private boolean dead;

	private int shoot;
	private int sneaking;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("shoot", "sprites/met_shoot#2");
		hm.put("walk", "sprites/met_walk#2");
		hm.put("sneak", "sprites/met_sneak#1");
		hm.put("die", "sprites/met_sneak#1");
		return hm;
	}

	private static String change(Sprite spr) {
		Met g = (Met) spr;
		if(g.isDead()) {
			return "dead";
		} else if(g.isShooting()) {
			return "shoot";
		} else if(g.isSneaking()) {
			return "sneak";
		}
		return "walk";
	}

	public Met(double x, double y) {
		super(getPaths(), Met::change, "walk", x, y, 32, 32);
	}

	public boolean isSneaking() {
		return sneaking < 0;
	}

	public boolean isShooting() {
		return shoot < 0;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
