package llc.engine;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Font;

import llc.engine.audio.SoundEngine;
import llc.engine.gui.screens.GUI;
import llc.input.Input;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class GUIRenderer {

	public TrueTypeFont font;
	
	private GUI currentGUI;
	private Input input;
	private SoundEngine soundEngine;
	
	public GUIRenderer(Input input, SoundEngine audioEngine) {
		this.input = input;
		this.soundEngine = audioEngine;
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
			if(this.currentGUI != null) this.currentGUI.render(this, x, y);
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
			this.currentGUI.soundEngine = this.soundEngine;
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
		
		float scale = (float)Display.getWidth() / 680F;
		try {
			this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/PTM55FT.ttf")).deriveFont(Math.min(14F * scale, 25)), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current GUI
	 */
	public GUI getGUI() {
		return this.currentGUI;
	}
	
}
