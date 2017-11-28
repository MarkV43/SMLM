package com.estudante.sc.senai.br.lhama.smlm;

public class ZStack<T> {
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
}
