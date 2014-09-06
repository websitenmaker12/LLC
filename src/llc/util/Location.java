package llc.util;

import de.teamdna.databundle.DataBundle;
import de.teamdna.databundle.ISavable;

/**
 * Util to save cells when the GameState is known!
 * @author simolus3
 *
 */
public class Location implements ISavable{
	
	private int x, y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Location(DataBundle bundle) {
		this.x = bundle.getInt("x");
		this.y = bundle.getInt("y");
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "x: " + x + "; y: " + y;
	}
	@Override
	public void read(DataBundle arg0) {}

	@Override
	public void save(DataBundle bundle) {
		bundle.setInt("x", x);
		bundle.setInt("y", y);
	}
}
