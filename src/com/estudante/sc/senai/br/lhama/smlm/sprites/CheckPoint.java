package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZFile;
import com.estudante.sc.senai.br.lhama.smlm.ZKeyboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.function.Consumer;

public class CheckPoint extends Sprite {

	private static JSONArray reference = (JSONArray) ZFile.readJSON("configs/checkpoints.json");

	private int index;
	private String character;
	private int energy;
	private Consumer<Integer> change;

	@Override
	public boolean falls() {
		return false;
	}

	private static HashMap<String, String> getPaths() {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("swing", "sprites/flag#1");
		return hm;
	}

	private static String change(Sprite spr) {
		return "swing";
	}

	public void setChange(Consumer<Integer> c) {
		change = c;
	}

	public void collide(Character c, ZKeyboard k) {
		if (c.isOnGround()) {
			if (!k.pS && k.S) {
				change.accept(index);
				c.change(character, energy);
			}
			if (Math.random() < 0.03) {
				c.setLife(c.getLife() + 1);
			}
		}
	}

	public CheckPoint(double x, double y, int num) {
		super(getPaths(), CheckPoint::change, "swing", x, y, 128, 128);
		index = num;
		JSONObject obj = (JSONObject) reference.get(num);
		character = (String) obj.get("character");
		energy = ((Long) obj.get("energy")).intValue();
	}

	public int getIndex() {
		return index;
	}

	public String getCharacter() {
		return character;
	}

	public int getEnergy() {
		return energy;
	}

	@Override
	public String toString() {
		return "CheckPoint{" +
				"index=" + index +
				", character='" + character + '\'' +
				", energy=" + energy +
				", x=" + x +
				", y=" + y +
				", w=" + w +
				", h=" + h +
				'}';
	}
}
