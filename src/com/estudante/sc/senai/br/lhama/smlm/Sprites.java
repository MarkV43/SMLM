package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.sprites.KoopaShell;

import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Sprites {

	private static Sprites instance = new Sprites();

	private HashMap<String, Sprite> hm = new HashMap<>();

	private Sprites() {
		hm.put("koopa_shell", new KoopaShell(0,0));
	}

	private static Sprites getInstance(){
		return instance;
	}

	public static Sprite getInstance(String name, double x, double y) {
		return new Sprite(getInstance().hm.get(name), x, y);
	}
}
