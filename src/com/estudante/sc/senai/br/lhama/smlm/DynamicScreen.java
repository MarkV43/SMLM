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
	private Bar energyBar;
	private Bar lifeBar;
	private ZMouse mouse;
	private int coins;

	public DynamicScreen(String levelName) throws IOException, SAXException, ParserConfigurationException {
		level = new Level(levelName);
		hoverImage = new ZImage("images/hover_image.png");
		tags = new ArrayList<>(4);
		energyBar = new Bar("energy", 52, 5, 16);
		lifeBar = new Bar("life", 5, 5, 8);

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
		mouse = m;
		lifeBar.set(level.getCharacter().getLife());
		energyBar.set(level.getCharacter().getEnergy());
		coins = level.getCharacter().getCoins();
	}

	@Override
	public void draw(Graphics2D g2d) {
		level.draw((Graphics2D) g2d.create());

		tags.forEach(tag -> tag.draw(g2d));
		energyBar.draw(g2d);
		lifeBar.draw(g2d);

		int x = Utils.getInstance().getWidth() - 70;
		int y = 33;
		g2d.setFont(Fonts.bit8.deriveFont(18f));
		g2d.drawString(ZUtils.trail(coins, 3), x, y);

		if(hover) {
			hoverImage.draw(g2d, 0, Utils.getInstance().getHeight() - hoverImage.getHeight());
		}

		if(SMLM.DEBUG_MODE) {
			Character c = level.getCharacter();
			g2d.setColor(Color.BLACK);
			g2d.drawString("posX: " + c.x, 99, 15);
			g2d.drawString("posY: " + c.y, 99, 30);
			g2d.drawString("velX: " + c.getSpeedX(), 99, 45);
			g2d.drawString("velY: " + c.getSpeedY(), 99, 60);
			g2d.drawString("onGround: " + c.isOnGround(), 99, 75);
			g2d.drawString("hover: " + hover, 99, 90);
			g2d.drawString("life: " + c.getLife(), 99, 105);
			g2d.drawString("energy: " + c.getEnergy(), 99, 120);
			g2d.drawOval((int) mouse.x - 2, (int) mouse.y - 2, 4, 4);
		}
	}

	public void reset() {
		level.reset();
	}

	public int getCharacter() {
		return level.getCharacterIndex();
	}

	public void save() {
		level.save();
	}

	public void load() {
		level.load();
	}
}
