package llc;

import org.lwjgl.LWJGLException;

public class Main {

	public static void main(String[] args) {
		LLC game = new LLC();
		try {
			game.startGame();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}

