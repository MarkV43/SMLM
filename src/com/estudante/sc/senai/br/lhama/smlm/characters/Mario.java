package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.ArrayList;
import java.util.HashMap;

public class Mario extends Character {

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/idle#3");
	    paths.put("fall", "characters/fall#3");
	    paths.put("jump", "characters/jump#3");
	    paths.put("walk", "characters/walk#3");
	    paths.put("spin", "characters/mario_spin#3");
        return paths;
    }

    private static AnimationChanger getAniChanger() {
    	return spr -> {
		    if (spr.isOnGround()) {
			    if (Math.abs(spr.getSpeedX()) > 1.2) {
				    return "walk";
			    } else {
				    return "idle";
			    }
		    } else if(((Mario) spr).isSpinning()) {
		    	return "spin";
		    } else if (spr.getSpeedY() > 0) {
			    return "fall";
		    } else {
			    return "jump";
		    }
	    };
    }

    private boolean spinning;

    public Mario(double x, double y, long w, Level l) {
        super(getPaths(), getAniChanger(), x, y, 48, 96, 10, 0.8, 3, w, l);
    }

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs) {
		super.update(lyr, sprs);
		if(isOnGround()) {
			spinning = false;
		}
	}

	@Override
	public void special(boolean prev, boolean space) {
		super.special(prev, space);

		if(isOnGround()) {
			spinning = !prev && space;
			if(spinning) {
				setEnergy(getEnergy() - 1);
				setSpeedY(getJumpSpeed());
			}
		}
	}

	public boolean isSpinning() {
    	return spinning;
	}
}
