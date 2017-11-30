package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.HashMap;

public class Sonic extends Character {

	private int dashing = 0;

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/idle#3");
	    paths.put("fall", "characters/fall#3");
	    paths.put("jump", "characters/jump#3");
	    paths.put("walk", "characters/walk#3");
        return paths;
    }

	@SuppressWarnings("Duplicates")
	private static AnimationChanger getAniChanger() {
		return spr -> {
			if (spr.isOnGround()) {
				if (Math.abs(spr.getSpeedX()) > 1.2) {
					return "walk";
				} else {
					return "idle";
				}
			} else if (spr.getSpeedY() > 0) {
				return "fall";
			} else {
				return "jump";
			}
		};
	}

	@Override
	public boolean update(TileLayer lyr, ZKeyboard kb, ZMouse mouse, Camera c, double dist) {
    	if(isDashing()) {
    		setTermVelocity(30);
    		setSpeedX(30);
    	    dashing--;
	    } else {
    		setTermVelocity(20);
	    }
		return super.update(lyr, kb, mouse, c, dist);
	}

	public Sonic(double x, double y, long w, Level l) {
        super(getPaths(), getAniChanger(), x, y, 48, 96, 20, 1.2, 2, w, l);
    }

	@Override
	public void special(boolean space) {
		if(space && !isDashing()) {
			dashing = 30;
		}
	}

	public boolean isDashing() {
		return dashing != 0;
	}
}
