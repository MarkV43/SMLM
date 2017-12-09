package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.SMLM;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.characters.Sonic;

import java.util.ArrayList;
import java.util.HashMap;

public class BeeBot extends Sprite {

	private static final double PI4 = Math.PI / 2;
	private static final double PI2 = 2 * Math.PI;
	private boolean dead;
	private int radius;
	private int r;
	private double cx;
	private double cy;
	private double angle = 0;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("fly", "sprites/beebot#1");
		return hm;
	}

	private static String change(Sprite spr) {
		if(((BeeBot) spr).isDead()) {
			return "none";
		} else {
			return "fly";
		}
	}

	@Override
	public boolean falls() {
		return false;
	}

	@Override
	public void collide(Character c) {
		if(!dead) {
			if(c instanceof Sonic && fromTop(c)) {
				dead = true;
				c.play("stomp");
			} else {
				c.die();
			}
		}
	}

	public BeeBot(double x, double y, int dist) {
		super(getPaths(), BeeBot::change, "fly", x, y, 84, 60);
		r = dist;
		radius = SMLM.TILE_SIZE * dist;
		cx = x;
		cy = y;
		setFacingRight(true);
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		angle += 0.09;

		x = cx + Math.sin(angle / r) * radius;
		y = cy + Math.sin(angle) * SMLM.TILE_SIZE;

		double rt = (angle / r) % (PI2);
		if(rt >= 3 * PI4) {
			setFacingRight(true);
		} else if(rt >= PI4) {
			setFacingRight(false);
		}

		super.update(lyr, sprs, clouds);
	}

	public boolean isDead() {
		return dead;
	}
}
