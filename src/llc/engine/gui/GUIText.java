package llc.engine.gui;

import llc.engine.GUIRenderer;

import org.newdawn.slick.Color;

public class GUIText extends GUIElement {
	
	private String text;
	private Color color;
	
	public GUIText(GUI gui, float posX, float posY, float width, float height, String text, Color color) {
		super(gui, posX, posY, width, height);
		this.setText(text);
		this.color = color;
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		renderer.font.drawString(this.posX + this.width / 2 - renderer.font.getWidth(this.text) / 2, this.posY, this.text, this.color);
	}
	
	@Override
	public void update(int x, int y) {  }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
