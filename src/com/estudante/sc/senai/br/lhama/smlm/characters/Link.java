package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.ArrayList;
import java.util.HashMap;

public class Link extends Character {

	private int special = 0;

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/link/idle#2");
	    paths.put("walk", "characters/link/walk#4");
	    paths.put("ocarina", "characters/link/ocarina#2");
        return paths;
    }

	@Override
	public int framesPerFrame() {
		return 8;
	}

	@SuppressWarnings("Duplicates")
	private static AnimationChanger getAniChanger() {
		return spr -> {
			if(((Link) spr).getSpecial() > 0) {
//				System.out.println(spr);
				return "ocarina";
			} else if (Math.abs(spr.getSpeedX()) > 1.2) {
				return "walk";
			} else {
				return "idle";
			}
		};
	}

    public Link(double x, double y, long w, Level l) {
        super(getPaths(), getAniChanger(), x, y, 48, 48, 5, 0.5, 0.1, w, l);
    }

	@Override
	public boolean mouseOver() {
		return true;
	}

	@Override
	public boolean update(TileLayer lyr, ArrayList<Sprite> sprites, ZKeyboard kb, ZMouse mouse, Camera c, double dist, boolean clouds) {
		System.out.println(special);
    	if(special > 0) {
    		special--;
    		setSpeedX(0);
    		setSpeedY(0);
	    }
		super.update(lyr, sprites, kb, mouse, c, dist, clouds);
    	return false;
	}

	@Override
	public void special(boolean prev, boolean space) {
		if(!prev && space) {
			if(special == 0) {
				getLevel().toggleCloudColision();
				setEnergy(getEnergy() - 1);
				special = 30;
			}
		}
	}

	public int getSpecial() {
		return special;
	}

	@Override
	public String toString() {
		return "Link{" +
				"special=" + special +
				", x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}
}
