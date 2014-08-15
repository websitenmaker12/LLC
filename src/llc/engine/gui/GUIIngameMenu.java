package llc.engine.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

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
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 165, 200, 35, "Back to game") {
			public void onClick(int x, int y) {
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 110, 200, 35, "Save") {
			public void onClick(int x, int y) {
				gameLoader.saveToFile(logic.getGameState(), "save.llcsav");
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 55, 200, 35, "Load") {
			public void onClick(int x, int y) {
				logic.setGameState(gameLoader.loadFromFile("save.llcsav"));
			}
		});
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glPushMatrix();
		GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.5f);
		RenderUtil.drawQuad(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glPopMatrix();
		super.render(renderer, x, y);
	}
}
