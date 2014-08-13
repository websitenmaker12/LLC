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
		this.elements.add(new GUIButton(0, Display.getHeight() - 55, 200, 55, "Buy Warrior") {
			public void onClick(int x, int y) {
				logic.buyEntity("Warrior");
			}
		});
		
		this.elements.add(new GUIButton(0, Display.getHeight() - 120, 200, 55, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity("Worker");
			}
		});
	}
	
}
