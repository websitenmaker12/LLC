package llc.engine.gui;

import llc.engine.GUIRenderer;

import org.newdawn.slick.Color;

public abstract class GUIText extends GUIElement {
	
	private String text;
	
	public GUIText(float posX, float posY, String text) {
		super(posX, posY);
		this.setText(text);
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		renderer.arial.drawString(this.posX, this.posY, this.getText(), Color.orange);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
