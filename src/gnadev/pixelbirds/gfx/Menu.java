package gnadev.pixelbirds.gfx;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.InputHandler;
import gnadev.pixelbirds.Main;
import gnadev.pixelbirds.level.Level;

public class Menu {

	private int secondsLeft = 5;
	private int timeLeft = (int) (secondsLeft * Main.getMaxTicks());
	private int chosen = 0;
	private boolean animIsOver = false;
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
						Level.getRandomLevel(Level.levelPack);
						Main.menu = false;
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
			
			if (System.currentTimeMillis() - lastTimer > 50) {
				lastTimer += 50;
				
				if(logo_y < 30){
					logo_y++;
				}
			}
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
					s.render(0 + x * 8, 0 + y * 8, 0,
							Colors.get(0, 0, 0, 0), 0x00, 1);
				}
			}
			for (int i = 0; i < 5; i++) {
				s.render(s.width / 2 - 5 * 16 / 2 + i * 16,
						s.height / 2 - 8, i + 32,
						Colors.get(-1, 555, 005, -1), 0x00, 2);
			}
		} else {

			l.renderTiles(screen_m, this.x, this.y);
			
			int[] cols = new int[3];
			for (int i = 0; i < cols.length; i++) {
				cols[i] = Colors.get(444, -1, -1, 000);
			}

			cols[chosen] = Colors.get(555, -1, -1, 000);

			// 'Pixel Bird' Logo
			for (int i = 0; i < 7; i++) {
				screen_m.render(screen_m.width / 2 - 7 * 16 / 2 + i * 16, logo_y,
						2*32 + i, Colors.get(-1, 000, 050, -1), 0x00, 2);
			}
			
			// Buttons
			for(int i = 0; i < sel.length; i++){
				Fonts.render(sel[i], screen_m, screen_m.width / 2 - sel[i].length() * 8
						/ 2, 50+i*8, cols[i], 1);
			}
		}
	}

	public boolean animIsOver() {
		return animIsOver;
	}

}
