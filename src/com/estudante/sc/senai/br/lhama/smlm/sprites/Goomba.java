package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.ArrayList;
import java.util.HashMap;

public class Goomba extends Sprite {

	private boolean spin = false;

	@Override
	public int framesPerFrame() {
		return 10;
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/goomba#2");
		hm.put("dead", "sprites/goomba_die#1");
		hm.put("smoke", "sprites/smoke#4");
		return hm;
	}

	private static String change(Sprite spr) {
		Goomba g = (Goomba) spr;
		if(g.getDead() == 0) {
			return "none";
		} else if(!g.isDead()) {
			return "walk";
		} else if(g.spin) {
			return "smoke";
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
			if (c instanceof Mario && fromTop(c)) {
				if (((Mario) c).isSpinning()) {
					c.setSpeedY(0);
					setDead(25);
					spin = true;
					c.play("spin_stomp");
				} else {
					c.bounce();
					setDead(25);
					c.play("stomp");
				}
			} else {
				c.die();
			}
		}
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if(getDead() > 0) {
			deadMM();
			setSpeedX(0);
		} else if(getDead() == -1){
			setSpeedX(2 * (isFacingRight() ? 1 : -1));
		}
		super.update(lyr, sprs, clouds, () -> setFacingRight(!isFacingRight()));
	}

}
