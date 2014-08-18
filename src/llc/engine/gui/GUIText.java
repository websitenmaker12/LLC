package llc.engine.gui;

import llc.engine.GUIRenderer;

import org.newdawn.slick.Color;

public class GUIText extends GUIElement {
	
	private String text;
	private Color color;
	
	private long markerTime;
	private Color markerColor;
	
	public GUIText(GUI gui, float posX, float posY, float width, float height, String text, Color color) {
		super(gui, posX, posY, width, height);
		this.setText(text);
		this.color = color;
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		renderer.font.drawString(this.posX + this.width / 2 - renderer.font.getWidth(this.text) / 2, this.posY, this.text, this.isMarked() ? this.markerColor : this.color);
	}
	
	@Override
	public void update(int x, int y) {  }

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
	 * @param i 
	 * @param red 
	 */
	public void mark(Color color, long l) {
		this.markerTime = System.currentTimeMillis() + l;
		this.markerColor = color;
	}
	
	public boolean isMarked() {
		return this.markerTime > System.currentTimeMillis();
	}
}
