package gnadev.pixelbirds.gfx;

public class Fonts {

	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-?!/.,"
			+ "1234567890': ";

	public String text;
	public int x, y, color;

	public static void render(String text, Screen screen, int x, int y, int color, int scale) {
		text = text.toUpperCase();

		for (int i = 0; i < text.length(); i++) {
			int charIndex = chars.indexOf(text.charAt(i));
			if (charIndex >= 0)
				screen.render(x + (i * 8), y, charIndex + 4 * 32, color, 0x00, scale);
		}
	}

}
