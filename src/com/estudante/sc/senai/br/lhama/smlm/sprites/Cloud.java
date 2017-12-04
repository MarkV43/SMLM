package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZRect;
import com.estudante.sc.senai.br.lhama.smlm.ZUtils;

import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 03/12/2017.
 */
public class Cloud extends Sprite {

	@Override
	public boolean falls() {
		return false;
	}

	public Cloud(int i, double x, double y) {
		super(ZUtils.toMap("idle", "sprites/clouds/cloud" + i + "#1"), spr -> "idle", "idle", x, y, 64, 64);
	}

	@Override
	protected void collisionX(ArrayList<ZRect> ts) {

	}

	@Override
	protected void collisionY(ArrayList<ZRect> ts) {

	}
}
