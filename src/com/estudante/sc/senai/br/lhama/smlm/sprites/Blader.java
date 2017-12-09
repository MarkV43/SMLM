package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.characters.Mario;

import java.util.ArrayList;
import java.util.HashMap;

public class Blader extends Sprite {

	private int r;
	private double cx;
	private double cy;
	private double angle = 0;

	@Override
	public int framesPerFrame() {
		return 5;
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("idle", "sprites/blader#4");
		return hm;
	}

	@Override
	public boolean falls() {
		return false;
	}

	private static String change(Sprite spr) {
		if (spr.isDead()) {
			return "none";
		} else {
			return "idle";
		}
	}


	public void hit() {
		setDead(true);
	}

	@Override
	public void collide(Character c) {
		if (!isDead()) {
			if (c instanceof Mario && ((Mario) c).isSpinning() && fromTopS(c)) {
				if (c.getSpeedY() > 0) {
					c.bounce(-5);
					c.play("spin_bounce");
				}
			} else {
				c.die();
			}
		}
	}

	public Blader(double x, double y, int r) {
		super(getPaths(), Blader::change, "idle", x, y - r * SMLM.TILE_SIZE, 64, 64);
		cx = x;
		cy = y;
		this.r = r * SMLM.TILE_SIZE;
	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if (!isDead()) {
			angle = ZMath.angleAdd(angle, 0.03);
			x = cx + Math.cos(angle) * r;
			y = cy + Math.sin(angle) * r;
			setSpeedX(0);
			setSpeedY(0);

			super.update(lyr, sprs, clouds);
		}
	}

	public void reset() {
		angle = 0;
		super.reset();
	}
}
