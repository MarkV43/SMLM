package com.estudante.sc.senai.br.lhama.smlm;

/**
 * Created by Marcelo Vogt on 29/09/2017.
 */
public class ZRunnable {
	private Runnable runnable;

	public ZRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public ZRunnable() {
		runnable = null;
	}

	public void run() {
		if(runnable != null) {
			runnable.run();
		}
	}
}
