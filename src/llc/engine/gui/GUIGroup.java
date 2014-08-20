package llc.engine.gui;

import java.util.ArrayList;
import java.util.List;

import llc.engine.GUIRenderer;

public class GUIGroup extends GUIElement {

	private List<GUIElement> elements = new ArrayList<GUIElement>();
	private boolean isVisible = true;
	
	public GUIGroup(GUI gui) {
		super(gui, 0, 0, 0, 0);
	}
	
	@Override
	public void update(int x, int y) {
		for(GUIElement element : this.elements) element.update(x, y);
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		for(GUIElement element : this.elements) element.render(renderer, x, y);
	}

	/**
	 * Adds a new GUIElement to this GUIGroup
	 */
	public GUIGroup add(GUIElement element) {
		this.elements.add(element);
		return this;
	}
	
	/**
	 * Removes a GUIElement from this GUIGroup
	 */
	public GUIGroup remove(GUIElement element) {
		this.elements.remove(element);
		return this;
	}
	
	/**
	 * Returns whether this group is visible or not
	 */
	public boolean isVisible() {
		return this.isVisible;
	}

	/**
	 * Sets the visibility of this group
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
