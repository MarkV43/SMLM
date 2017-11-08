package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.geom.Point2D;

/**
 * Created by Marcelo Vogt on 29/09/2017.
 */
public class ZPoint {

	public double x;
	public double y;

	public ZPoint() {
		x = y = 0;
	}

	public ZPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public ZPoint(ZPoint p) {
		x = p.x;
		y = p.y;
	}

	public ZPoint(Point2D p) {
		x = p.getX();
		y = p.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean in(ZRect r) {
		return x >= r.x && x <= r.x + r.w &&
				y >= r.y && y <= r.y + r.h;
	}

	@Override
	public String toString() {
		return "ZPoint{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
