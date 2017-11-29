package com.estudante.sc.senai.br.lhama.smlm;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Marcelo Vogt on 26/07/2017.
 */
public class ZImage implements Drawable {

	private Image image;
	private ZRect rect;

	public ZImage(String path) {
		if (path != null) {
			setImage(getClass().getClassLoader().getResource(path));
		}
	}

	public ZImage(Image img) {
		rect = new ZRect();
		image = img;
	}

	public ZImage(ZImage img) {
		rect = new ZRect();
		image = img.getImage();
	}

	public ZImage(ZRect rect, String path) {
		if(this.getClass().equals(ZImage.class)) {
			path = "images/" + path + ".png";
		}
		setImage(getClass().getClassLoader().getResource(path));
		this.rect = rect;
	}

	public ZImage(ZRect rect, Image img) {
		this.rect = rect;
		image = img;
	}

	public ZImage(ZRect rect, ZImage img) {
		this.rect = rect;
		image = img.getImage();
	}

	public void setImage(ZImage image) {
		if (image.getImage() != null) {
			this.image = image.getImage();
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setImage(URL path) {
		rect = new ZRect();
		try {
			image = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		rect.setW(image.getWidth(null));
		rect.setH(image.getHeight(null));
	}

	public void setY(double y) {
		rect.y = y;
	}

	public double getY() {
		return rect.y;
	}

	public int getWidth() {
		return getBufferedImage().getWidth();
	}

	public int getHeight() {
		return getBufferedImage().getHeight();
	}

	public Image getImage() {
		return image;
	}

	public BufferedImage getBufferedImage() {
		return (BufferedImage) image;
	}

	public ZRect getRect() {
		return rect;
	}

	public void draw(Graphics2D g2d, int x, int y) {
		g2d.drawImage(image, x, y, null);
	}

	public void draw(Graphics2D g2d) {
		if (rect == null) {
			draw(g2d, 0, 0);
		} else {
			rect.draw(g2d, this);
			if (this.getClass().equals(ZImage.class) && SMLM.DEBUG_MODE) {
				rect.drawBorder(g2d, Color.GREEN);
			}
		}
	}

}