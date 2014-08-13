package llc.engine.gui;

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
				logic.buyEntity("Warrior");
			}
		});
		
		this.elements.add(new GUIButton(20, Display.getHeight() - 110, 200, 35, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity("Worker");
			}
		});
	}
	
}
