package com.estudante.sc.senai.br.lhama.smlm;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * Is necessary. The level draws the level in its own, while
 * this class will draw the static things in it, like punctuation
 * and things like that.
 */
public class DynamicScreen implements Screen {

	private ZImage s;
	private Level level;

	public DynamicScreen(String levelName) throws IOException, SAXException, ParserConfigurationException {
		level = new Level(levelName);
	}

	public void update(ZKeyboard kb, ZMouse m) {
		level.update(kb, m);
	}

	@Override
	public void draw(Graphics2D g2d) {
		level.draw(g2d);
	}
}
