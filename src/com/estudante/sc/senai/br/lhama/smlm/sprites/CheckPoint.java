package com.estudante.sc.senai.br.lhama.smlm.sprites;

import com.estudante.sc.senai.br.lhama.smlm.Character;
import com.estudante.sc.senai.br.lhama.smlm.Sprite;
import com.estudante.sc.senai.br.lhama.smlm.ZFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class CheckPoint extends Sprite {

	private static JSONArray reference = (JSONArray) ZFile.readJSON("configs/checkpoints.json");

	private int index;
	private String character;
	private int energy;

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

	@Override
	public void collide(Character c) {
		if(Math.random() < 0.03) {
			c.setLife(c.getLife() + 1);
		}
		c.setEnergy(energy);
		c.change(character);
		c.setEnergy(energy);
	}

	public CheckPoint(double x, double y) {
		super(getPaths(), CheckPoint::change, "swing", x, y, 128, 128);
	}

	public CheckPoint(Sprite cp, double x, double y, int num) {
		super(cp, x, y);
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
}
