package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Koopa extends Sprite {

	private int sliding = -1;
	private int inShell = 0;
	private boolean spin = false;

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("walk", "sprites/koopa_walk#2");
		hm.put("idle", "sprites/koopa_shell#1");
		hm.put("slide", "sprites/koopa_slide#1");
		hm.put("smoke", "sprites/smoke#4");
		return hm;
	}

	private static String change(Sprite spr) {
		Koopa k = (Koopa) spr;
		if (k.getDead() == 0) {
			return "none";
		} else if(k.isDead()) {
			return "smoke";
		} else if (k.sliding >= 0) {
			return "slide";
		} else if (k.inShell == 0) {
			return "walk";
		} else {
			return "idle";
		}
	}

	@Override
	public void collide(Character c) {
		if (!isDead()) {
			if (c instanceof Mario && fromTop(c)) {
				if (((Mario) c).isSpinning()) {
					c.setSpeedY(0);
					setDead(25);
					spin = true;
					c.play("spin_stomp");
				} else if (sliding >= 0) {
					c.bounce();
					sliding = -1;
					play("stomp");
				} else if (inShell == 0) {
					c.bounce();
					inShell = 600;
					c.play("stomp");
				}
			} else if (inShell > 0 && sliding  == -1 && c.getSpeedY() >= 0) {
				setFacingRight(fromLeft(c));
				sliding = 20;
				play("kick");
			} else if(sliding == 0 || inShell == 0) {
				c.die();
			}
		}
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if(getDead() > 0) {
			deadMM();
		}
		if(!isDead()) {
			int mult;
			if (sliding >= 0) {
				mult = 13;
				if (sliding != 0) {
					sliding--;
				}
			} else if (inShell == 0) {
				mult = 2;
			} else {
				mult = 0;
				inShell--;
			}
			setSpeedX(mult * (isFacingRight() ? 1 : -1));
		} else {
			setSpeedX(0);
		}
		super.update(lyr, sprs, clouds, () -> setFacingRight(!isFacingRight()));

	}

	@Override
	public int framesPerFrame() {
		return 10;
	}

	public Koopa(double x, double y) {
		super(getPaths(), Koopa::change, "walk", x, y, 48, 72);
		add("kick", "smw_kick");
	}

	@Override
	public String toString() {
		return "Koopa{" +
				"x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}

}
