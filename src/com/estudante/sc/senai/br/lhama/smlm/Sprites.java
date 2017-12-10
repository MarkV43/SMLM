package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.sprites.*;

import java.lang.Character;
import java.util.HashMap;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class Sprites {

	public static Sprite getInstance(Level l, String name, double x, double y) throws Exception {
		Sprite spr;
		int num = 0;
		int i = 0;
		char last = name.charAt(name.length() - 1);
		while(Character.isDigit(last)) {
			num += Character.getNumericValue(last) * Math.pow(10, i);
			name = name.substring(0, name.length() - 1);
			last = name.charAt(name.length() - 1);
			i++;
		}
		switch (name.toLowerCase()) {
			case "koopa":
				spr = new Koopa(x, y);
				break;
			case "cp":
				spr = new CheckPoint(x, y, num);
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
			case "crab":
				spr = new CrabBot(x, y);
				break;
			case "blader":
				spr = new Blader(x, y, num);
				break;
			case "beebot":
				spr = new BeeBot(x, y, num);
				break;
			case "walkblaster":
				spr = new WalkBlaster(x, y);
				break;
			default:
				throw new Exception("No matched Sprite: " + name);
		}
		return spr;
	}
}
