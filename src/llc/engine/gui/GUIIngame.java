package llc.engine.gui;

import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.logic.Logic;

import org.lwjgl.opengl.Display;

public class GUIIngame extends GUI {

	private Logic logic;
	
	public GUIIngame(Logic logic) {
		this.logic = logic;
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(20, Display.getHeight() - 55, 200, 35, "Buy Warrior") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWarrior());
			}
		});
		
		this.elements.add(new GUIButton(20, Display.getHeight() - 110, 200, 35, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWorker());
			}
		});
		
		this.elements.add(new GUIText(20, 20, "Gold: ") {
			@Override
			public void update(int x, int y) {
				setText("Gold:" + logic.getGameState().getActivePlayer().getMinerals());
			}
		});
		
		this.elements.add(new GUIText(Display.getWidth() - 200, 20, "Player: ") {
			@Override
			public void update(int x, int y) {
				setText("Player " + logic.getGameState().getActivePlayer().playerID);
			}
		});
	}
	
}
