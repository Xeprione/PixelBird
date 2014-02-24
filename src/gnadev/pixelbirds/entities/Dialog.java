package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.InputHandler;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public class Dialog extends Entity {

	private static Level _level;
	private static InputHandler _input;

	public static TextDialog[] textDialogs = new TextDialog[5];
	public static TextDialog _empty_inv = new TextDialog(_level, _input,
			"You'r inventory is not empty.");
	public static TextDialog _dead = new TextDialog(_level, _input,
			"You died! Press   space to respawn.");

	public Dialog(Level l, InputHandler i) {
		super(l);
	}

	public void showTextDialog(TextDialog d, Screen s) {
		d.render(s);
	}

	public void tick() {
	}

	public void render(Screen screen) {
	}

}
