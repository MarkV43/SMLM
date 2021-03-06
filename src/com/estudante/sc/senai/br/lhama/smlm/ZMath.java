package com.estudante.sc.senai.br.lhama.smlm;

/**
 * Created by Marcelo Vogt on 21/11/2017.
 */
public final class ZMath {
	private ZMath() {
	}

	public static final double TWO_PI = Math.PI * 2;

	public static ZPoint lerp(ZPoint p1, ZPoint p2, double alpha) {
		ZPoint ret = new ZPoint();
		ret.x = lerp(p1.x, p2.x, alpha);
		ret.y = lerp(p1.y, p2.y, alpha);
		return ret;
	}

	public static double lerp(double p1, double p2, double alpha) {
		return p1 + alpha * (p2 - p1);
	}

	public static int limit(int num, int min, int max) {
		return Math.min(max, Math.max(min, num));
	}

	public static double angleAdd(double angle, double add) {
		return (angle + add) % TWO_PI;
	}

	public static double angleAdd(double angle, double add, int mult) {
		return (angle + add) % (TWO_PI * mult);
	}
}
