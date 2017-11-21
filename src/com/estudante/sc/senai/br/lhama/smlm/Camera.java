package com.estudante.sc.senai.br.lhama.smlm;

/**
 * Created by Marcelo Vogt on 21/11/2017.
 */
public class Camera extends ZRect {
	private ZRect limits;
	private double smoothness;

	public Camera(ZRect limits, double smoothness) {
		this.limits = limits;
		this.smoothness = smoothness;
	}

	public void goTo(ZPoint p) {
		ZPoint newCenter = ZMath.lerp(getCenter(), p, smoothness);
		setCenter(newCenter);
	}

	private void setCenter(ZPoint newCenter) {
		ZPoint preCenter = getCenter();
		x += newCenter.x - preCenter.x;
		y += newCenter.y - preCenter.y;
	} 
}
