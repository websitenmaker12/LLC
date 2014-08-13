package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.engine.res.Texture;
import llc.util.RenderUtil;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public abstract class GUIButton extends GUIElement {

	private float width;
	private float height;
	private String text;
	
	private boolean isHover = false;
	private boolean isClicked = false;
	
	private static final Texture button;
	private static final Texture buttonHover;
	private static final Texture buttonDown;
	
	static {
		button = new Texture("res/gui/button_normal.png");
		buttonHover = new Texture("res/gui/button_hover.png");
		buttonDown = new Texture("res/gui/button_down.png");
	}
	
	public GUIButton(float posX, float posY, float width, float height, String text) {
		super(posX, posY);
		this.width = width;
		this.height = height;
		this.text = text;
	}

	@Override
	public void update(int x, int y) {
		if(this.isClicked) this.onClick(x, y);
		
		this.isHover = x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height;
		this.isClicked = this.isHover && Mouse.isButtonDown(0);
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		if(this.isClicked) buttonDown.bind();
		else if(this.isHover) buttonHover.bind();
		else button.bind();
		RenderUtil.drawTexturedQuad(this.posX, this.posY, this.width, this.height);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		TextureImpl.bindNone();
		renderer.arial.drawString(this.posX + this.width / 2 - renderer.arial.getWidth(this.text) / 2,
				this.posY + this.height / 2 - renderer.arial.getHeight() / 2, this.text, Color.black);
	}

	/**
	 * Gets triggered when the button gets clicked
	 */
	public abstract void onClick(int x, int y);
	
}
