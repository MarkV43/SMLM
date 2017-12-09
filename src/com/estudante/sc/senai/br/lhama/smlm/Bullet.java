package com.estudante.sc.senai.br.lhama.smlm;


import com.estudante.sc.senai.br.lhama.smlm.sprites.Blader;
import com.estudante.sc.senai.br.lhama.smlm.sprites.CheckPoint;
import com.estudante.sc.senai.br.lhama.smlm.sprites.Cloud;
import com.estudante.sc.senai.br.lhama.smlm.sprites.Coin;

import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 02/12/2017.
 */
public class Bullet extends Sprite {

	public static ArrayList<ZRect> filter(ArrayList<Sprite> sprs, boolean clouds) {
		ArrayList<ZRect> rs = new ArrayList<>();
		for (Sprite s : sprs) {
			if((!(s instanceof Cloud) || clouds) && !(s instanceof CheckPoint) && !(s instanceof Coin)) {
				rs.add(s);
			}
		}
		return rs;
	}

	private double speed;
	private boolean dead = false;

	private static String change(Sprite spr) {
		if (((Bullet) spr).isDead()) {
			return "none";
		} else {
			return "shoot";
		}
	}

	public Bullet(String path, double x, double y, int w, int h, double speed, boolean facingRight) {
		super(ZUtils.toMap("shoot", path), Bullet::change, "shoot", x - w / 2, y - h / 2, w, h);
		setFacingRight(facingRight);
		this.speed = speed;
		add("hit", "meg_damage");
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		this.x += speed * (isFacingRight() ? 1 : -1);
		ArrayList<ZRect> range = getRangeX(lyr.getTiles(), SMLM.TILE_SIZE);
		if (collisionX(range)) {
			dead = true;
			play("hit");
		}
		if (collisionX(filter(sprs, clouds))) {
			ZRect r = getLastCollision();
			if(r instanceof Blader) {
				((Blader) r).hit();
			}
			dead = true;
			play("hit");
		}
	}

	public boolean isDead() {
		return dead;
	}
}
