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
		
		if (x < scrollFrameBorder) ThrowScrollEvent(Direction.left);
		if (x > w - scrollFrameBorder) ThrowScrollEvent(Direction.right);

		if (y < scrollFrameBorder) ThrowScrollEvent(Direction.up);
		if (y > h - scrollFrameBorder) ThrowScrollEvent(Direction.down);

	}
	
	// ------------- Interface for the Listener -------------
	
	public interface logicListener
	{
		public void scrollEvent(Direction d);
		public void cellClickedEvent(int cell_x, int cell_y);
	}
	
	public enum Direction
	{
		left,
		right,
		down,
		up;
		
	}
	
	// -------------------------------------------------------
	
	List<logicListener> listeners = new ArrayList<logicListener>();
	
	
	// ------------ Function to add yourself as Listener
    public void addThrowListener(logicListener toAdd){ listeners.add(toAdd); }

    public void ThrowScrollEvent(Direction d) 
    {
        for (logicListener hl : listeners) hl.scrollEvent(d);
    }
    
    public void cellClickedEvent(int cell_x, int cell_y) 
    {
        for (logicListener hl : listeners) hl.cellClickedEvent(cell_x,cell_y);
    }
	
	
}
