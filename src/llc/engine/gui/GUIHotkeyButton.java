package llc.engine.gui;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import llc.LLC;
import llc.engine.GUIRenderer;
import llc.engine.gui.screens.GUI;
import llc.engine.res.Texture;
import llc.input.IKeyListener;
import llc.util.RenderUtil;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.TextureImpl;

public abstract class GUIHotkeyButton extends GUIElement implements IKeyListener {

	private int key;
	protected String desc;
	
	private boolean isHover = false;
	private boolean isClicked = false;
	private boolean wasClicked = false;
	private boolean isKeyDown = false;
	
	private static final Texture button;
	private static final Texture buttonHover;
	private static final Texture buttonDown;
	
	static {
		button = new Texture("res/gui/img_btn.png");
		buttonHover = new Texture("res/gui/img_btn_hover.png");
		buttonDown = new Texture("res/gui/img_btn_down.png");
	}
	
	public GUIHotkeyButton(GUI gui, float posX, float posY, float width, float height, String desc, int key) {
		super(gui, posX, posY, width, height);
		this.key = key;
		this.desc = desc;
		
		LLC.getLLC().keyboardListener.registerEventHandler(this);
	}

	@Override
	public void update(int x, int y) {
		if(this.isClicked && !this.wasClicked) this.onClick(x, y);
		this.wasClicked = this.isClicked;
		
		this.isHover = x >= this.posX && x <= this.posX + this.width && y >= this.posY && y <= this.posY + this.height;
		this.isClicked = (this.isHover && Mouse.isButtonDown(0)) || this.isKeyDown;
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		glEnable(GL_TEXTURE_2D);
		RenderUtil.clearColor();
		
		if(this.isClicked) buttonDown.bind();
		else if(this.isHover) buttonHover.bind();
		else button.bind();
		
		RenderUtil.drawTexturedQuad(this.posX, this.posY, this.width, this.height);
		
		TextureImpl.bindNone();
		String s = Keyboard.getKeyName(this.key);
		renderer.font.drawString(this.posX + this.width / 2 - renderer.font.getWidth(s) / 2, this.posY + this.height / 2 - renderer.font.getHeight() / 2, s);
		
		glDisable(GL_TEXTURE_2D);
		if(this.isHover) RenderUtil.drawHoverBox(x + 12, y + 12, false, renderer.font, this.desc);
	}

	/**
	 * Gets triggered when the button gets clicked
	 */
	public abstract void onClick(int x, int y);

	@Override
	public void onKeyUpdate(int key, boolean isPressed) {
		if(key == this.key) this.isKeyDown = isPressed;
	}
	
}
