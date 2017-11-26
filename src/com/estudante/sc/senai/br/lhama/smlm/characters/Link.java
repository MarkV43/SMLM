package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.ZMouse;

import java.util.HashMap;

public class Link extends Character {

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
	    paths.put("idle", "characters/idle#3");
	    paths.put("fall", "characters/fall#3");
	    paths.put("jump", "characters/jump#3");
	    paths.put("walk", "characters/walk#3");
        return paths;
    }

    public Link(double x, double y, long w) {
        super(getPaths(), x, y, 48, 48, 5, 0.5, 0.1, w);
    }

	@Override
	public boolean mouseOver() {
		return true;
	}

	@Override
	public void special() {

	}
}
