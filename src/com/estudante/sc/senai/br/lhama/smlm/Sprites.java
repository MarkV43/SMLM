package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.sprites.CheckPoint;
import com.estudante.sc.senai.br.lhama.smlm.sprites.KoopaShell;

import java.lang.Character;
import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Sprites {

	private static Sprites instance = new Sprites();

	private HashMap<String, Sprite> hm = new HashMap<>();

	private Sprites() {
		hm.put("koopa_shell", new KoopaShell(0,0));
		hm.put("cp", new CheckPoint(0, 0));
	}

	private static Sprites getInstance(){
		return instance;
	}

	public static Sprite getInstance(String name, double x, double y) {
		char last = name.charAt(name.length() - 1);
		Sprite spr;
		if(Character.isDigit(last)) {
			int num = Character.getNumericValue(last);
			spr = new CheckPoint(getInstance().hm.get("cp"), x, y, num);
		} else {
			spr = new Sprite(getInstance().hm.get(name), x, y);
		}
		return spr;
	}
}
