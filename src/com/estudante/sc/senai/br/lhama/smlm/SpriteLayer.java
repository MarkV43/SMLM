package com.estudante.sc.senai.br.lhama.smlm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class SpriteLayer extends Layer {

	private ArrayList<Sprite> sprites;

	public SpriteLayer(JSONObject layer) {
		setName((String) layer.get("name"));
		setOpacity(((Number) layer.get("opacity")).floatValue());
		setVisible((boolean) layer.get("visible"));

		load(layer);

		setAc(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	protected void load(JSONObject layer) {
		sprites = new ArrayList<>();

		JSONArray sprs = (JSONArray) layer.get("objects");
		for (Object obj : sprs) {
			JSONObject spr = (JSONObject) obj;

			String name = (String) spr.get("name");
			long x = (long) spr.get("x");
			long y = (long) spr.get("y");

			Sprite s = Sprites.getInstance(name, x, y);
			sprites.add(s);
		}

	}

	@Override
	public void draw(Graphics2D g2d, ZRect rect) {
		if(visible) {
			g2d.setComposite(ac);

			sprites.forEach(sprite -> sprite.draw(g2d));
		}
	}
}
