package llc.engine;

import java.awt.Font;

import llc.engine.audio.AudioEngine;
import llc.engine.gui.GUI;
import llc.input.Input;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class GUIRenderer {

	public TrueTypeFont font;
	
	private GUI currentGUI;
	private Input input;
	private AudioEngine audioEngine;
	
	public GUIRenderer(Input input, AudioEngine audioEngine) {
		this.input = input;
		this.audioEngine = audioEngine;
		
		try {
			this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/PTM55FT.ttf")).deriveFont(20F), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Renders the current GUI
	 */
	public void render(int width, int height, int x, int y) {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		
		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		if(this.currentGUI != null) {
			this.currentGUI.update(x, y);
			this.currentGUI.render(this, x, y);
		}

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}
	
	/**
	 * Opens the given GUI
	 */
	public void openGUI(GUI gui) {
		this.closeCurrentGUI();
		this.currentGUI = gui;
		
		if(gui != null) {
			this.currentGUI.onOpen();
			this.input.guiChange(this.currentGUI);
			this.currentGUI.audioEngine = this.audioEngine;
		} else {
			this.input.guiChange(null);
		}
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
	
	/**
	 * Gets called when the Display was resized
	 */
	public void handleDisplayResize(int width, int height) {
		if(this.currentGUI != null) this.currentGUI.onOpen();
	}

	/**
	 * Returns the current GUI
	 */
	public GUI getGUI() {
		return this.currentGUI;
	}
	
}
