package gnadev.pixelbirds.entities;

import gnadev.pixelbirds.Colors;
import gnadev.pixelbirds.InputHandler;
import gnadev.pixelbirds.InputHandler.Key;
import gnadev.pixelbirds.gfx.Fonts;
import gnadev.pixelbirds.gfx.Screen;
import gnadev.pixelbirds.level.Level;

public class TextDialog extends Dialog {

	private InputHandler input;
	private boolean showing = false;
	private String text;
	private boolean closed = true;
	public Key close = null;

	public TextDialog(Level level, InputHandler input, String text) {
		super(level, input);
		this.input = input;
		this.text = text;
	}

	public void tick() {
		if (input.E.isPressed()) {
			this.showing = false;
		}
	}

	public void render(Screen screen) {
		int color = Colors.get(-1, 000, 050, -1);
		for (int i = 0; i < screen.width - 16; i += 8) {
			screen.render(screen.xOffset + 8 + i, screen.yOffset
					+ screen.height / 3 * 2, 6 + 29 * 32, color, 0x00, 1);
			screen.render(screen.xOffset + 8 + i, screen.yOffset
					+ screen.height - 8, 6 + 29 * 32, color, 0x3, 1);
		}

		for (int i = 0; i < 3; i++) {
			screen.render(screen.xOffset, screen.yOffset + screen.height / 3
					* 2 + 8 + i * 8, 5 + 29 * 32, color, 0x00, 1);
			screen.render(screen.xOffset + screen.width - 8, screen.yOffset
					+ screen.height / 3 * 2 + 8 + i * 8, 5 + 29 * 32, color,
					0x01, 1);
		}

		for (int x = 0; x < screen.width - 16; x += 8) {
			for (int y = 0; y < 3 * 8; y += 8) {
				screen.render(screen.xOffset + 8 + x, screen.yOffset
						+ screen.height - 4 * 8 + y, 7 + 29 * 32, color, 0x00,
						1);
			}
		}

		screen.render(screen.xOffset, screen.yOffset + screen.height - 8,
				4 + 29 * 32, color, 0x6, 1);
		screen.render(screen.xOffset + screen.width - 8, screen.yOffset
				+ screen.height - 8, 4 + 29 * 32, color, 0x3, 1);
		screen.render(screen.xOffset, screen.yOffset + screen.height / 3 * 2,
				4 + 29 * 32, color, 0x00, 1);
		screen.render(screen.xOffset + screen.width - 8, screen.yOffset
				+ screen.height / 3 * 2, 4 + 29 * 32, color, 0x9, 1);
		if (!(this.text.length() > 19)) {
			Fonts.render(this.text, screen, screen.xOffset + 8, screen.yOffset
					+ screen.height / 3 * 2 + 8, Colors.get(-1, -1, -1, 0), 1);
		}else{
			String row1 = "";
			String row2 = "";
			String row3 = "";
			for(int i = 0; i < this.text.length(); i++){
				if(i <= 17){
					row1 = row1+this.text.charAt(i);
				}
				if(i == 17 && this.text.charAt(18) != ' '){
					row1 = row1+this.text.charAt(i)+"-";
				}
				
				if(i == 18 && this.text.charAt(i) != ' '){
					row2 = row2+this.text.charAt(i);
				}
				if(i >= 19 && i <= 34){
					row2 = row2+this.text.charAt(i);
				}
				
				if(i == 35 && this.text.charAt(36) != ' '){
					row2 = row2+this.text.charAt(i)+"-";
				}
				if(i >= 36 && i < 52){
					row3 = row3+this.text.charAt(i);
				}
			}
			Fonts.render(row1, screen, screen.xOffset+6, screen.yOffset+screen.height/3*2+8, Colors.get(-1, -1, -1, 000), 1);
			Fonts.render(row2, screen, screen.xOffset+6, screen.yOffset+screen.height/3*2+8+9, Colors.get(-1, -1, -1, 000), 1);
			Fonts.render(row3, screen, screen.xOffset+6, screen.yOffset+screen.height/3*2+8+9+9, Colors.get(-1, -1, -1, 000), 1);
		}
			
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isShowing() {
		return showing;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
