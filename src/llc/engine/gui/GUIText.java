package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.util.RenderUtil;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public abstract class GUIText extends GUIElement {

	private float width;
	private float height;
	private String text;
	
	public GUIText(float posX, float posY, String text) {
		super(posX, posY);
		this.setText(text);
	}

	
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		RenderUtil.clearColor();
		
		TextureImpl.bindNone();
		renderer.arial.drawString(this.posX/* + this.width / 2 - renderer.arial.getWidth(this.getText()) / 2*/,
				this.posY/* + this.height / 2 - renderer.arial.getHeight() / 2*/, this.getText(), Color.orange);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
