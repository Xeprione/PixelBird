package gnadev.pixelbirds.level.tiles;

import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public class GroundTile extends Tile{
	
	protected int tileId, tileColor;

	public GroundTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x+y*32;
		this.tileColor=tileColor;
	}
	
	public void render(Screen screen, Level level, int x, int y){
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}

	public void tick() {
		
	}
	
	public void setColor(int color){
		this.tileColor = color;
	}

}
