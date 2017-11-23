package com.estudante.sc.senai.br.lhama.smlm;

public class ZKeyboard {
	public boolean W;
	public boolean pW;
	public boolean A;
	public boolean S;
	public boolean D;
	public boolean B17;
	public boolean B28;
	public boolean B39;
	public boolean B40;
	public boolean SPACE;
	public boolean ESCP;

	public ZKeyboard() {
		W = false;
		pW = false;
		A = false;
		S = false;
		D = false;
		B17 = false;
		B28 = false;
		B39 = false;
		B40 = false;
		SPACE = false;
		ESCP = false;
	}

	public ZKeyboard(ZKeyboard k) {
		A = k.A;
		S = k.S;
		D = k.D;
		W = k.W;
		pW = k.pW;
		B17 = k.B17;
		B28 = k.B28;
		B39 = k.B39;
		B40 = k.B40;
		SPACE = k.SPACE;
		ESCP = k.ESCP;
	}
}
