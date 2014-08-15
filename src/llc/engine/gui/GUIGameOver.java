package llc.engine.gui;

import llc.LLC;
import llc.engine.GUIRenderer;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIGameOver extends GUI{
	
	GUIRenderer renderer;
	
	public GUIGameOver() {
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2, 300, 40, "Close Game") {
			public void onClick(int x, int y) {
				LLC.getLLC().closeGame();
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 50, Display.getHeight() / 3, "Game Over", Color.orange) {});
		
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glPushMatrix();
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5f);
		RenderUtil.drawQuad(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glPopMatrix();
		
		super.render(renderer, x, y);
	}
}
