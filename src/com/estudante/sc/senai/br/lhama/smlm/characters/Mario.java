package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.AnimationChanger;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.HashMap;

public class Mario extends Character {
    public Mario(HashMap<String, String> paths, AnimationChanger aniChanger, String defaultAnimation, double x, double y, double w, double h, int termVelocity, double speed, double jumpHeight) {
        super(paths, aniChanger, defaultAnimation, x, y, w, h, termVelocity, speed, jumpHeight);
    }
}
