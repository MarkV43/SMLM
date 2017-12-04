package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.sprites.MegamanBullet;

import java.util.ArrayList;
import java.util.HashMap;

public class Megaman extends Character {

	private int bulletDelay = 0;

	@Override
	public boolean canShoot() {
		return true;
	}

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

    public Megaman(double x, double y, long w, Level l) {
        super(getPaths(), getAniChanger(), x, y, 48, 80, 7, 1, 1, w, l);
    }

	@Override
	public boolean update(TileLayer lyr, ArrayList<Sprite> s, ZKeyboard kb, ZMouse mouse, Camera c, double dist) {
    	bulletDelay = Math.max(bulletDelay - 1, 0);
		return super.update(lyr, s, kb, mouse, c, dist);
	}

	@Override
    public void special(boolean prev, boolean space) {
        super.special(prev, space);
        if(!prev && space && getEnergy() != 0 && bulletDelay == 0) {
        	ZPoint center = getCenter();
        	bulletDelay = 35;
        	setEnergy(getEnergy() - 1);
        	addBullet(new MegamanBullet(center.x, center.y, isFacingRight()));
        }
    }
}
