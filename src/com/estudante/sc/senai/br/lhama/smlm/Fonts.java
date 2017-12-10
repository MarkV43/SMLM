package com.estudante.sc.senai.br.lhama.smlm;

import java.awt.*;
import java.io.InputStream;

public class Fonts {
	private Fonts() {}

	public static Font bit8;

	public static void init() {
		try {
			bit8 = load("/fonts/8bit.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Font load(String path) throws Exception {
		InputStream is = Fonts.class.getResourceAsStream(path);
		return Font.createFont(Font.TRUETYPE_FONT, is);
	}
}
