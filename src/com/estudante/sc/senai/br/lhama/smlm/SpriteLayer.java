package com.estudante.sc.senai.br.lhama.smlm;

import com.estudante.sc.senai.br.lhama.smlm.sprites.CheckPoint;
import com.estudante.sc.senai.br.lhama.smlm.sprites.Cloud;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Marcelo Vogt on 23/11/2017.
 */
public class SpriteLayer extends Layer {

	private ZList<Sprite> sprites;

	public SpriteLayer(JSONObject layer) {
		setName((String) layer.get("name"));
		setOpacity(((Number) layer.get("opacity")).floatValue());
		setVisible((boolean) layer.get("visible"));

		load(layer);

		setAc(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	protected void load(JSONObject layer) {
		sprites = new ZList<>();

		JSONArray sprs = (JSONArray) layer.get("objects");
		for (Object obj : sprs) {
			JSONObject spr = (JSONObject) obj;

			String name = (String) spr.get("name");
			long x = (long) spr.get("x");
			long y = (long) spr.get("y");

			Sprite s = null;
			try {
				s = Sprites.getInstance(name, x, y);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			sprites.add(s);
		}

	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, Character c, ZKeyboard k) {
		sprites.forEach(sprite -> sprite.update(lyr, sprs));
		collide(c, k);
	}

	private void collide(Character c, ZKeyboard k) {
		sprites.forEach(s -> {
			if(s.intersects(c)) {
				if(s instanceof CheckPoint) {
					((CheckPoint) s).collide(c, k);
				} else {
					s.collide(c);
				}
			}
		});

		sprites.forEachOther((s1, s2) -> {
			if(s1.intersects(s2)) {
				s1.collide(s2);
			}
		});
	}

	@Override
	public void draw(Graphics2D g2d, ZRect rect) {
		if(visible) {
			g2d.setComposite(ac);

			sprites.forEach(sprite -> {
				if(sprite.intersects(rect)) {
					sprite.draw(g2d);
				}
			});
		}
	}

	public ArrayList<CheckPoint> getCheckpoints() {
		ArrayList<CheckPoint> arr = new ArrayList<>();
		for (Sprite spr : sprites) {
			if(spr instanceof CheckPoint) {
				arr.add((CheckPoint) spr);
			}
		}
		return arr;
	}

	public ArrayList<Cloud> getClouds() {
		ArrayList<Cloud> arr = new ArrayList<>();
		for (Sprite spr : sprites) {
			if(spr instanceof Cloud) {
				arr.add((Cloud) spr);
			}
		}
		return arr;
	}
}
