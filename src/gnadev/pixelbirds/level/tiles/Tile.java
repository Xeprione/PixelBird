package gnadev.pixelbirds.level.tiles;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public abstract class Tile {
	
	public static Tile[] tiles = new Tile[256];
	
	public static final Tile SKY = new GroundTile(0, 0, 0,Colors.get(445, 445, 445, 445), 0xffffffff);
	public static final Tile WALL = new SolidTile(1, 0, 0, Colors.get(-1,421,531,-1), 0xff000000);
	public static final Tile FINISH = new GroundTile(2, 2, 0, Colors.get(000,555,-1,-1), 0xff00ff00);
	public static final Tile GROUND = new SolidTile(3, 3, 0, Colors.get(-1,-1,-1,-1), 0x00);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	
	private int levelColor;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor){
		this.id = (byte) id;
		if(tiles[id] != null){
			throw new RuntimeException("Duplicate Tile ID on "+id);
		}
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id] = this;
	}
	
	public byte getID(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public void setSolid(boolean isSolid){
		this.solid = isSolid;
	}
	
	public void setEmitter(boolean isEmitter){
		this.emitter = isEmitter;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public int getLevelColor() {
		return levelColor;
	}

}
