package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.function.Consumer;

public class CheckBox extends ZImage implements Clickable {

	private boolean selected;
	private Consumer<Boolean> click;
	private boolean prevClick = false;

	private ZImage yes;
	private ZImage no;

	public CheckBox(ZRect rect, String no, String yes, Consumer<Boolean> click, boolean selected) {
		super(rect, "images/" + (selected ? yes : no) + ".png");
		this.selected = selected;
		this.click = click;
		this.yes = new ZImage("images/" + yes + ".png");
		this.no = new ZImage("images/" + no + ".png");
	}

	public void update(ZMouse m) {
		boolean h = m.in(getRect());
		if(h && !m.click && prevClick) {
			selected = !selected;
			setImage(selected ? yes : no);
			click.accept(selected);
		}
		prevClick = m.click;
	}

	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if(SMLM.DEBUG_MODE) {
			getRect().drawBorder(g2d, Color.ORANGE);
		}
	}

	public void set(boolean b) {
		selected = b;
	}

}
