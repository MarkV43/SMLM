package com.estudante.sc.senai.br.lhama.smlm;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
		return new ArrayList<>(t);
	}

	public static String repeat(String str, int times) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < times; i++) {
			s.append(str);
		}
		return s.toString();
	}

	public static String trail(int num, int size) {
		NumberFormat formatter = new DecimalFormat(repeat("0", size));
		return formatter.format(num);
	}
}
