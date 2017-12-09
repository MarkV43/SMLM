package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.characters.Sonic;

import java.util.ArrayList;
import java.util.HashMap;

public class CrabBot extends Sprite {

	private boolean dead = false;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/crab_bot#20");
		return hm;
	}

	@Override
	public int framesPerFrame() {
		return 3;
	}

	@Override
	public void collide(Sprite s) {
	}

	@Override
	public void collide(Character c) {
		if(!dead) {
			if(c instanceof Sonic && fromTop(c)) {
				dead = true;
				c.bounce();
				play("stomp");
			} else {
				c.die();
			}
		}
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if(!dead) {
			setSpeedX(3 * (isFacingRight() ? 1 : -1));
		}
		super.update(lyr, sprs, clouds, () -> setFacingRight(!isFacingRight()));
	}

	private static String change(Sprite spr) {
		if(((CrabBot) spr).isDead()) {
			return "none";
		} else {
			return "walk";
		}
	}

	public CrabBot(double x, double y) {
		super(getPaths(), CrabBot::change, "walk", x, y, 64, 48);
		add("stomp", "smw_stomp");
	}

	public boolean isDead() {
		return dead;
	}
}
