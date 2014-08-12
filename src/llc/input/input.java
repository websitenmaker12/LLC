package llc.input;


import java.util.*;
import llc.LLC;


public class input 
{
	
	public int scrollFrameBorder = 30;
 
	private LLC LLC_ref;
	
	public input(LLC reference)
	{
		LLC_ref = reference;
		
	}
	public void mouseClick(int x, int y)
	{
		//todo	
	}
	
	public void mousePos(int x, int y)
	{
	
		int h = LLC_ref.height;
		int w = LLC_ref.width;
		
		if (x < scrollFrameBorder) ThrowScrollEvent("left");
		if (x > w - scrollFrameBorder) ThrowScrollEvent("right");

		if (y < scrollFrameBorder) ThrowScrollEvent("up");
		if (y > h - scrollFrameBorder) ThrowScrollEvent("down");

	}
	
	// ------------- Interface for the Listener -------------
	
	interface logicListener
	{
		public void scrollEvent(String border);
		public void cellClickedEvent(int cell_x, int cell_y);
	}
	
	// -------------------------------------------------------
	
	List<logicListener> listeners = new ArrayList<logicListener>();
	
	
	// ------------ Function to add yourself as Listener
    public void addThrowListener(logicListener toAdd){ listeners.add(toAdd); }

    public void ThrowScrollEvent(String border) 
    {
        for (logicListener hl : listeners) hl.scrollEvent(border);
    }
    
    public void cellClickedEvent(int cell_x, int cell_y) 
    {
        for (logicListener hl : listeners) hl.cellClickedEvent(cell_x,cell_y);
    }
	
	
}
