package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.ArrayList;
import java.util.HashMap;

public class Sonic extends Character {

	private int dashing = 0;

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/sonic/idle#6");
	    paths.put("fall", "characters/sonic/fall#1");
	    paths.put("jump", "characters/sonic/jump#3");
	    paths.put("walk", "characters/sonic/walk#8");
	    paths.put("special", "characters/sonic/special#7");
        return paths;
    }

	@SuppressWarnings("Duplicates")
	private static AnimationChanger getAniChanger() {
		return spr -> {
			Sonic s = (Sonic) spr;
			if(s.isDashing()) {
				return "special";
			} else if (spr.isOnGround()) {
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
	public boolean update(TileLayer lyr, ArrayList<Sprite> sprs, ZKeyboard kb, ZMouse mouse, Camera c, double dist) {
    	if(isDashing()) {
    		setTermVelocity(30);
    		float sgn = Math.signum(dashing);
    		setSpeedX(30 * sgn);
    	    dashing -= sgn;
	    } else {
    		setTermVelocity(20);
	    }
		return super.update(lyr, sprs, kb, mouse, c, dist);
	}

	public Sonic(double x, double y, long w, Level l) {
        super(getPaths(), getAniChanger(), x, y, 96, 96, 20, 1.2, 2, w, l);
    }

	@Override
	public void special(boolean prev, boolean space) {
		super.special(prev, space);
		if(!prev && space && !isDashing() && getEnergy() != 0) {
			setEnergy(getEnergy() - 1);
			dashing = 30 * (isFacingRight() ? 1 : -1);
		}
	}

	public boolean isDashing() {
		return dashing != 0;
	}
}
