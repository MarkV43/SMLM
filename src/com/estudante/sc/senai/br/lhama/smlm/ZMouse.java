package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.event.MouseEvent;

public class ZMouse {
	public boolean click;
	public ZPoint pos;

	public ZMouse() {
		pos = new ZPoint();
	}

	public void set(MouseEvent e) {
		pos.x = e.getX();
		pos.y = e.getY();
	}

	@Override
	public String toString() {
		return "ZMouse{" +
				"click=" + click +
				", pos=" + pos +
				'}';
	}
}
