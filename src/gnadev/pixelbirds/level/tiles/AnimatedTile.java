package gnadev.pixelbirds.level.tiles;


public class AnimatedTile extends GroundTile {

	private int[][] animCoords;
	private int curAnimIndex;
	private long lastIterTime;
	private int delay;

	public AnimatedTile(int id, int[][] animCoords, int tileColor,
			int levelColor, int delay) {
		super(id, animCoords[0][0], animCoords[0][1], tileColor, levelColor);
		this.animCoords = animCoords;
		this.curAnimIndex = 0;
		this.lastIterTime = System.currentTimeMillis();
		this.delay = delay;
	}

	public void tick() {
		if ((System.currentTimeMillis() - lastIterTime) >= (delay)) {
			lastIterTime = System.currentTimeMillis();
			curAnimIndex = (curAnimIndex + 1) % animCoords.length;
			tileId = (animCoords[curAnimIndex][0]+(animCoords[curAnimIndex][1]*32));
		}
	}

}
