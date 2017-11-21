package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;

/**
 * Created by Marcelo Vogt on 21/11/2017.
 */
public class Camera extends ZRect {
	private ZRect limits;
	private double smoothness;

	public Camera(ZRect limits, double smoothness, ZPoint center) {
		this.limits = limits;
		this.smoothness = smoothness;
		w = Utils.getInstance().getWidth();
		h = Utils.getInstance().getHeight();
		setCenter(center);
	}

	public void goTo(ZPoint p) {
		ZPoint newCenter = ZMath.lerp(getCenter(), p, smoothness);
		setCenter(newCenter);
	}

	private void setCenter(ZPoint newCenter) {
		ZPoint preCenter = getCenter();
		x += newCenter.x - preCenter.x;
		if(x < limits.x) {
			x = 0;
		} else if(x + w > limits.x + limits.w) {
			x = limits.x + limits.w - w;
		}
		y += newCenter.y - preCenter.y;
		if(y < 0) {
			y = 0;
		} else if(y + h > limits.y + limits.h) {
			y = limits.y + limits.h - h;
		}
	}

	@Override
	public String toString() {
		return "Camera{" +
				"center=" + getCenter() +
				'}';
	}
}
