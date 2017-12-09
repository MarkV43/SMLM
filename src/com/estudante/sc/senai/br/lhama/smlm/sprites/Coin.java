package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Coin extends Sprite {

	@Override
	public boolean falls() {
		return false;
	}

	@Override
	public int framesPerFrame() {
		return 5;
	}

	@Override
	public void collide(Character c) {
		if(!isDead()) {
			setDead(9);
			c.addCoin();
			c.play("coin");
		}
	}

	private static String change(Sprite spr) {
		Coin c = (Coin) spr;
		if(c.getDead() == 0) {
			return "none";
		} else if(!c.isDead()) {
			return "idle";
		} else {
			return "blink";
		}
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("idle", "sprites/coin#4");
		hm.put("blink", "sprites/coin-blink#1");
		return hm;
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if(getDead() > 0) {
			deadMM();
		}
		super.update(lyr, sprs, clouds);
	}

	public Coin(double x, double y) {
		super(getPaths(), Coin::change, "idle", x, y, 64, 64);
	}

}
