package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.event.MouseEvent;

public class ZMouse extends ZPoint {
	public boolean click;

	public void set(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public String toString() {
		return "ZMouse{" +
				"click=" + click +
				", x=" + x +
				", y=" + y +
				'}';
	}
}
