package llc.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import de.teamdna.databundle.DataBundle;
import de.teamdna.databundle.ISavable;
import llc.LLC;
import llc.logic.Cell;
import llc.logic.GameState;
import llc.logic.Player;
import llc.util.Location;

/**
 * @author simolus3
 */
public class HotkeyManager implements IKeybindingListener, ISavable{

	private HashMap<Player, Location[]> bindings = new HashMap<Player, Location[]>();
	
	private GameState state;
	
	public HotkeyManager(GameState state) {
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
		this.state = state;
	}
	/**
	 * <b>Important</b> Key 1 has index 0, key 2 index 1 and so on!
	 * @param index
	 * @param c
	 */
	public void set(int index, Cell c, Player player) {
		if (!bindings.containsKey(player)) {
			bindings.put(player, new Location[9]);
		}
		
		Location l = bindings.get(player)[index];
		if (l == null) {
			l = new Location(c.x, c.y);
		} else {
			l.setX(c.x);
			l.setY(c.y);
		}
		
		bindings.get(player)[index] = l;
		System.out.println(l);
	}
	
	public Cell get(int index, Player player) {
		Location ls[] = bindings.get(player);
		//Prevent Nullpointer Exception
		if (ls == null || ls[index] == null) {
			return null;
		}
		Location l = ls[index];
		System.out.println(l);
		return state.getGrid().getCellAt(l.getX(), l.getY());
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
				if (state.hoveredCell != null) {
					set(i, state.hoveredCell, state.activePlayer);
				}
			} else {
				//Get
				Cell c = get(i, state.activePlayer);
				if (c != null) {
					LLC.getLLC().getCamera().focusCell(c, true);
				}
			}
		}
	}
	
	//Saving and Loading:
	//Every Player has an own bundle: That bundle contains 9 child-bundles which contain the location
	
	public HotkeyManager(DataBundle bundle, GameState gameState) {
		state = gameState;

		List<Player> players = state.getPlayers();
		bindings = new HashMap<Player, Location[]>();
		for (int i = 0; i < players.size(); i++) {
			if (!bundle.hasTag("players_" + i)) {
				//Player has never set a hotkey!
				bindings.put(players.get(i), new Location[9]);
				continue;
			}
			DataBundle pd = bundle.getBundle("players_" + i);
			Location[] ls = new Location[9];
			if (pd != null) {
				for (int li = 0; li < ls.length; li++) {
					if (!pd.hasTag("keys_" + li)) {
						continue;
					}
					DataBundle keyB = pd.getBundle("keys_" + li);
					if (keyB.hasTag("isNull")) {
						continue;
					}
					ls[li] = new Location(keyB);
				}
			}
			bindings.put(players.get(i), ls);
		}
	}
	@Override
	public void read(DataBundle arg0) {}
	
	@Override
	public void save(DataBundle data) {
		int pi = -1; //Not actually pi, player-index
		for (Entry<Player, Location[]> entry : bindings.entrySet()) {
			pi++;
			DataBundle playerBundle = new DataBundle();
			
			for (int i = 0; i < entry.getValue().length; i++) {
				DataBundle keyBundle = new DataBundle();
				Location l = entry.getValue()[i];
				if (l == null) {
					keyBundle.setBoolean("isNull", true);
				} else {
					l.save(keyBundle);
				}
				playerBundle.setBundle("keys_" + i, keyBundle);
			}
			data.setBundle("players_" + pi, playerBundle);
		}
	}
}
