package llc.engine.gui;

import llc.engine.GUIRenderer;

import org.newdawn.slick.Color;

public abstract class GUIText extends GUIElement {
	
	private String text;
	private Color color;
	private Long stopMark;
	
	public GUIText(GUI gui, float posX, float posY, String text, Color color) {
		super(gui, posX, posY, 0, 0);
		this.setText(text);
		this.color = color;
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		renderer.font.drawString(this.posX, this.posY, this.getText(), this.color);
	}
	
	@Override
	public void update(int x, int y) {}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	/**
	 * Sets the color of this elememt
	 * @param c the color
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	/**
	 * Marks this element for 1.5 seconds. The element decides how to handle this
	 */
	public void mark() {
		stopMark = System.currentTimeMillis() + 1500;
	}
	public boolean isMarked() {
		if (stopMark == null) {
			return false;
		}
		if (stopMark >= System.currentTimeMillis()) {
			return true;
		} else {
			stopMark = null;
			return false;
		}
	}
}
