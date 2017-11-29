package com.estudante.sc.senai.br.lhama.smlm;

import java.util.function.Consumer;

/**
 * Created by Marcelo Vogt on 19/11/2017.
 */
public interface AnimationChanger extends Consumer {
	String change(Sprite spr);
}
