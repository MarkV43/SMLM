package com.estudante.sc.senai.br.lhama.smlm;


import com.estudante.sc.senai.br.lhama.smlm.sprites.*;

import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 02/12/2017.
 */
public class Bullet extends Sprite {

	@Override
	public ArrayList<ZRect> filter(ArrayList<Sprite> sprs, boolean clouds) {
		ArrayList<ZRect> rs = new ArrayList<>();
		for (Sprite s : sprs) {
			if ((!(s instanceof Cloud) || clouds) && !(s instanceof CheckPoint) && !(s instanceof Coin)) {
				if (!s.isDead()) {
					rs.add(s);
				}
			}
		}
		return rs;
	}

	private double speed;

	private static String change(Sprite spr) {
		if (spr.isDead()) {
			return "none";
		} else {
			return "shoot";
		}
	}

	public Bullet(String path, double x, double y, int w, int h, double speed, boolean facingRight) {
		super(ZUtils.toMap("shoot", path), Bullet::change, "shoot", x - w / 2, y - h / 2, w, h);
		setFacingRight(facingRight);
		this.speed = speed;
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		this.x += speed * (isFacingRight() ? 1 : -1);
		ArrayList<ZRect> range = getRangeX(lyr.getTiles(), SMLM.TILE_SIZE);
		if (collisionX(range)) {
			setDead(true);
			play("hit");
		}
		if (collisionX(filter(sprs, clouds))) {
			ZRect r = getLastCollision();
			if (r instanceof Blader) {
				((Blader) r).hit();
			} else if (r instanceof Met) {
				((Met) r).hit();
			}
			setDead(true);
			play("hit");
		}
	}
}
