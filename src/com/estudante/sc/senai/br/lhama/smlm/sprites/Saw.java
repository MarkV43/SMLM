package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZUtils;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

public class Saw extends Sprite {

	@Override
	public boolean falls() {
		return false;
	}

	private static String change(Sprite spr) {
		return "spin";
	}

	@Override
	public void collide(Character c) {
		if (c instanceof Mario && ((Mario) c).isSpinning()) {
			if (c.y + c.h <= y + h / 2) {
				if(c.getSpeedY() > -5) {
					c.setSpeedY(c.getJumpSpeed());
					c.play("spin_bounce");
				}
			} else {
				c.die();
			}
		} else {
			c.die();
		}
	}

	public Saw(double x, double y) {
		super(ZUtils.toMap("spin", "sprites/saw#2"), Saw::change, "spin", x, y, 128, 128);
	}

}
