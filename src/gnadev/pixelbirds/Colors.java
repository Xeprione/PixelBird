package gnadev.pixelbirds;

public class Colors {

	public static int get(int black, int darkGray, int lightGray, int white) {
		return (get(white) << 24) + (get(lightGray) << 16)
				+ (get(darkGray) << 8) + (get(black));
	}

	private static int get(int color) {
		if (color < 0)
			return 255;
		int r = color / 100 % 10;
		int g = color / 10 % 10;
		int b = color % 10;

		return r * 36 + g * 6 + b;
	}
}
