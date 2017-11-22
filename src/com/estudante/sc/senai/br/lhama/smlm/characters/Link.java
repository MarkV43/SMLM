package com.estudante.sc.senai.br.lhama.smlm.characters;

import com.estudante.sc.senai.br.lhama.smlm.AnimationChanger;
import com.estudante.sc.senai.br.lhama.smlm.Character;

import java.util.HashMap;

public class Link extends Character {
    public Link(HashMap<String, String> paths, AnimationChanger aniChanger, String defaultAnimation, double x, double y, double w, double h, int termVelocity, double speed, double jumpHeight) {
        super(paths, aniChanger, defaultAnimation, x, y, w, h, termVelocity, speed, jumpHeight);
    }
}
