package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;

/**
 * Created by Marcelo Vogt on 29/09/2017.
 */
public class ZRect {

	public double x;
	public double y;
	public double w;
	public double h;

	public ZRect(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public ZRect(double x, double y, double size) {
		this.x = x;
		this.y = y;
		w = h = size;
	}

	public ZRect(double w, double h) {
		x = 0;
		y = 0;
		this.w = w;
		this.h = h;
	}

	public ZRect() {
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}

	public ZPoint getUpLeft() {
		return new ZPoint(x, y);
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

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void draw(Graphics2D g2d, ZImage img) {
		g2d.drawImage(img.getImage(), (int) x, (int) y, (int) (x + w), (int) (y + h), 0, 0, img.getWidth(), img.getHeight(), null);
	}

	public Rectangle getRect() {
		return new Rectangle((int) x, (int) y, (int) w, (int) h);
	}

	public int intersectsX(ZRect r) {
		if(intersects(r)) {
			if (x + w >= r.x && x + w <= r.x + r.w) {
				return 1;
			} else if (x <= r.x + r.w && x >= r.x) {
				return -1;
			}
		}
		return 0;
	}

	public int intersectsY(ZRect r) {
		if(intersects(r)) {
			if (y + h >= r.h && y + h <= r.y + r.h) {
				return 1;
			} else if (y <= r.y + r.h && y >= r.y) {
				return -1;
			}
		}
		return 0;
	}

	public boolean intersects(ZRect r) {
		return getRect().intersects(r.getRect());
	}

	public void drawBorder(Graphics2D g2d, Color c) {
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine((int) x, (int) y, (int) (x + w), (int) y);
		g2d.drawLine((int) x, (int) y, (int) x, (int) (y + h));
		g2d.drawLine((int) (x + w), (int) (y + h), (int) x, (int) (y + h));
		g2d.drawLine((int) (x + w), (int) (y + h), (int) (x + w), (int) y);
	}

	@Override
	public String toString() {
		return "ZRect{" +
				"x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}
}
