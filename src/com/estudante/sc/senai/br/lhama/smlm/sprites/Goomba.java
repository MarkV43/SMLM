package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.AnimationChanger;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZUtils;

import java.util.HashMap;

public class Goomba extends Sprite {

	private boolean dead = false;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/goomba#2");
		hm.put("die", "sprites/goomba_die#1");
		return hm;
	}

	private static String change(Sprite spr) {
		Goomba g = (Goomba) spr;
		if(g.isDead()) {
			return "dead";
		}
		return "walk";
	}

	public Goomba(double x, double y) {
		super(getPaths(), Goomba::change, "walk", x, y, 32, 32);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}