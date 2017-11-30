package com.estudante.sc.senai.br.lhama.smlm;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class ZList<T> extends ArrayList<T> {
	public void forEachOther(BiConsumer<T, T> b) {
		for (int i = 0; i < size() - 1; i++) {
			for(int j = i + 1; j < size(); j++) {
				b.accept(get(i), get(j));
			}
		}
	}
}
