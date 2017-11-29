package com.estudante.sc.senai.br.lhama.smlm;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ZStack<T extends Drawable> implements Iterateable<T> {
	private Object[] ts;
	private int length = 0;

	public ZStack() {
		ts = new Object[1000];
	}

	public ZStack(int capacity) {
		ts = new Object[capacity];
	}

	public void push(T obj) {
		int i = length;
		length++;
		ts[i] = obj;
	}

	@SuppressWarnings("unchecked")
	public T pop() {
		length--;
		int i = length;
		T value = (T) ts[i];
		ts[i] = null;
		return value;
	}

	public int size() {
		return length;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void forEach(Consumer<T> c) {
		for (Object t : ts) {
			c.accept((T) t);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void indexedForEach(BiConsumer<Integer, T> b) {
		for (int i = 0; i < ts.length; i++) {
			T t = (T) ts[i];
			if(t != null) {
				b.accept(i, t);
			}
		}
	}
}
