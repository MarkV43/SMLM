package com.estudante.sc.senai.br.lhama.smlm;

import br.senai.sc.engine.Game;

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
	public static final int GRAVITY = 10;
	public static final int TILE_SIZE = 64;

	private Level level;

	private ZMouse mouse;

	private HashMap<Screens, Screen> screens;
	private Screens currentScreen = Screens.MAIN;

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
		try {
			level = new Level("level1");

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
	}

	@Override
	public void gameLoop() {
		Screen current = screens.get(currentScreen);
		if(current instanceof DynamicScreen) {
			((DynamicScreen) current).update(mouse);
		} else {
			((StaticScreen) current).update(mouse);
		}
		current.draw(g2d);
//		level.update();
//		level.draw(g2d);
//		c.run();
//		c.draw(g2d);
	}

	private class KeyboardHandler extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
//					c.up(true);
					break;
				case KeyEvent.VK_DOWN:
//					c.down(true);
					break;
				case KeyEvent.VK_LEFT:
//					c.left(true);
					break;
				case KeyEvent.VK_RIGHT:
//					c.right(true);
					break;
				case KeyEvent.VK_R:
//					c.x = 64;
//					c.y = 64;
//					c.xv = 0;
//					c.yv = 0;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
//					c.up(false);
					break;
				case KeyEvent.VK_DOWN:
//					c.down(false);
					break;
				case KeyEvent.VK_LEFT:
//					c.left(false);
					break;
				case KeyEvent.VK_RIGHT:
//					c.right(false);
					break;
				case KeyEvent.VK_F12:
					DEBUG_MODE = !DEBUG_MODE;
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
