package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;


public class SMLM extends Game {
	public static final ZPoint TARGET_SIZE = new ZPoint(1920, 1080);
	public static boolean DEBUG_MODE = false;
	public static ZMode mode = ZMode.CENTER;
	public static final double GRAVITY = 0.6;
	public static final int TILE_SIZE = 64;

	private ZMouse mouse;
	private ZKeyboard keyboard;
	private ZKeyboard kb;

	private ArrayList<ArrayList<ZClip>> musics;

	private HashMap<Screens, Screen> screens;
	private Screens currentScreen = Screens.GAME;

	private void nextMusic() {
		int charac = ((DynamicScreen) screens.get(currentScreen)).getCharacter();
		ZClip c = musics.get(2).get((int) (Math.random() * 3));
		int i = c.play();
		c.wait(i, this::nextMusic);
	}

	public SMLM() {
		super();
		addKeyListener(new KeyboardHandler());
		addMouseListener(new MouseHadler());
		addMouseMotionListener(new MouseHadler());
	}

	public static void main(String[] args) {
		new SMLM().startGame();
	}

	@Override
	public void init() {
		mouse = new ZMouse();
		keyboard = new ZKeyboard();
		kb = new ZKeyboard();

		musics = new ArrayList<>(4);

		for (int i = 0; i < 4; i++) {
			musics.add(new ArrayList<>(3));

			String p = "musics/" + Character.names.get(i) + "/";

			for (int j = 0; j < 3; j++) {
				ZClip c = new ZClip(p + j);
				musics.get(i).add(c);
			}
		}

		try {

			screens = new HashMap<>();

			ZImage background = new ZImage("screens/background.png");

			{
				ArrayList<ZButton> buttons = new ArrayList<>();

				buttons.add(new ZButton(
						new ZRect(864, 800, 197, 81),
						"btn_start",
						() -> currentScreen = Screens.GAME
				));

				buttons.add(new ZButton(
						new ZRect(1152, 800, 256, 81),
						"btn_options",
						() -> currentScreen = Screens.OPTIONS
				));

				buttons.add(new ZButton(
						new ZRect(1504, 800, 320, 81),
						"btn_controls",
						() -> currentScreen = Screens.CONTROLS
				));

				buttons.add(new ZButton(
						new ZRect(1616, 950, 96, 67),
						"btn_quit",
						() -> System.exit(0)
				));

				ArrayList<ZImage> imgs = new ArrayList<>();

				imgs.add(new ZImage(
						new ZRect(732, 292, 1033, 237),
						"logo_original"
				));

				StaticScreen main = new StaticScreen(background, buttons, imgs);

				screens.put(Screens.MAIN, main);
			}

			{
				ArrayList<ZButton> buttons = new ArrayList<>();

				buttons.add(new ZButton(
						new ZRect(1264, 800, 256, 81),
						"btn_resume",
						() -> currentScreen = Screens.OPTIONS
				));

				buttons.add(new ZButton(
						new ZRect(1584, 800, 320, 81),
						"btn_controls",
						() -> currentScreen = Screens.CONTROLS
				));

				buttons.add(new ZButton(
						new ZRect(1616, 950, 96, 67),
						"btn_quit",
						() -> System.exit(0)
				));

				ArrayList<ZImage> imgs = new ArrayList<>();

				imgs.add(new ZImage(
						new ZRect(732, 292, 1033, 237),
						"logo_pause"
				));

				StaticScreen pause = new StaticScreen(background, buttons, imgs);

				screens.put(Screens.PAUSE, pause);
			}

			{
				ArrayList<ZButton> buttons = new ArrayList<>();

				buttons.add(new ZButton(
						new ZRect(1280, 950, 448, 67),
						"btn_reset",
						() -> {

						}
				));

				buttons.add(new ZButton(
						new ZRect(1696, 950, 113, 67),
						"btn_back",
						() -> currentScreen = Screens.MAIN
				));

				ArrayList<ZImage> imgs = new ArrayList<>();

				imgs.add(new ZImage(
						new ZRect(732, 292, 1033, 237),
						"logo_pause_bw"
				));

				StaticScreen options = new StaticScreen(background, buttons, imgs);

				screens.put(Screens.OPTIONS, options);
			}

			{

				ArrayList<ZButton> buttons = new ArrayList<>();

				buttons.add(new ZButton(
						new ZRect(1696, 950, 113, 67),
						"btn_back",
						() -> currentScreen = Screens.MAIN
				));

				ArrayList<ZImage> imgs = new ArrayList<>();

				imgs.add(new ZImage(
						new ZRect(732, 292, 1033, 237),
						"logo_pause_bw"
				));

				StaticScreen controls = new StaticScreen(background, buttons, imgs);

				screens.put(Screens.CONTROLS, controls);
			}

			screens.put(Screens.GAME, new DynamicScreen("level1"));

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		ZImage img = new ZImage("images/blank.png");
		Image i = img.getImage();
		Point p = new Point(0, 0);
		addCursor("none", i, p);

		ZClip clip = musics.get(2).get(0);
		int j = clip.play();
		clip.wait(j, this::nextMusic);
	}

	@Override
	public void gameLoop() {
		Screen current = screens.get(currentScreen);
		kb.update(keyboard);
		if(current instanceof DynamicScreen) {
			((DynamicScreen) current).update(kb, mouse);
			setCursor("none");
		} else {
			((StaticScreen) current).update(mouse);
		}
		current.draw(g2d);
	}

	private class KeyboardHandler extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					keyboard.W(true);
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					keyboard.A(true);
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					keyboard.S(true);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					keyboard.D(true);
					break;
				case KeyEvent.VK_1:
				case KeyEvent.VK_7:
					keyboard.B17(true);
					break;
				case KeyEvent.VK_2:
				case KeyEvent.VK_8:
					keyboard.B28(true);
					break;
				case KeyEvent.VK_3:
				case KeyEvent.VK_9:
					keyboard.B39(true);
					break;
				case KeyEvent.VK_4:
				case KeyEvent.VK_0:
					keyboard.B40(true);
					break;
				case KeyEvent.VK_SPACE:
					keyboard.SPACE(true);
					break;
				case KeyEvent.VK_ESCAPE:
				case KeyEvent.VK_P:
					keyboard.ESCP(true);
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					keyboard.W(false);
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					keyboard.A(false);
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					keyboard.S(false);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					keyboard.D(false);
					break;
				case KeyEvent.VK_1:
				case KeyEvent.VK_7:
					keyboard.B17(false);
					break;
				case KeyEvent.VK_2:
				case KeyEvent.VK_8:
					keyboard.B28(false);
					break;
				case KeyEvent.VK_3:
				case KeyEvent.VK_9:
					keyboard.B39(false);
					break;
				case KeyEvent.VK_4:
				case KeyEvent.VK_0:
					keyboard.B40(false);
					break;
				case KeyEvent.VK_SPACE:
					keyboard.SPACE(false);
					break;
				case KeyEvent.VK_ESCAPE:
				case KeyEvent.VK_P:
					keyboard.ESCP(false);
					break;
				case KeyEvent.VK_F12:
					DEBUG_MODE = !DEBUG_MODE;
					break;
				case KeyEvent.VK_R:
					Screen current = screens.get(currentScreen);
					if(current instanceof DynamicScreen) {
						((DynamicScreen) current).resetCharacter();
					}
					break;
			}
		}
	}

	private class MouseHadler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			mouse.set(e);
			mouse.click = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouse.set(e);
			mouse.click = false;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouse.set(e);
		}
	}

}
