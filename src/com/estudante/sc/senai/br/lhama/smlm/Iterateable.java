package com.estudante.sc.senai.br.lhama.smlm;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Marcelo Vogt on 29/11/2017.
 */
public interface Iterateable<T> {
	void forEach(Consumer<T> c);

	void indexedForEach(BiConsumer<Integer, T> b);
}
