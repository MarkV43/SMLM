package com.estudante.sc.senai.br.lhama.smlm;

import org.json.simple.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.ArrayList;

public abstract class Layer {

	protected float opacity;
	protected boolean visible;
	protected AlphaComposite ac;
	protected String name;

	public abstract void draw(Graphics2D g2d, ZRect rect);

	public String getName() {
		return name;
	}

	public static Layer getInstance(Level lvl, ZTileMap tileMap, JSONObject layer, int tileSize) {
		Layer l = null;
		if(layer.get("type").equals("tilelayer")) {
			l = new TileLayer(tileMap, layer, tileSize);
		} else if(layer.get("type").equals("objectgroup")) {
			l = new SpriteLayer(lvl, layer);
		}
		return l;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AlphaComposite getAc() {
		return ac;
	}

	public void setAc(AlphaComposite ac) {
		this.ac = ac;
	}
}
