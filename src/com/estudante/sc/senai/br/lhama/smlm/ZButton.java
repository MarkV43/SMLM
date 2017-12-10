package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;

import java.awt.*;

/**
 * Created by Marcelo Vogt on 29/09/2017.
 */
public class ZButton extends ZImage implements Clickable {

	private ZImage normal, clicked, hovered;
	private ZRunnable click, hover, over;
	private boolean prevClicking, prevHovering;

	public ZButton(ZRect rect, String path, String clicked, Runnable click) {
		super(rect, "buttons/".concat(path).concat("_normal.png"));
		path = "buttons/".concat(path);
		this.normal = new ZImage(path.concat("_normal.png"));
		this.clicked = new ZImage(clicked);
		this.click = new ZRunnable(click);
		this.hover = new ZRunnable();
		this.over = new ZRunnable();
	}

	public ZButton(ZRect rect, String path, Runnable click) {
		super(rect, "buttons/".concat(path).concat("_normal.png"));
		path = "buttons/".concat(path);
		this.normal = new ZImage(path.concat("_normal.png"));
		this.clicked = new ZImage(path.concat("_click.png"));
		this.hovered = new ZImage(path.concat("_hover.png"));
		this.click = new ZRunnable(click);
		this.hover = new ZRunnable();
		this.over = new ZRunnable();
	}

	public ZButton(ZRect rect, String path, Runnable click, Runnable hover) {
		super(rect, "buttons/".concat(path).concat("_normal.png"));
		path = "buttons/".concat(path);
		this.normal = new ZImage(path.concat("_normal.png"));
		this.clicked = new ZImage(path.concat("_click.png"));
		this.hovered = new ZImage(path.concat("_hover.png"));
		this.click = new ZRunnable(click);
		this.hover = new ZRunnable(hover);
		this.over = new ZRunnable();
	}

	public ZButton(ZRect rect, String path, Runnable click, Runnable hover, Runnable over) {
		super(rect, "buttons/".concat(path).concat("_normal.png"));
		path = "buttons/".concat(path);
		this.normal = new ZImage(path.concat("_normal.png"));
		this.clicked = new ZImage(path.concat("_click.png"));
		this.hovered = new ZImage(path.concat("_hover.png"));
		this.click = new ZRunnable(click);
		this.hover = new ZRunnable(hover);
		this.over = new ZRunnable(over);
	}

	public void update(ZMouse m) {
		boolean h = m.in(getRect());
		if(h) {
			if (m.click) {
				setImage(clicked);
			} else if (prevClicking) {
				click.run();
			} else {
				setImage(hovered);
				over.run();
				if (!prevHovering) {
					hover.run();
				}
			}
		}else {
			setImage(normal);
		}
		prevClicking = m.click;
		prevHovering = h;
	}

	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if(SMLM.DEBUG_MODE) {
			getRect().drawBorder(g2d, Color.BLUE);
		}
	}

}
