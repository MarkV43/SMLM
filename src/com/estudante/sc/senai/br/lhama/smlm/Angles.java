package com.estudante.sc.senai.br.lhama.smlm;

public interface Angles {
	default double cos(double angle) {
		return Math.cos(angle);
	}

	default double sin(double angle) {
		return Math.sin(angle);
	}
}
