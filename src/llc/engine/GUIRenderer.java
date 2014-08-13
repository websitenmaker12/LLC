package llc.engine;

import java.awt.Font;

import llc.engine.gui.GUI;

import org.newdawn.slick.TrueTypeFont;

public class GUIRenderer {

	private GUI currentGUI;
	public final TrueTypeFont arial = new TrueTypeFont(new Font("Arial", Font.ITALIC, 24), true);
	
	/**
	 * Renders the current GUI
	 */
	public void render(int x, int y) {
		if(this.currentGUI != null) {
			this.currentGUI.update(x, y);
			this.currentGUI.render(this, x, y);
		}
	}
	
	/**
	 * Opens the given GUI
	 */
	public void openGUI(GUI gui) {
		this.closeCurrentGUI();
		this.currentGUI = gui;
		this.currentGUI.onOpen();
	}
	
	/**
	 * Closes the current GUI
	 */
	public void closeCurrentGUI() {
		if(this.currentGUI != null) {
			this.currentGUI.onClose();
			this.currentGUI = null;
		}
	}
	
}
