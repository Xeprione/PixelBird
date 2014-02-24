package gnadev.pixelbirds.level.tiles;

public class SolidTile extends GroundTile{

	public SolidTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		this.solid = true;
	}

}
