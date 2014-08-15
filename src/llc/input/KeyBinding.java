package llc.input;

import org.lwjgl.input.Keyboard;

public class KeyBinding {

	public final String name;
	public final int key;
	public final boolean canRepeat;
	
	public KeyBinding(String name, int key, boolean canRepeat) {
		this.name = name;
		this.key = key;
		this.canRepeat = canRepeat;
	}
	
	/**
	 * Returns whether the Key is pressed or not
	 */
	public boolean isPressed() {
		return Keyboard.isKeyDown(this.key);
	}
	
}
