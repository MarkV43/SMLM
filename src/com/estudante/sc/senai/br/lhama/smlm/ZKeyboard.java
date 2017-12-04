package com.estudante.sc.senai.br.lhama.smlm;

public class ZKeyboard {
	public boolean pW;
	public boolean W;
	public boolean pA;
	public boolean A;
	public boolean pS;
	public boolean S;
	public boolean pD;
	public boolean D;
	public boolean pB17;
	public boolean B17;
	public boolean pB28;
	public boolean B28;
	public boolean pB39;
	public boolean B39;
	public boolean pB40;
	public boolean B40;
	public boolean SPACE;
	public boolean pSPACE;
	public boolean pESCP;
	public boolean ESCP;

	public ZKeyboard() {
		pW = false;
		W = false;
		pA = false;
		A = false;
		pS = false;
		S = false;
		pB17 = false;
		B17 = false;
		pB28 = false;
		B28 = false;
		pB39 = false;
		B39 = false;
		pB40 = false;
		B40 = false;
		pSPACE = false;
		SPACE = false;
		pESCP = false;
		ESCP = false;
	}
	
	public ZKeyboard(ZKeyboard k) {
		pW = k.pW;
		W = k.W;
		pA = k.pA;
		A = k.A;
		pS = k.pS;
		S = k.S;
		pD = k.pD;
		D = k.D;
		pB17 = k.pB17;
		B17 = k.B17;
		pB28 = k.pB28;
		B28 = k.B28;
		pB39 = k.pB39;
		B39 = k.B39;
		pB40 = k.pB40;
		B40 = k.B40;
		pSPACE = k.pSPACE;
		SPACE = k.SPACE;
		pESCP = k.pESCP;
		ESCP = k.ESCP;
	} 

	public void W(boolean w) {
		pW = W;
		W = w;
	}

	public void A(boolean a) {
		pA = A;
		A = a;
	}

	public void S(boolean s) {
		pS = S;
		S = s;
	}

	public void D(boolean d) {
		pD = D;
		D = d;
	}

	public void B17(boolean b17) {
		pB17 = B17;
		B17 = b17;
	}

	public void B28(boolean b28) {
		pB28 = B28;
		B28 = b28;
	}

	public void B39(boolean b39) {
		pB39 = B39;
		B39 = b39;
	}

	public void B40(boolean b40) {
		pB40 = B40;
		B40 = b40;
	}

	public void SPACE(boolean space) {
		pSPACE = SPACE;
		SPACE = space;
	}

	public void ESCP(boolean escp) {
		pESCP = ESCP;
		ESCP = escp;
	}

	public void update(ZKeyboard k) {
		pW = W;
		W = k.W;
		pA = A;
		A = k.A;
		pS = S;
		S = k.S;
		pD = D;
		D = k.D;
		pB17 = B17;
		B17 = k.B17;
		pB28 = B28;
		B28 = k.B28;
		pB39 = B39;
		B39 = k.B39;
		pB40 = B40;
		B40 = k.B40;
		pSPACE = SPACE;
		SPACE = k.SPACE;
		pESCP = ESCP;
		ESCP = k.ESCP;
	}

	@Override
	public String toString() {
		return "ZKeyboard{" +
				"pD=" + pD +
				", D=" + D +
				'}';
	}
}
