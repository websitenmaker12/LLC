package llc.input;

public interface IKeybindingListener {

	/**
	 * This function is called when the state of the KeyBinding changes or
	 * the key is pressed
	 */
	void onKeyBindingUpdate(KeyBinding keyBinding, boolean isPressed);
	
}
