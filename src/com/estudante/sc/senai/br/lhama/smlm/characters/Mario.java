package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.ArrayList;
import java.util.HashMap;

public class Mario extends Character {

	@Override
	public int framesPerFrame() {
		return 5;
	}

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/mario/idle#4");
	    paths.put("fall", "characters/mario/fall#1");
	    paths.put("jump", "characters/mario/jump#1");
	    paths.put("walk", "characters/mario/walk#3");
	    paths.put("spin", "characters/mario/special#4");
	    paths.put("break", "characters/mario/break#1");
        return paths;
    }

    private static AnimationChanger getAniChanger() {
    	return spr -> {
		    if (spr.isOnGround()) {
		    	if (Math.abs(spr.getSpeedX()) > 1.2) {
				    if((spr.getSpeedX() > 0 && !spr.isFacingRight()) || (spr.getSpeedX() < 0 && spr.isFacingRight())) {
					    return "break";
				    } else {
					    return "walk";
				    }
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
        super(getPaths(), getAniChanger(), x, y, 96, 96, 10, 0.8, 3, w, l);
        add("stomp", "smw_stomp");
        add("spin", "smw_spin_jump");
        add("spin_stomp", "smw_spin_stomp");
        add("damage", "smw_damage");
        add("death", "smw_death");
        add("spin_bounce", "smw_spin_bounce");
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
		if(isOnGround()) {
			spinning = !prev && space;
			if(spinning) {
				setEnergy(getEnergy() - 1);
				setSpeedY(getJumpSpeed());
				play("spin");
			}
		}
	}

	public boolean isSpinning() {
    	return spinning;
	}
}
