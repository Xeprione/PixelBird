package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.Main;
import gnadev.pixelbirds.gfx.Fonts;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public class PauseMenu extends Dialog {

	private int chosen = 0;
	private String[] opt = new String[3];
	private Player p;

	public PauseMenu(Level l, Player p) {
		super(l, p.getInput());
		this.p = p;
		opt[0] = "Resume";
		opt[1] = "Restart";
		opt[2] = "Exit";
	}

	public void tick() {

		if (p.getInput().UP.isPressed()) {
			p.getInput().UP.toggle(false);
			this.setChosen(this.getChosen() - 1);
		}
		if (p.getInput().DOWN.isPressed()) {
			p.getInput().DOWN.toggle(false);
			this.setChosen(this.getChosen() + 1);
		}
		if (p.getInput().ACTION.isPressed()) {
			p.getInput().ACTION.toggle(false);
			if (chosen == 0) {
				Player.isPaused = false;
			}
			if(chosen == 1){
				Player.isPaused = false;
				this.p.score = 0;
				this.p.reset();
			}
			if (chosen == 2) {
				Player.isPaused = false;
				level.getRandomLevel(Level.defaultPack);
				this.p.reset();
				p.setLocation(15,15);
				this.chosen = 0;
				Main.menu = true;
			}
		}

		if (chosen < 0)
			chosen = 2;
		if (chosen > 2)
			chosen = 0;

	}

	public void render(Screen s) {
		int color = Colors.get(-1, 000, 335, -1);
		
		s.render(s.xOffset + 32, s.yOffset + 5, 5+32, color, 0x00, 1);
		s.render(s.xOffset + 32, s.yOffset + s.height - 16, 5+32, color,
				0x06, 1);
		s.render(s.xOffset + s.width - 32, s.yOffset + 5, 5+32, color,
				0x09, 1);
		s.render(s.xOffset + s.width - 32, s.yOffset + s.height - 16,
				5+32, color, 0x03, 1);

		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				s.render(s.xOffset + 40 + x * 8, s.yOffset + 13 + y * 8,
						8+ 32, color, 0x00, 1);
				s.render(s.xOffset + 40 + x * 8, s.yOffset + 5, 7+ 32,
						color, 0x00, 1);
				s.render(s.xOffset + 40 + x * 8, s.yOffset + s.height - 16,
						7+ 32, color, 0x03, 1);
				
				s.render(s.xOffset + 32, s.yOffset + 13 + y * 8, 6+ 32,
						color, 0x00, 1);
				
				s.render(s.xOffset + s.width - 32, s.yOffset + 13 + y * 8,
						6+ 32, color, 0x03, 1); // Right border side
			}
		}

		for (int i = 0; i < opt.length; i++) {
			Fonts.render(opt[i], s, s.xOffset + 50, s.yOffset + 15 + i * 20,
					Colors.get(-1, -1, -1, 000), 1);
		}

		s.render(s.xOffset + 35, s.yOffset + 15 + chosen * 20, 9+32,
				Colors.get(-1, 555, -1, -1), 0x00, 1);
	}

	public int getChosen() {
		return chosen;
	}

	public void setChosen(int chosen) {
		this.chosen = chosen;
	}

}
