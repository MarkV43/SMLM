package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Bullet;

/**
 * Created by Marcelo Vogt on 01/12/2017.
 */
public class MegamanBullet extends Bullet {

	public MegamanBullet(double x, double y, boolean facingRight) {
		super("images/megaman_bullet#1", x, y, 32, 24, 15, facingRight);
	}
}
