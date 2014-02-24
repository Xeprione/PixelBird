package gnadev.pixelbirds.gfx;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.InputHandler;
import gnadev.pixelbirds.Main;
import gnadev.pixelbirds.entities.Player;
import gnadev.pixelbirds.level.Level;

import javax.swing.JOptionPane;

public class Menu {

	private int secondsLeft = 5;
	private int timeLeft = (int) (secondsLeft * Main.getMaxTicks());
	private int chosen = 0;
	private int gtChosen = 0;
	private int lpChosen = 0;
	private boolean animIsOver = false;
	private boolean choosingGameType = false;
	private boolean choosingLevelPack = false;
	private InputHandler i;
	private String[] sel = new String[2];
	private Screen s = null;
	private int x = 0, y = 0;
	private int logo_y = -8;
	long lastTimer;

	public Menu(InputHandler i) {
		this.i = i;
		sel[0] = "START  ";
		sel[1] = "EXIT   ";
		this.lastTimer = System.currentTimeMillis();
	}

	public void tick() {
		if (animIsOver()) {

			if (!choosingGameType && !choosingLevelPack) {
				if (i.UP.isPressed()) {
					i.UP.toggle(false);
					chosen -= 1;
				}
				if (i.DOWN.isPressed()) {
					i.DOWN.toggle(false);
					chosen += 1;
				}

				if (i.ACTION.isPressed()) {
					i.ACTION.toggle(false);
					if (chosen == 0) {
						this.choosingGameType = true;
					}
					if (chosen == 1) {
						System.exit(0);
					}
				}

				if (chosen == 2) {
					chosen = 0;
				}
				if (chosen == -1) {
					chosen = 1;
				}
				this.gtChosen = 0;
			} else if (choosingGameType && !choosingLevelPack) {
				if (i.UP.isPressed()) {
					i.UP.toggle(false);
					gtChosen -= 1;
				}
				if (i.DOWN.isPressed()) {
					i.DOWN.toggle(false);
					gtChosen += 1;
				}

				if (i.ACTION.isPressed()) {
					i.ACTION.toggle(false);
					if (gtChosen == 0) { // Default level pack
						this.gtChosen = 0;
						this.choosingGameType = false;
						Main.menu = false;
					}
				}

				if (i.ESCAPE.isPressed()) {
					this.choosingGameType = false;
				}

				if (gtChosen == 2) {
					gtChosen = 0;
				}
				if (gtChosen == -1) {
					gtChosen = 1;
				}
			}

			if (System.currentTimeMillis() - lastTimer > 50) {
				lastTimer += 50;

				if (logo_y < 30) {
					logo_y++;
				}
			}

			if (System.currentTimeMillis() - Player.lastTimer > 1000)
				Player.lastTimer += 1000;
		}
		if (!animIsOver()) {
			if (timeLeft != 0 && timeLeft > 0) {
				timeLeft -= 1;
			}
			if (timeLeft == 0) {
				animIsOver = true;
			}

			if (timeLeft < 0)
				timeLeft = 0;
		}
	}

	public void render(Screen screen_m, Level l) {
		if (this.s == null)
			this.s = screen_m;

		if (!animIsOver()) {
			for (int x = 0; x < s.width; x++) {
				for (int y = 0; y < s.height; y++) {
					s.render(0 + x * 8, 0 + y * 8, 0, Colors.get(0, 0, 0, 0),
							0x00, 1);
				}
			}
			for (int i = 0; i < 5; i++) {
				s.render(s.width / 2 - 5 * 16 / 2 + i * 16, s.height / 2 - 8,
						i + 32, Colors.get(-1, 555, 005, -1), 0x00, 2);
			}
		} else {

			l.renderTiles(screen_m, this.x, this.y);

			int[] cols = new int[3];
			for (int i = 0; i < cols.length; i++) {
				cols[i] = Colors.get(-1, -1, -1, 000);
			}

			cols[chosen] = Colors.get(225, -1, -1, 000);

			// 'Pixel Bird' Logo
			for (int i = 0; i < 7; i++) {
				screen_m.render(screen_m.width / 2 - 7 * 16 / 2 + i * 16,
						logo_y, 2 * 32 + i, Colors.get(-1, 000, 050, -1), 0x00,
						2);
			}

			// Buttons
			for (int i = 0; i < sel.length; i++) {
				Fonts.render(sel[i], screen_m,
						screen_m.width / 2 - sel[i].length() * 8 / 2,
						50 + i * 8, cols[i], 1);
			}

			if (choosingGameType) {
				// The background:
				int color = Colors.get(-1, 000, 335, -1);

				s.render(s.xOffset + 32, s.yOffset + 25, 5 + 32, color, 0x00, 1);

				s.render(s.xOffset + 32, s.yOffset + 65, 5 + 32, color, 0x06, 1); // Bottom
																					// left
																					// corner
				s.render(s.xOffset + s.width - 32, s.yOffset + 25, 5 + 32,
						color, 0x09, 1);
				s.render(s.xOffset + s.width - 32, s.yOffset + 65, 5 + 32,
						color, 0x03, 1);

				for (int x = 0; x < 11; x++) {
					for (int y = 0; y < 4; y++) {
						s.render(s.xOffset + 32, s.yOffset + 33 + y * 8, 38,
								color, 0x00, 1); // Left border
						s.render(s.xOffset + s.width - 32, s.yOffset + 33 + y
								* 8, 38, color, 0x03, 1); // Right border
						s.render(s.xOffset + 40 + x * 8, s.yOffset + 25, 39,
								color, 0x00, 1); // Top border
						s.render(s.xOffset + 40 + x * 8, s.yOffset + 65, 39,
								color, 0x03, 1); // Bottom border
						s.render(s.xOffset + 40 + x * 8,
								s.yOffset + 33 + y * 8, 40, color, 0x00, 1); // Between
					}
				}

				Fonts.render("Game type:", s, s.xOffset + 44, s.yOffset + 27,
						Colors.get(-1, -1, -1, 555), 1);
				Fonts.render("Default", s, s.xOffset + 35, s.yOffset + 35,
						Colors.get(-1, -1, -1, 555), 1);
				/*Fonts.render("Custom", s, s.xOffset + 35, s.yOffset + 43,
						Colors.get(-1, -1, -1, 555), 1);*/

				s.render(s.xOffset + s.width - 40, s.yOffset + 35
						+ this.gtChosen * 8, 41, Colors.get(-1, 555, -1, -1),
						0x03, 1);
			}
		}
	}

	public boolean animIsOver() {
		return animIsOver;
	}

}
