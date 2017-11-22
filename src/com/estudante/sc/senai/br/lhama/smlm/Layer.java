package com.estudante.sc.senai.br.lhama.smlm;

import org.json.simple.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.ArrayList;

public abstract class Layer {

	protected float opacity;
	protected boolean visible;
	protected boolean collision;
	protected AlphaComposite ac;
	protected String name;

	public abstract void draw(Graphics2D g2d, ZRect rect);

	public String getName() {
		return name;
	}

	public static Layer getInstance(ZTileMap tileMap, JSONObject layer, int tileSize) {
		Layer l = null;
		if(layer.get("type").equals("tilelayer")) {
			l = new TileLayer(tileMap, layer, tileSize);
		} else {
			//TODO implement SpriteLayer class
//			l = new SpriteLayer();
		}
		return l;
	}
}
