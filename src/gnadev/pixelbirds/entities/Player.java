package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.InputHandler;
import gnadev.pixelbirds.Main;
import gnadev.pixelbirds.gfx.Fonts;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;
import gnadev.pixelbirds.level.tiles.Tile;

import java.awt.Point;

public class Player extends Mob {

	private Level level;
	private InputHandler input;
	private int color = Colors.get(-1, 000, 555, 550);
	private int tickCount;
	private int walkingSpeed = 3;
	public static boolean isPaused = false;
	private PauseMenu pauseMenu;
	public boolean dead = false;
	private int timeLeft = (int) (5 * Main.getMaxTicks());
	private boolean won = false;
	int going = 0, DOWN = 1, UP = -1, STATIC = 0;
	private int currentLevel = 1;
	public int score = 0;
	long lastTimer;

	public Player(Level level, int x, int y, InputHandler input) {
		super(level, "Player", x, y, 2);
		this.level = level;
		this.input = input;
		this.pauseMenu = new PauseMenu(level, this);
		level.addEntity(this);

		lastTimer = System.currentTimeMillis();
	}

	public void tick() {
		if (Main.menu) {
			this.score = 0;
			this.setLocation(15, 15);
			this.dead = false;
		}

		if (Main.isPaused) {
			isPaused = true;
		}

		if (isPaused) {
			pauseMenu.tick();
		}
		if (getInput().ESCAPE.isPressed()) {
			getInput().ESCAPE.toggle(false);
			isPaused = !isPaused;
		}
		if (!isPaused) {
			this.going = STATIC;
			int xa = 0;
			int ya = 0;
			if (!dead) {
				if (getInput().DOWN.isPressed()) {
					this.going = DOWN;
					ya++;
				}
				if (getInput().UP.isPressed()) {
					this.going = UP;
					ya--;
				}
				xa = 1;
			} else {
				if (getInput().ACTION.isPressed() && dead) {
					this.level.changeLevel(Level
							.getRandomLevel(Level.levelPack));
					this.currentLevel = 1;
					reset();
				}
			}

			if (!dead) {
				if (xa != 0 || ya != 0) {
					move(xa, ya);
					isMoving = true;
				} else {
					isMoving = false;
				}
			}
		}

		if (level.getTile((this.x) >> 3, this.y >> 3).getID() == Tile.FINISH
				.getID()) {
			this.level.changeLevel(Level.getRandomLevel(Level.levelPack));
			this.currentLevel += 1;
			reset();
		}

		if (!won && hasCollided(1, 0)) {
			this.dead = true;
		}

		if (System.currentTimeMillis() - lastTimer > 5000) {
			lastTimer += 5000;
			if (!isPaused && !Main.isPaused && !won){
				this.score++;
			}
		}

		tickCount++;

	}

	public void render(Screen screen) {

		this.setSpeed(1);
		int xTile = 0;
		int yTile = 3;
		int flipTop = (numSteps >> walkingSpeed) & 1;

		if (!isPaused) {
			if (movingDir == 1) {
				xTile += 2;
			} else if (movingDir > 1) {
				xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
				flipTop = (movingDir - 1) % 2;
			}

			int modifier = 8 * scale;
			int xOffset = x - modifier / 2;
			int yOffset = y - modifier / 2 - 4;

			int tile = 0;
			if (this.going == DOWN) {
				tile = 4;
			} else if (this.going == UP) {
				if (tickCount % 60 < 10) {
					tile = 4;
				} else if (10 <= tickCount % 60 && tickCount % 60 < 20) {
					tile = 2;
				} else if (20 <= tickCount % 60 && tickCount % 60 < 30) {
					tile = 4;
				} else if (30 <= tickCount % 60 && tickCount % 60 < 40) {
					tile = 2;
				} else if (40 <= tickCount % 60 && tickCount % 60 < 50) {
					tile = 4;
				} else {
					tile = 2;
				}
			} else {
				if (tickCount % 60 < 15) {
					tile = 2;
				} else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
					tile = 0;
				} else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
					tile = 4;
				} else {
					tile = 0;
				}
			}

			if (!dead) {
				screen.render(xOffset, yOffset, 3 * 32 + tile, this.color,
						0x00, scale);
				screen.render(xOffset + scale * 8, yOffset, 3 * 32 + tile + 1,
						this.color, 0x00, scale);
			}
			if (dead) {
				screen.render(this.x + scale * 8, this.y, 6 + 3 * 32,
						this.color, 0x00, scale);

				Fonts.render("Press space...", screen, screen.xOffset
						+ (screen.width / 2)
						- ("Press space...".length() * 8 / 2), screen.yOffset
						+ (screen.height / 2) - 4, Colors.get(-1, -1, -1, 000),
						1);

				this.y++;

			}

			Fonts.render("Score: " + this.score, screen, screen.xOffset + 2,
					screen.yOffset + screen.height - 10,
					Colors.get(-1, -1, -1, 555), 1);

		}
		if (isPaused) {
			pauseMenu.render(screen);
		}
	}

	public boolean hasCollided(int xa, int ya) {
		int xMin = 0, xMax = 10, yMin = -2, yMax = -8;

		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}

		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}

		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}

		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}

		return false;
	}

	public void reset() {
		this.setLocation(15, 15);
		if (this.dead) {
			this.currentLevel = 1;
			this.score = 0;
		}
		this.level.changeLevel(Level.getRandomLevel(Level.levelPack));
		this.dead = false;
		this.won = false;
		this.timeLeft = (int) (5 * Main.getMaxTicks());
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputHandler getInput() {
		return input;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public void setLocation(Point loc) {
		this.x = loc.x;
		this.y = loc.y;
	}

	public void setLocation(int x, int y) {
		setLocation(new Point(x, y));
	}
}
