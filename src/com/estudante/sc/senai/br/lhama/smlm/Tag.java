package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;

/**
 * Created by Marcelo Vogt on 24/11/2017.
 */
public class Tag extends ZImage {

	private boolean active = false;

	public Tag(ZRect rect, String path) {
		super(rect, path);
	}

	@Override
	public void draw(Graphics2D g2d) {
		if(active) {
			setY(getY() - 8);
		}
		super.draw(g2d);
		if(active) {
			setY(getY() + 8);
			active = false;
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
