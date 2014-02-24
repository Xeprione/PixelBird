package gnadev.pixelbirds;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

	public InputHandler(Main m) {
		m.addKeyListener(this);
	}

	public class Key {
		public boolean pressed = false;

		public void toggle(boolean isPressed) {
			pressed = isPressed;
		}

		public boolean isPressed() {
			return pressed;
		}
	}

	public Key UP = new Key();
	public Key DOWN = new Key();
	public Key LEFT = new Key();
	public Key RIGHT = new Key();
	public Key ACTION = new Key();
	public Key ESCAPE = new Key();
	public Key FULLSCREEN = new Key();
	public Key ENTER = new Key();
	public Key CTRL = new Key();
	public Key Q = new Key(), W = new Key(), E = new Key(), R = new Key(),
			T = new Key(), Y = new Key(), U = new Key(), I = new Key(),
			O = new Key(), P = new Key(), A = new Key(), S = new Key(),
			D = new Key(), F = new Key(), G = new Key(), H = new Key(),
			J = new Key(), K = new Key(), L = new Key(), Z = new Key(),
			X = new Key(), C = new Key(), V = new Key(), B = new Key(),
			N = new Key(), M = new Key(), ERASE = new Key();

	public void keyPressed(KeyEvent evt) {
		toggleKey(evt.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent evt) {
		toggleKey(evt.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent evt) {
	}

	public void toggleKey(int kc, boolean isPressed) {
		if (kc == KeyEvent.VK_W || kc == KeyEvent.VK_UP)
			UP.toggle(isPressed);
		if (kc == KeyEvent.VK_A || kc == KeyEvent.VK_LEFT)
			LEFT.toggle(isPressed);
		if (kc == KeyEvent.VK_S || kc == KeyEvent.VK_DOWN)
			DOWN.toggle(isPressed);
		if (kc == KeyEvent.VK_D || kc == KeyEvent.VK_RIGHT)
			RIGHT.toggle(isPressed);
		if (kc == KeyEvent.VK_SPACE)
			ACTION.toggle(isPressed);
		if (kc == KeyEvent.VK_ESCAPE)
			ESCAPE.toggle(isPressed);
	}
}
