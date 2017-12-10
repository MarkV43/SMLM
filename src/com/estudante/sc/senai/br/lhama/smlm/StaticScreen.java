package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;

import java.awt.*;
import java.util.ArrayList;

public class StaticScreen implements Screen {

	private <T> ArrayList<T> resize(ArrayList<T> a) {
		ZPoint p = SMLM.TARGET_SIZE;
		double w = Utils.getInstance().getWidth();
		double h = Utils.getInstance().getHeight();

		for (T img : a) {
			ZRect r = ((ZImage) img).getRect();

			r.w = r.w * w / p.x;
			r.h = r.h * h / p.y;
			switch (SMLM.mode) {
				case CENTER:
					r.x = r.x * w / p.x - r.w / 2;
					r.y = r.y * h / p.y - r.h / 2;
					break;
				case TOP_LEFT:
					r.x = r.x * w / p.x;
					r.y = r.y * h / p.y;
					break;
			}
		}
		return a;
	}

	private ArrayList<Clickable> clickables;
	private ArrayList<ZImage> images;
	private ZImage background;

	public StaticScreen(ZImage background, ArrayList<Clickable> clickables, ArrayList<ZImage> images) {
		this.background = background;
		this.clickables = resize(clickables);
		this.images = resize(images);
	}

	public void update(ZMouse m) {
		for (Clickable c : clickables) {
			c.update(m);
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		background.draw(g2d);
		for (Clickable c : clickables) {
			c.draw(g2d);
		}
		for (ZImage img : images) {
			img.draw(g2d);
		}
	}

}
