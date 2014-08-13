package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.util.RenderUtil;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

public class GUIButton extends GUIElement {

	private float width;
	private float height;
	private String text;
	
	private boolean isHover = false;
	private boolean isClicked = false;
	
	public GUIButton(float posX, float posY, float width, float height, String text) {
		super(posX, posY);
		this.width = width;
		this.height = height;
		this.text = text;
	}

	@Override
	public void update(int x, int y) {
		this.isHover = x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height;
		this.isClicked = this.isHover && Mouse.isButtonDown(0);
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		RenderUtil.drawQuad(this.posX, this.posY, this.width, this.height);
		renderer.arial.drawString(this.posX + this.width / 2 - renderer.arial.getWidth(this.text) / 2,
				this.posY + this.height / 2 - renderer.arial.getHeight() / 2, this.text, Color.black);
	}

}
