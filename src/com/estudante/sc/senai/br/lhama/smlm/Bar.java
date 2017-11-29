package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.Stack;

/**
 * Created by Marcelo Vogt on 26/11/2017.
 */
public class Bar extends ZImage {

	private final static int SIZE = 8;

	private ZStack<BarSegment> segs;
	private BarSegment template;

	public Bar(String path, String segPath) {
		super(path);
		segs = new ZStack<>(4);
		template = new BarSegment(segPath);
	}

	public void reset() {
		while(segs.size() < SIZE) {
			segs.push(new BarSegment(template));
		}
	}

	public void draw(Graphics2D g2d) {
		super.draw(g2d, 5, 5);
		segs.forEach(this::drawSegment, g2d);
	}

	private void drawSegment(BarSegment seg) {

	}

}
