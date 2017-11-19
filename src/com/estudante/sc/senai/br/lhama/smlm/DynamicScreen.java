package com.estudante.sc.senai.br.lhama.smlm;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class DynamicScreen implements Screen {

	private ZImage s;
	private Level level;

	public DynamicScreen(String levelName) throws IOException, SAXException, ParserConfigurationException {
		level = new Level(levelName);
	}

	public void update(ZMouse m) {

	}

	@Override
	public void draw(Graphics2D g2d) {
		level.update();
		level.draw(g2d);
	}
}
