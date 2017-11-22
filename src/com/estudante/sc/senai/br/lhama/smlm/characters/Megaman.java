package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.HashMap;

public class Megaman extends Character {

    private static HashMap<String, String> getPaths() {
        HashMap<String, String> paths = new HashMap<>();
        paths.put("idle", "characters/idle#3");
        paths.put("fall", "characters/fall#3");
        paths.put("jump", "characters/jump#3");
        paths.put("walk", "characters/walk#3");
        return paths;
    }

    public Megaman(double x, double y) {
        super(getPaths(), x, y, 48, 96, 7, 1, 1);
    }

    @Override
    public void special() {

    }
}
