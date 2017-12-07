package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 03/12/2017.
 */
public class Cloud extends Sprite {

	private Level level;

	@Override
	public boolean falls() {
		return false;
	}

	private static AnimationChanger getAniChanger() {
		return spr -> {
				Cloud c = (Cloud) spr;
				if(c.getLevel().isCloudColision()) {
					return "opaque";
				} else {
					return "translucent";
				}
		};
	}

	private static HashMap<String, String> getPaths(int i) {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("opaque", "sprites/clouds/" + i + "#1");
		hm.put("translucent", "sprites/clouds/" + i + "t#1");
		return hm;
	}

	//ZUtils.toMap("idle", "sprites/clouds/cloud" + i + "#1")

	public Cloud(int i, double x, double y, Level l) {
		super(getPaths(i), getAniChanger(), "opaque", x, y, 64, 64);
		level = l;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	protected boolean collisionX(ArrayList<ZRect> ts) {
		return false;
	}

	@Override
	protected void collisionY(ArrayList<ZRect> ts) {

	}
}
