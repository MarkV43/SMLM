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

	public SpriteLayer(Level l, JSONObject layer) {
		setName((String) layer.get("name"));
		setOpacity(((Number) layer.get("opacity")).floatValue());
		setVisible((boolean) layer.get("visible"));

		load(l, layer);

		setAc(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	private void load(Level l, JSONObject layer) {
		sprites = new ZList<>();

		JSONArray sprs = (JSONArray) layer.get("objects");
		for (Object obj : sprs) {
			JSONObject spr = (JSONObject) obj;

			String name = (String) spr.get("name");
			double x = ((Number) spr.get("x")).doubleValue();
			double y = ((Number) spr.get("y")).doubleValue();

			Sprite s = null;
			try {
				s = Sprites.getInstance(l, name, x, y);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			sprites.add(s);
		}

	}

	public void update(TileLayer lyr, ArrayList<Sprite> sprs, Character c, ZKeyboard k, boolean clouds) {
		sprites.forEach(sprite -> sprite.update(lyr, sprs, clouds));
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
				} else if(sprite.canShoot()) {
					sprite.getBullets().forEach(b -> {
						if(b != null) {
							b.draw(g2d);
						}
					});
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

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public void reset() {
		for (Sprite spr : sprites) {
			spr.reset();
		}
	}
}
