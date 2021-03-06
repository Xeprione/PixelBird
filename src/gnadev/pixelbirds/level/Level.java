package gnadev.pixelbirds.level;

import gnadev.pixelbirds.entities.Entity;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.tiles.Tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Level {

	public static String[] defaultPack = new String[9];
	public static String[] currentLevelPack;

	public byte[] tiles;

	public int width;
	public int height;

	public List<Entity> ent = new ArrayList<Entity>();

	private String imagePath = null;
	private BufferedImage image = null;
	private BufferedImage gen = null;

	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}
	}

	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0,
				width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null
							&& t.getLevelColor() == tileColors[x + y * width]) {
						if (t.getLevelColor() != tileColors[x + y * width]) {
							this.tiles[x + y * width] = Tile.SKY.getID();
						} else if (t.getLevelColor() == tileColors[x + y
								* width]) {
							this.tiles[x + y * width] = t.getID();
						}
						break tileCheck;
					}
				}
			}
		}
	}

	public void changeLevel(String newLevel) {
		if (imagePath != null) {
			this.tiles = null;
			this.imagePath = newLevel;
			this.loadLevelFromFile();
		}
	}

	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x + y * width] = newTile.getID();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void saveLevel() {
		try {
			ImageIO.write(gen, "png",
					new File(Level.class.getResource("/Levels/gen.png")
							.getFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (yOffset < 0)
			yOffset = 0;
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	public void renderEntities(Screen screen) {
		for (Entity e : ent) {
			e.render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if (0 > x || 0 > y || height <= y || y == height - 1)
			return Tile.WALL;
		if (width <= x)
			return Tile.SKY;
		if (x == width - 1)
			return Tile.FINISH;
		return Tile.tiles[tiles[x + y * width]];
	}

	public void tick() {
		for (Entity e : ent) {
			e.tick();
		}

		for (Tile t : Tile.tiles) {
			if (t == null) {
				break;
			} else {
				t.tick();
			}
		}
	}

	public void addEntity(Entity e) {
		this.ent.add(e);
	}

	public static String getRandomLevel(String[] levelPack) {
		int nr;
		while (true) {
			nr = (int) (Math.random() * 10);
			if (nr < levelPack.length) {
				break;
			} else {
				continue;
			}
		}
		return levelPack[nr];
	}

	static {
		defaultPack[0] = "/levels/level_1.png";
		defaultPack[1] = "/levels/level_2.png";
		defaultPack[2] = "/levels/level_3.png";
		defaultPack[3] = "/levels/level_4.png";
		defaultPack[4] = "/levels/level_5.png";
		defaultPack[5] = "/levels/level_6.png";
		defaultPack[6] = "/levels/level_7.png";
		defaultPack[7] = "/levels/level_8.png";
		defaultPack[8] = "/levels/level_9.png";
	}

	public static File[] getLevelPacks() {
		File packsRoot = new File(System.getProperty("user.home")
				+ "/PixelBird/LevelPacks");
		if (!packsRoot.exists()) {
			packsRoot.mkdirs();
		}
		int levelPackFiles = packsRoot.listFiles().length;
		int levelPacksCount = 0;
		for (File f : packsRoot.listFiles()) {
			boolean isLevelPack = new File(f.getPath() + "/data.txt").exists();
			if (f.isDirectory() && isLevelPack)
				levelPacksCount++;
		}
		File[] levelPacks = new File[levelPacksCount];
		for (int i = 0; i < levelPacksCount; i++) {
			levelPacks[i] = packsRoot.listFiles()[i];
		}
		return levelPacks;
	}

	public static String[] getLevelPackNames() {
		File[] levelPacks = getLevelPacks();
		String[] levelPackNames = new String[levelPacks.length];

		// TODO: Read the data file to get the pack name

		for (int i = 0; i < levelPacks.length; i++) {
			levelPackNames[i] = levelPacks[i].getName();
		}

		return levelPackNames;
	}

}
