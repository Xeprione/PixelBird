package gnadev.pixelbirds;

import gnadev.pixelbirds.entities.Player;
import gnadev.pixelbirds.gfx.Menu;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.gfx.SpriteSheet;
import gnadev.pixelbirds.level.Level;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String TITLE = "Pixel bird";

	public JFrame f;

	public boolean running = false;
	public static boolean menu = true;
	public static boolean isPaused = false;

	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer())
			.getData();
	private int[] colors = new int[6 * 6 * 6];

	public Screen screen;
	public Screen screen_m;
	public InputHandler input;
	public static Level level;
	public static Level level_menu;
	public Toolkit tk;
	public Menu m;
	private static double maxTicks = 60D;
	private Player player;

	public Main() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		f = new JFrame(TITLE);
		f.setDefaultCloseOperation(3);
		f.setLayout(new BorderLayout());
		f.add(this, BorderLayout.CENTER);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void init() {

		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colors[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/spritesheet.png"));
		screen_m = new Screen(WIDTH, HEIGHT,
				new SpriteSheet("/spritesheet.png"));
		input = new InputHandler(this);
		m = new Menu(input);
		level_menu = new Level("/levels/level_1.png");
		level = new Level(Level.getRandomLevel(Level.levelPack));
		player = new Player(level, 15, 15, input);
		level.addEntity(player);
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / getMaxTicks();

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean srender = false;

			while (delta >= 1) {
				tick();
				delta -= 1;
				srender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (srender) {
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 2500) {
				lastTimer += 2500;
			}
		}
	}

	public void tick() {

		if (!f.isFocused()) {
			isPaused = true;
		}

		if (f.isFocused()) {
			isPaused = false;
		}

		if (menu) {
			m.tick();
			level_menu.tick();
		} else
			level.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		if (!menu) {
			int xOffset = player.x - (screen.width / 2);
			int yOffset = player.y - (screen.height / 2);

			level.renderTiles(screen, xOffset, yOffset);
			level.renderEntities(screen);

			for (int y = 0; y < screen.height; y++) {
				for (int x = 0; x < screen.width; x++) {
					int colorCode = screen.pixels[x + y * screen.width];
					if (colorCode < 255)
						pixels[x + y * WIDTH] = colors[colorCode];
				}
			}
		} else {
			for (int y = 0; y < screen_m.height; y++) {
				for (int x = 0; x < screen_m.width; x++) {
					int colorCode = screen_m.pixels[x + y * screen_m.width];
					if (colorCode < 255) {
						pixels[x + y * WIDTH] = colors[colorCode];
					}
				}
			}
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		if (menu) {
			m.render(screen_m, level_menu);
		}

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Main().start();
	}
	
	public static double getMaxTicks(){
		return maxTicks;
	}

}
