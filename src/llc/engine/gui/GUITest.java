package llc.engine.gui;

public class GUITest extends GUI {

	@Override
	public void onOpen() {
		this.elements.add(new GUIButton(80, 80, 100, 20, "Test"));
	}
	
}
