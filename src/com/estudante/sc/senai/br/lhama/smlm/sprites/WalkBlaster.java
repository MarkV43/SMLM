package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.TileLayer;
import com.estudante.sc.senai.br.lhama.smlm.ZPoint;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.ArrayList;
import java.util.HashMap;

public class WalkBlaster extends Sprite {

	private int counter = 300;

	@Override
	public boolean canShoot() {
		return true;
	}

	@Override
	public boolean falls() {
		return false;
	}

	@Override
	public boolean collides() {
		return false;
	}

	@Override
	public void collide(Character c) {
		if (c instanceof Mario && ((Mario) c).isSpinning() && fromTopS(c)) {
			if (c.getSpeedY() > 0) {
				c.setSpeedY(c.getJumpSpeed());
				c.play("spin_bounce");
			}
		} else {
			c.die();
		}
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("idle", "sprites/walkblaster#1");
		return hm;
	}

	private static String change(Sprite spr) {
		return "idle";
	}

	public WalkBlaster(double x, double y) {
		super(getPaths(), WalkBlaster::change, "idle", x, y, 43, 41);
	}

	private void shoot() {
		ZPoint center = getCenter();
		addBullet(new WalkBlasterBullet(center.x, center.y, false));
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		setFacingRight(true);
		setSpeedX(0);
		setSpeedY(0);
		if(counter > 0) {
			counter--;
		} else {
			shoot();
			counter = 300;
		}
		super.update(lyr, sprs, clouds);
	}
}
