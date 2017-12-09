package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.*;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Met extends Sprite {

	private int shoot = 270;
	private int sneaking = 180;



	@Override
	public boolean canShoot() {
		return true;
	}

	@Override
	public int framesPerFrame() {
		return 15;
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("shoot", "sprites/met_shoot#1");
		hm.put("walk", "sprites/met_walk#2");
		hm.put("sneak", "sprites/met_sneak#1");
		return hm;
	}

	private static String change(Sprite spr) {
		Met g = (Met) spr;
		if (g.isDead()) {
			return "none";
		} else if (g.isShooting()) {
			return "shoot";
		} else if (g.isSneaking()) {
			return "sneak";
		} else {
			return "walk";
		}
	}

	@Override
	public void collide(Character c) {
		if(!isDead()) {
			c.die();
		}
	}

	public void hit() {
		if(!isDead() && !isSneaking()) {
			setDead(true);
		}
	}

	public Met(double x, double y) {
		super(getPaths(), Met::change, "sneak", x, y, 64, 64);
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs, boolean clouds) {
		if(!isDead()) {
			sneaking--;
			shoot--;

			if (sneaking == -180) {
				sneaking = 180;
				shoot = 270;
			}

			if (shoot == -15) {
				shoot();
			}


			if ((!isSneaking() && isShooting()) || isSneaking()) {
				setSpeedX(0);
			} else {
				setSpeedX((isFacingRight() ? 1 : -1));
			}

		} else {
			setSpeedX(0);
			setSpeedY(0);
		}
		super.update(lyr, sprs, clouds, () -> setFacingRight(!isFacingRight()));
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if(SMLM.DEBUG_MODE) {
			g2d.drawString(String.valueOf(isDead()), (int) x, (int) y - 30);
		}
	}

	private void shoot() {
		ZPoint center = getCenter();
		addBullet(new MetBullet(center.x, center.y, isFacingRight()));
	}

	private boolean isSneaking() {
		return sneaking > 0;
	}

	private boolean isShooting() {
		return shoot < 0  && shoot > -30;
	}

}
