package llc.engine.gui;

import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.loading.GameLoader;
import llc.logic.Logic;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class GUIIngame extends GUI {

	private Logic logic;
	private GameLoader gameLoader;
	
	public GUIIngame(Logic logic, GameLoader gameLoader) {
		this.logic = logic;
		this.gameLoader = gameLoader;
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(this, 20, Display.getHeight() - 55, 200, 35, "Buy Warrior") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWarrior());
			}
		});
		
		this.elements.add(new GUIButton(this, 20, Display.getHeight() - 110, 200, 35, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWorker());
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() - 220, Display.getHeight() - 110, 200, 35, "Save") {
			public void onClick(int x, int y) {
				gameLoader.saveToFile(logic.getGameState(), "save.llcsav");
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() - 220, Display.getHeight() - 55, 200, 35, "Load") {
			public void onClick(int x, int y) {
				logic.setGameState(gameLoader.loadFromFile("save.llcsav"));
			}
		});
		
		this.elements.add(new GUIText(this, 20, 20, "Gold: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Gold:" + logic.getGameState().getActivePlayer().getMinerals());
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() - 110, 20, "Player: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Player " + logic.getGameState().getActivePlayer().playerID);
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 40, 20, "Turns left: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Turns left: " + (logic.subTurns - logic.getGameState().moveCount) + "/" + logic.subTurns);
			}
		});
	}
	
}
