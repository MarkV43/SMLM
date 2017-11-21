package br.senai.sc.engine;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Game extends Canvas {
	private static final long serialVersionUID = 6058040659371962305L;
	private static final int TARGET_FRAMERATE = 60;
	protected JFrame container;
	private JPanel panel;
	protected BufferStrategy strategy;
	protected boolean gameRunning = true;
	private boolean sairAoTermino = false;
	protected Graphics2D g2d;
	protected Fps fps;
	private Map<String, Mp3> musicas;
	private Map<String, CustomFont> customFonts;
	private Map<String, Cursor> cursors;

	public Game() {
		Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();
		Utils.getInstance().setHeight(fullscreen.height);
		Utils.getInstance().setWidth(fullscreen.width);
		container = new JFrame(Utils.getInstance().getNomeJogo());
		container.setUndecorated(true);
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(Utils.getInstance().getWidth(), Utils.getInstance().getHeight()));
		panel.setLayout(null);
		setBounds(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
		panel.add(this);
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(false);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fps = new Fps(TARGET_FRAMERATE);
		musicas = new HashMap<>();
		customFonts = new HashMap<>();
		init();
		container.setVisible(true);
		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	public Game(String nomeJogo, int width, int height) {
		Utils.getInstance().setNomeJogo(nomeJogo);
		Utils.getInstance().setHeight(height);
		Utils.getInstance().setWidth(width);
		container = new JFrame(Utils.getInstance().getNomeJogo());
		container.setUndecorated(true);
		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(Utils.getInstance().getWidth(), Utils.getInstance().getHeight()));
		panel.setLayout(null);
		setBounds(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
		panel.add(this);
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(false);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fps = new Fps(TARGET_FRAMERATE);
		musicas = new HashMap<>();
		customFonts = new HashMap<>();
		cursors = new HashMap<>();
		init();
		container.setLocationRelativeTo(null);
		container.setVisible(true);
		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	public abstract void init();

	public void startGame() {
		while (gameRunning) {
			g2d = (Graphics2D) strategy.getDrawGraphics();
			fps.updateFPS();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, Utils.getInstance().getWidth(), Utils.getInstance().getHeight());
			gameLoop();
			g2d.dispose();
			strategy.show();
			fps.synchronize(true);
		}

		aposTermino();
		if (sairAoTermino) {
			System.exit(0);
		}

	}

	public void drawGif(Image image, int x, int y) {
		g2d.drawImage(image, x, y, container);
	}

	public void drawImage(Image image, int x, int y) {
		g2d.drawImage(image, x, y, (ImageObserver) null);
	}

	public void drawString(String mensagem, int x, int y) {
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(mensagem, x, y + fm.getAscent());
	}

	public void drawString(String mensagem, int x, int y, Color color) {
		g2d.setColor(color);
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(mensagem, x, y + fm.getAscent());
	}

	public void drawString(String mensagem, int x, int y, Color color, int fontSize) {
		g2d.setColor(color);
		g2d.setFont(new Font("Arial", 0, fontSize));
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(mensagem, x, y + fm.getAscent());
	}

	public void drawString(String mensagem, int x, int y, Color color, Font font) {
		g2d.setColor(color);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(mensagem, x, y + fm.getAscent());
	}

	public void drawString(String mensagem, int x, int y, Color color, int fontSize, Font font) {
		g2d.setColor(color);
		g2d.setFont(font.deriveFont((float) fontSize));
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(mensagem, x, y + fm.getAscent());
	}

	public Image loadImage(String path) {
		return Utils.getInstance().loadImage(path);
	}

	public void endGame() {
		gameRunning = false;
	}

	public void aposTermino() {}

	public abstract void gameLoop();

	public void exitOnClose() {
		sairAoTermino = true;
	}

	public void alterFPS(int fps) {
		this.fps = new Fps(fps);
	}

	public void drawRect(int x, int y, int width, int height, Color color) {
		g2d.setColor(color);
		g2d.fillRect(x, y, width, height);
	}

	public void drawCircle(int x, int y, int width, int height, Color color) {
		g2d.setColor(color);
		g2d.fillOval(x, y, width, height);
	}

	public void addNewFont(String name, String path, float size, int style) {
		CustomFont cf = new CustomFont(path, size, style);
		customFonts.put(name, cf);
	}

	public void setFont(String name) {
		g2d.setFont(((CustomFont) customFonts.get(name)).getCustomFont());
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public int getWidth() {
		return Utils.getInstance().getWidth();
	}

	public int getHeight() {
		return Utils.getInstance().getHeight();
	}

	public Font getFont(String fontName) {

		return customFonts.get(fontName).getCustomFont();

	}

	public void addCursor(String name, Image img, Point hotSpot) {

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, name);

		cursors.put(name, cursor);

	}

	public void setCursor(String cursorName) {

		if(cursors.containsKey(cursorName)) {
			panel.setCursor(cursors.get(cursorName));
		}

	}
}
