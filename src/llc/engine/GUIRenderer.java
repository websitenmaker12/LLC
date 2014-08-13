package llc.engine;

import llc.engine.gui.GUI;

public class GUIRenderer {

	private GUI currentGUI;
	
	/**
	 * Renders the current GUI
	 */
	public void render(int x, int y) {
		if(this.currentGUI != null) {
			this.currentGUI.update(x, y);
			this.currentGUI.render(x, y);
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
