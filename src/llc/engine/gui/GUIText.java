package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.engine.gui.screens.GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public class GUIText extends GUIElement {
	
	private String text;
	private Color color;
	private EnumAnchor anchor;
	
	private long markerTime;
	private Color markerColor;
	
	public GUIText(GUI gui, float posX, float posY, float width, float height, String text, Color color) {
		this(gui, posX, posY, width, height, text, color, EnumAnchor.LEFT);
	}
	
	public GUIText(GUI gui, float posX, float posY, float width, float height, String text, Color color, EnumAnchor anchor) {
		super(gui, posX, posY, width, height);
		this.setText(text);
		this.color = color;
		this.anchor = anchor;
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		TextureImpl.bindNone();
		switch(this.anchor) {
			case LEFT:
				renderer.font.drawString(this.posX, this.posY, this.text, this.isMarked() ? this.markerColor : this.color);
				break;
			case MIDDLE:
				renderer.font.drawString(this.posX + this.width / 2 - renderer.font.getWidth(this.text) / 2, this.posY, this.text, this.isMarked() ? this.markerColor : this.color);
				break;
			case RIGHT:
				renderer.font.drawString(this.posX + this.width - renderer.font.getWidth(this.text), this.posY, this.text, this.isMarked() ? this.markerColor : this.color);
				break;
		}
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
