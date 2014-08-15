package llc.engine.gui;

import org.lwjgl.opengl.Display;

import llc.loading.GameLoader;
import llc.logic.Logic;

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
		
		this.elements.add(new GUIButton(this, Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 165, 200, 35, "Save") {
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

}
