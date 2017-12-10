package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Bullet;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZRect;

import java.util.ArrayList;

public class WalkBlasterBullet extends Bullet {

	@Override
	public ArrayList<ZRect> filter(ArrayList<Sprite> sprs, boolean clouds) {
		ArrayList<ZRect> rs = new ArrayList<>();
		for (Sprite s : sprs) {
			if((!(s instanceof Cloud) || clouds) && !(s instanceof CheckPoint) && !(s instanceof Coin) && !(s instanceof WalkBlaster)) {
				rs.add(s);
			}
		}
		return rs;
	}

	public WalkBlasterBullet(double x, double y, boolean facingRight) {
		super("images/megaman_bullet#1", x, y, 16, 12, 15, facingRight);
		add("hit", "none");
	}
}
