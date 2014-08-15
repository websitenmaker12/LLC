package llc.engine.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import llc.engine.GUIRenderer;
import llc.loading.GameLoader;
import llc.logic.Logic;
import llc.util.RenderUtil;

public class GUIIngameMenu extends GUI{
	
	private GameLoader gameLoader;
	private Logic logic;
	
	public GUIIngameMenu(GameLoader gameLoader, Logic logic) {
		this.gameLoader = gameLoader;
		this.logic = logic;
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 165, 300, 40, "Back to game") {
			public void onClick(int x, int y) {
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 110, 300, 40, "Save") {
			public void onClick(int x, int y) {
				gameLoader.saveToFile(logic.getGameState(), "save.llcsav");
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 55, 300, 40, "Load") {
			public void onClick(int x, int y) {
				logic.setGameState(gameLoader.loadFromFile("save.llcsav"));
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2, 300, 40, "Exit") {
			public void onClick(int x, int y) {
			}
		});
		
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glPushMatrix();
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5f);
		RenderUtil.drawQuad(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glPopMatrix();
		
		renderer.font.drawString(Display.getWidth() / 2 - renderer.font.getWidth("LLC v. 0.1"), Display.getHeight() / 8, "LLC v. 0.1", Color.orange);
		
		super.render(renderer, x, y);
	}
}
