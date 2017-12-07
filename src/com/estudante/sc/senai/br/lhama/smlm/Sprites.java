package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.sprites.*;

import java.lang.Character;
import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Sprites {

	private static Sprites instance = new Sprites();

	private HashMap<String, Sprite> hm = new HashMap<>();

	private Sprites() {
		hm.put("cp", new CheckPoint(0, 0));
	}

	private static Sprites getInstance() {
		return instance;
	}

	public static Sprite getInstance(Level l, String name, double x, double y) throws Exception {
		char last = name.charAt(name.length() - 1);
		Sprite spr;
		int num = -1;
		if (Character.isDigit(last)) {
			num = Character.getNumericValue(last);
			name = name.substring(0, name.length() - 1);
		}
		switch (name.toLowerCase()) {
			case "koopa":
				spr = new Koopa(x, y);
				break;
			case "cp":
				spr = new CheckPoint(getInstance().hm.get("cp"), x, y, num);
				break;
			case "cloud":
				spr = new Cloud(num, x, y, l);
				break;
			case "goomba":
				spr = new Goomba(x, y);
				break;
			case "met":
				spr = new Met(x, y);
				break;
			case "coin":
				spr = new Coin(x, y);
				break;
			case "saw":
				spr = new Saw(x, y);
				break;
			default:
				throw new Exception("No matched Sprite");
		}
		return spr;
	}
}
