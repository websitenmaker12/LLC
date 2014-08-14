package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.engine.audio.EnumSounds;
import llc.engine.res.Texture;
import llc.util.RenderUtil;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public abstract class GUIButton extends GUIElement {

	private String text;
	
	private boolean isHover = false;
	private boolean isClicked = false;
	private boolean wasClicked = false;
	
	private static final Texture button;
	private static final Texture buttonHover;
	private static final Texture buttonDown;
	
	static {
		button = new Texture("res/gui/button_normal.png");
		buttonHover = new Texture("res/gui/button_hover.png");
		buttonDown = new Texture("res/gui/button_down.png");
	}
	
	public GUIButton(GUI gui, float posX, float posY, float width, float height, String text) {
		super(gui, posX, posY, width, height);
		this.text = text;
	}

	@Override
	public void update(int x, int y) {
		if(this.isClicked && !this.wasClicked) {
			this.onClick(x, y);
			//this.gui.audioEngine.playSound(EnumSounds.BUTTONCLICK);
		}
		this.wasClicked = this.isClicked;
		
		this.isHover = x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height;
		this.isClicked = this.isHover && Mouse.isButtonDown(0);
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		RenderUtil.clearColor();
		
		if(this.isClicked) buttonDown.bind();
		else if(this.isHover) buttonHover.bind();
		else button.bind();
		
		RenderUtil.drawTexturedQuad(this.posX, this.posY, this.width, this.height);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		TextureImpl.bindNone();
		renderer.font.drawString(this.posX + this.width / 2 - renderer.font.getWidth(this.text) / 2,
				this.posY + this.height / 2 - renderer.font.getHeight() / 2, this.text, Color.black);
	}

	/**
	 * Gets triggered when the button gets clicked
	 */
	public abstract void onClick(int x, int y);
	
}
