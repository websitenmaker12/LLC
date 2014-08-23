package llc.engine.gui.screens;

import llc.LLC;
import llc.engine.GUIRenderer;
import llc.engine.gui.EnumAnchor;
import llc.engine.gui.GUIButton;
import llc.engine.gui.GUIText;
import llc.loading.GameLoader;
import llc.logic.Logic;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIIngameMenu extends GUI{
	
	private GameLoader gameLoader;
	private Logic logic;
	
	GUIRenderer renderer;
	
	public GUIIngameMenu(GameLoader gameLoader, Logic logic) {
		this.gameLoader = gameLoader; 
		this.logic = logic;
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 230, 300, 40, "LLC - " + LLC.VERSION, Color.orange, EnumAnchor.MIDDLE));
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 165, 300, 40, "Back to game") {
			public void onClick(int x, int y) {
				LLC.getLLC().togglePauseMenu();
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 110, 145, 40, "Save") {
			public void onClick(int x, int y) {
				gameLoader.saveToFile(logic.getGameState(), "save.dss");
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 + 5, Display.getHeight() / 2 - 110, 145, 40, "Load") {
			public void onClick(int x, int y) {
				try {
					logic.setGameState(gameLoader.loadFromFile("save.dss"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 55, 300, 40, "Options") {
			public void onClick(int x, int y) {
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 150, Display.getHeight() / 2, 300, 40, "Exit") {
			public void onClick(int x, int y) {
				LLC.getLLC().closeGame();
			}
		});
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5f);
		RenderUtil.drawQuad(0, 0, Display.getWidth(), Display.getHeight());
		RenderUtil.clearColor();
		
		super.render(renderer, x, y);
	}
}
