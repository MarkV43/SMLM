package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Coin extends Sprite {

	private int captured = -1;
	private ZClip sound;

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
		if(!isCaptured()) {
			captured = 9;
			c.addCoin();
		}
	}

	private static String change(Sprite spr) {
		Coin c = (Coin) spr;
		if(c.getCaptured() == 0) {
			return "none";
		} else if(c.getCaptured() == -1) {
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

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs) {
		if(captured > 0) {
			captured--;
			play("collect");
		}
		super.update(lyr, sprs);
	}

	public Coin(double x, double y) {
		super(getPaths(), Coin::change, "idle", x, y, 64, 64);
		add("collect", "smw_coin");
	}

	public int getCaptured() {
		return captured;
	}

	public boolean isCaptured() {
		return captured != -1;
	}
}
