package llc.input;

import org.lwjgl.input.Keyboard;

import llc.LLC;
import llc.logic.Cell;
import llc.logic.Logic;

/**
 * @author simolus3
 */
public class HotkeyManager implements IKeybindingListener{
	
	private Cell[] bindingsPlayer1 = new Cell[9];
	private Cell[] bindingsPlayer2 = new Cell[9];
	
	public Logic l;
	
	public HotkeyManager(Logic l) {
		LLC llc = LLC.getLLC();
		KeyboardListener listener = llc.keyboardListener;
		//Set up keys
		listener.registerKeyBinding(new KeyBinding("hotbar.1", Keyboard.KEY_1, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.2", Keyboard.KEY_2, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.3", Keyboard.KEY_3, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.4", Keyboard.KEY_4, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.5", Keyboard.KEY_5, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.6", Keyboard.KEY_6, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.7", Keyboard.KEY_7, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.8", Keyboard.KEY_8, false));
		listener.registerKeyBinding(new KeyBinding("hotbar.9", Keyboard.KEY_9, false));
		
		listener.registerEventHandler(this);
	}
	/**
	 * <b>Important</b> Key 1 has index 0, key 2 index 1 and so on!
	 * @param index
	 * @param c
	 */
	public void set(int index, Cell c, int playerID) {
		if (playerID == 1) {
			bindingsPlayer1[index] = c;
		} else {
			bindingsPlayer2[index] = c;
		}
	}
	public Cell get(int index, int playerID) {
		if (playerID == 1) {
			return bindingsPlayer1[index];
		} else {
			return bindingsPlayer2[index];
		}
	}
	@Override
	public void onKeyBindingUpdate(KeyBinding keyBinding, boolean isPressed) {
		if (!isPressed) {
			return;
		}
		if (keyBinding.name.startsWith("hotbar.")) {
			boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
			int i = Integer.parseInt(keyBinding.name.substring(7))-1;
			if (shift) {
				//Set
				if (l.getGameState().hoveredCell != null) {
					set(i, l.getGameState().hoveredCell, l.getGameState().activePlayer);
				}
			} else {
				//Get
				Cell c = get(i, l.getGameState().activePlayer);
				if (c != null) {
					l.getGameState().focusCell(c, true);
				}
			}
		}
	}
}
