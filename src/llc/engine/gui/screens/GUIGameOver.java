package llc.engine.gui.screens;

import llc.LLC;
import llc.engine.GUIRenderer;
import llc.engine.gui.GUIButton;
import llc.engine.gui.GUIText;
import llc.logic.Player;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIGameOver extends GUI {
	
	private Player winner;
	
	public GUIGameOver(Player winner) {
		this.winner = winner;
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 140, 300, 0, "Game Over", Color.orange));
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 100, 300, 0, String.valueOf(this.winner.getName()) + " wins!", Color.orange));
	
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2, 300, 40, "New Game") {
			public void onClick(int x, int y) {
				LLC.getLLC().startNewGame();
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 + 60, 300, 40, "Close Game") {
			public void onClick(int x, int y) {
				LLC.getLLC().closeGame();
			}
		});
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
