package llc.engine.gui;

import org.lwjgl.opengl.Display;

public class GUIIngame extends GUI {

	@Override
	public void onOpen() {
		this.elements.add(new GUIButton(0, Display.getHeight() - 55, 200, 55, "Buy Warrior") {
			public void onClick(int x, int y) {
			}
		});
	}
	
}
