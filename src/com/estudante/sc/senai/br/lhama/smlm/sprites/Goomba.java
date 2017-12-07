package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.ArrayList;
import java.util.HashMap;

public class Goomba extends Sprite {

	private int dead = -1;

	@Override
	public int framesPerFrame() {
		return 10;
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/goomba#2");
		hm.put("dead", "sprites/goomba_die#1");
		return hm;
	}

	private static String change(Sprite spr) {
		Goomba g = (Goomba) spr;
		if(g.dead == 0) {
			return "none";
		} else if(g.dead == -1) {
			return "walk";
		} else {
			return "dead";
		}
	}

	public Goomba(double x, double y) {
		super(getPaths(), Goomba::change, "walk", x, y, 64, 64);
	}

	@Override
	public void collide(Character c) {
		if(!isDead()) {
			if (c instanceof Mario && c.y + c.h <= y + c.getSpeedY()) {
				if (((Mario) c).isSpinning()) {
					c.setSpeedY(0);
					dead = 25;
					c.play("spin_stomp");
				} else {
					c.setSpeedY(-13);
					dead = 25;
					c.play("stomp");
				}
			} else {
				c.die();
			}
		}
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs) {
		if(dead > 0) {
			dead--;
			setSpeedX(0);
		} else if(dead == -1){
			setSpeedX(2 * (isFacingRight() ? 1 : -1));
		}
		super.update(lyr, sprs, () -> setFacingRight(!isFacingRight()));
	}

	public boolean isDead() {
		return dead != -1;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}
}
