package com.estudante.sc.senai.br.lhama.smlm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcelo Vogt on 02/12/2017.
 */
public final class ZUtils {
	private ZUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends Map<K, V>, K, V> T toMap(K key, V value) {
		Map<K, V> m = new HashMap<>();
		m.put(key, value);
		return (T) m;
	}

	public static <T extends Collection<O>, O extends D, D> Collection<D> cast(T t) {
		Collection<D> d = new ArrayList<>();
		d.addAll(t);
		return d;
	}
}
