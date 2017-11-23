package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.AnimationChanger;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;

import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class KoopaShell extends Sprite {

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("idle", "sprites/koopa_greenshell#1");
		hm.put("run", "sprites/koopa_greenshell_run#8");
		return hm;
	}

	private static AnimationChanger getAniChanger() {
		return spr -> {
			if(spr.getSpeedX() != 0) {
				return "run";
			}
			return "idle";
		};
	}

	public KoopaShell(double x, double y) {
		super(getPaths(), getAniChanger(), "idle", x, y, 64, 47);
	}

}
