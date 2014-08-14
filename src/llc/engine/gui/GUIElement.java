package llc.engine.gui;

import llc.engine.GUIRenderer;

public abstract class GUIElement {
	
	public float posX;
	public float posY;
	public float width;
	public float height;

	public GUIElement(float posX, float posY, float widht, float height) {
		this.posX = posX;
		this.posY = posY;
		this.width = widht;
		this.height = height;
	}
	
	/**
	 * Is called for input updates
	 */
	public abstract void update(int x, int y);
	
	/**
	 * Is called for render updates
	 */
	public abstract void render(GUIRenderer renderer, int x, int y);

	/**
	 */
	public boolean isHovered(int x, int y) {
		return x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height;
	}
	
}
