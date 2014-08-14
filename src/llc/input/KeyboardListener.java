package llc.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {

	private Map<Integer, KeyBinding> keyBindings = new HashMap<Integer, KeyBinding>();
	private List<KeyBinding> updateKeys = new ArrayList<KeyBinding>();
	private List<IKeybindingListener> listeners = new ArrayList<IKeybindingListener>();
	
	/**
	 * Registers a new {@link KeyBinding}
	 */
	public void registerKeyBinding(KeyBinding keyBinding) {
		if(this.keyBindings.containsKey(keyBinding.key)) return;
		this.keyBindings.put(keyBinding.key, keyBinding);
	}
	
	/**
	 * Handles all KeyBinding presses. Don't call manually!
	 */
	public void update() {
		while(Keyboard.next()) {
			KeyBinding binding = this.keyBindings.get(Keyboard.getEventKey());
			boolean state = Keyboard.getEventKeyState();
			if(binding != null) {
				this.fireKeyEvent(binding, state);
				if(binding.canRepeat && !this.updateKeys.contains(binding)) {
					if(state) this.updateKeys.add(binding);
					else this.updateKeys.remove(binding);
				}
			}
		}
		
		for(KeyBinding binding : this.updateKeys) this.fireKeyEvent(binding, true);
	}
	
	/**
	 * Registers a new EventHandler
	 */
	public void registerEventHandler(IKeybindingListener listener) {
		this.listeners.add(listener);
	}
	
	private void fireKeyEvent(KeyBinding keyBinding, boolean isPressed) {
		for(IKeybindingListener listener : this.listeners) listener.onKeyBindingUpdate(keyBinding, isPressed);
	}
	
}
