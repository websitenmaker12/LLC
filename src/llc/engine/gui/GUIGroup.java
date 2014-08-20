package llc.engine.gui;

import java.util.ArrayList;
import java.util.List;

import llc.engine.GUIRenderer;

public class GUIGroup extends GUIElement {

	private List<GUIElement> elements = new ArrayList<GUIElement>();
	private boolean isVisible;
	
	public GUIGroup(GUI gui) {
		this(gui, true);
	}
	
	public GUIGroup(GUI gui, boolean isVisible) {
		super(gui, 0, 0, 0, 0);
		this.isVisible = isVisible;
	}
	
	@Override
	public void update(int x, int y) {
		if(this.isVisible) for(GUIElement element : this.elements) element.update(x, y);
	}

	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		if(this.isVisible) for(GUIElement element : this.elements) element.render(renderer, x, y);
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
	
	@Override
	public boolean isHovered(int x, int y) {
		if(this.isVisible) for(GUIElement element : this.elements) if(element.isHovered(x, y)) return true;
		return false;
	}

}
