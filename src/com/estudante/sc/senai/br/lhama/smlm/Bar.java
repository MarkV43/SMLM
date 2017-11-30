package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;

/**
 * Created by Marcelo Vogt on 26/11/2017.
 */
public class Bar extends ZImage {

	private final static int SIZE = 8;

	public final static int FULL = SIZE;
	public final static int EMPTY = 0;
	public final static int HALF = FULL / 2;

	private ZStack<BarSegment> segs;
	private BarSegment template;

	private int x;
	private int y;

	public Bar(String path, int initial, int x, int y) {
		super("images/".concat(path).concat(".png"));
		segs = new ZStack<>(SIZE);
		template = new BarSegment("images/".concat(path).concat("_seg.png"));
		set(initial);
		this.x = x;
		this.y = y;
	}

	public void empty() {
		while(segs.size() > 0) {
			segs.pop();
		}
	}

	public void set(int i) {
		while(segs.size() < i) {
			segs.push(new BarSegment(template));
		}
		while(segs.size() > i) {
			segs.pop();
		}
	}

	public int count() {
		return segs.size();
	}

	public void add(int i) {
		set(i + segs.size());
	}

	public void remove(int i) {
		set(segs.size() - i);
	}

	public boolean is(int state) {
		return segs.size() == state;
	}

	public void fill() {
		set(FULL);
	}

	public void draw(Graphics2D g2d) {
		super.draw(g2d, x, y);
		segs.indexedForEach((i, seg) ->
				seg.draw(g2d, 12 + x, 9 + y + ((SIZE - (i + 1)) * 12)));
	}

}
