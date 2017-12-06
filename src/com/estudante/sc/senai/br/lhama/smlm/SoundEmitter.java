package com.estudante.sc.senai.br.lhama.smlm;

import java.util.HashMap;

public interface SoundEmitter {

	void add(String name, ZClip clip);

	int play(String name);

	int loop(String name, int times);

	void stop(String name, int i);

}
