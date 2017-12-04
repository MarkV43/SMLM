package com.estudante.sc.senai.br.lhama.smlm;


import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 02/12/2017.
 */
public class Bullet extends Sprite {

	private double speed;

	public Bullet(String path, double x, double y, int w, int h, double speed, boolean facingRight) {
		super(ZUtils.toMap("shoot", path), spr -> "shoot", "shoot", x - w / 2, y - h / 2, w, h);
		setFacingRight(facingRight);
		this.speed = speed;
	}

	@Override
	public void update(TileLayer lyr, ArrayList<Sprite> sprs) {
		this.x += speed * (isFacingRight() ? 1 : -1);
	}
}
