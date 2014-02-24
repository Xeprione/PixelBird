package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public abstract class Entity {
	
	public int x, y;
	protected Level level;
	private String name;
	
	public Entity(Level level){
		init(level);
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	public abstract void render(Screen screen);

	public int getLevelColor() {
		return 0;
	}
	
	protected void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}
