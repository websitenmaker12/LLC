package llc.engine.gui;

import llc.engine.GUIRenderer;

import org.newdawn.slick.Color;

public abstract class GUIText extends GUIElement {
	
	private String text;
	private Color color;
	
	public GUIText(GUI gui, float posX, float posY, String text, Color color) {
		super(gui, posX, posY, 0, 0);
		this.setText(text);
		this.color = color;
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		renderer.font.drawString(this.posX, this.posY, this.getText(), this.color);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
