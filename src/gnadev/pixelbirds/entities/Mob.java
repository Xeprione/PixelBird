package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.level.Level;
import gnadev.pixelbirds.level.tiles.Tile;

public abstract class Mob extends Entity {

	public static final Mob[] mobs = new Mob[256];

	protected String name;
	public int x;
	public int y;
	protected int speed;
	protected int movingDir = 1;
	protected boolean isMoving;
	protected int scale = 1;
	protected int numSteps;
	protected byte id;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.setName(name);
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			if (ya < 0)
				movingDir = 0;
			if (ya > 0)
				movingDir = 1;
			if (xa < 0)
				movingDir = 2;
			if (xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null)
			return false;
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3,
				(this.y + y + ya) >> 3);
		if (!lastTile.equals(newTile) && newTile.isSolid())
			return true;

		return false;
	}

	public String getName() {
		return name;
	}

	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public byte getID() {
		return id;
	}

}
