package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Utils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Is necessary. The level draws the level in its own, while
 * this class will draw the static things in it, like punctuation
 * and things like that.
 */
public class DynamicScreen implements Screen {

	private ZImage s;
	private Level level;
	private boolean hover = false;
	private ZImage hoverImage;
	private ArrayList<Tag> tags;

	public DynamicScreen(String levelName) throws IOException, SAXException, ParserConfigurationException {
		level = new Level(levelName);
		hoverImage = new ZImage("images/hover_image.png");
		tags = new ArrayList<>(4);

		for (int i = 0; i < 4; i++) {
			ZRect r = new ZRect(
					4 + 64 * i,
					Utils.getInstance().getHeight() - 64,
					64, 80
			);
			tags.add(new Tag(r, "images/tags" + i + ".png"));
		}
	}

	public void update(ZKeyboard kb, ZMouse m) {
		hover = level.update(kb, m);
		tags.get(level.getCharacterIndex()).setActive(true);
	}

	@Override
	public void draw(Graphics2D g2d) {
		level.draw((Graphics2D) g2d.create());

		if(SMLM.DEBUG_MODE) {
			Character c = level.getCharacter();
			g2d.setColor(Color.BLACK);
			g2d.drawString("posX: " + c.x, 5, 15);
			g2d.drawString("posY: " + c.y, 5, 30);
			g2d.drawString("velX: " + c.getSpeedX(), 5, 45);
			g2d.drawString("velY: " + c.getSpeedY(), 5, 60);
			g2d.drawString("onGround: " + c.isOnGround(), 5, 75);
			g2d.drawString("hover: " + hover, 5, 90);
		}

		if(hover) {
			hoverImage.draw(g2d, 0, Utils.getInstance().getHeight() - hoverImage.getHeight());
		}

		tags.forEach(tag -> tag.draw(g2d));
	}

	public void resetCharacter() {
		level.getCharacter().setLL(level.getCheckpoint().getLL());
		level.resetCamera();
	}

	private void setCharacterPos(double x, double y) {
		Character c = level.getCharacter();
		c.x = x;
		c.y = y;
		c.setSpeedX(0);
		c.setSpeedY(0);
	}
}
