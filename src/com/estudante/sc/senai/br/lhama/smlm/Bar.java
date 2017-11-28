package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.util.Stack;

/**
 * Created by Marcelo Vogt on 26/11/2017.
 */
public class Bar extends ZImage {

	private Stack<BarSegment> segs;
	private int size = 8;
	private BarSegment template;

	public Bar(String path, String segPath) {
		super(path);
		segs = new Stack<>();
		template = new BarSegment(segPath);
	}

	public void reset() {
		while(segs.size() < size) {
			segs.push(new BarSegment(template));
		}
	}

	public void draw(Graphics2D g2d) {
		super.draw(g2d, 5, 5);
	}

}
