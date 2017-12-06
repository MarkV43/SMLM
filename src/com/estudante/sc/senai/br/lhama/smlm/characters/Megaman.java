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
		paths.put("idle", "characters/megaman/idle#8");
		paths.put("fall", "characters/megaman/fall#1");
		paths.put("jump-start", "characters/megaman/jump-start#1");
		paths.put("jump", "characters/megaman/jump#1");
		paths.put("walk", "characters/megaman/walk#10");
		paths.put("walk-shoot", "characters/megaman/walk-shot#10");
		paths.put("shoot", "characters/megaman/shot#1");
		return paths;
	}

	@SuppressWarnings("Duplicates")
	private static AnimationChanger getAniChanger() {
		return spr -> {
			if (spr.isOnGround()) {
				Megaman m = (Megaman) spr;
				if (Math.abs(spr.getSpeedX()) > 1.2) {
					if(m.isShooting()) {
						return "walk-shoot";
					} else {
						return "walk";
					}
				} else {
					if(m.isShooting()) {
						return "shoot";
					} else {
						return "idle";
					}
				}
			} else if (spr.getSpeedY() > 0) {
				return "fall";
			} else if (spr.getSpeedY() < ((Character) spr).getJumpSpeed() / 2) {
				return "jump-start";
			} else {
				return "jump";
			}
		};
	}

	public Megaman(double x, double y, long w, Level l) {
		super(getPaths(), getAniChanger(), x, y, 80, 80, 7, 1, 1, w, l);
	}

	@Override
	public boolean update(TileLayer lyr, ArrayList<Sprite> s, ZKeyboard kb, ZMouse mouse, Camera c, double dist) {
		bulletDelay = Math.max(bulletDelay - 1, 0);
		return super.update(lyr, s, kb, mouse, c, dist);
	}

	@Override
	public void special(boolean prev, boolean space) {
		if (!prev && space && getEnergy() != 0 && bulletDelay == 0) {
			ZPoint center = getCenter();
			bulletDelay = 35;
			setEnergy(getEnergy() - 1);
			addBullet(new MegamanBullet(center.x, center.y, isFacingRight()));
		}
	}

	public boolean isShooting() {
		return bulletDelay > 10;
	}
}
