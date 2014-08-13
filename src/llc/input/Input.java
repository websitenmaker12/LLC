package llc.input;

import java.util.*;
import org.lwjgl.util.vector.Vector3f;

import llc.LLC;
import llc.engine.Camera;


public class Input 
{
	private Camera Cam;
	
	public int scrollFrameBorder = 30;
 
	private LLC LLC_ref;
	
	public Input(LLC reference)
	{
		LLC_ref = reference;
		
	}
	public void mouseClick(int x, int y)
	{		
		
		Vector3f clickPos = new Vector3f(x, y, 1) ;
		
		clickPos.x = x;
		clickPos.y = y;
		clickPos.z = 1; // z of projection-plane is 1 ?
		
		float t = (- Cam.pos.z)/ (clickPos.z - Cam.pos.z); // z of the grid is 0
		
		int cell_x = (int) ((int) Cam.pos.x + t * (clickPos.x - Cam.pos.x));
		int cell_y = (int) ((int) Cam.pos.y + t * (clickPos.y - Cam.pos.y));
		
		cellClickedEvent(cell_x, cell_y);
		
		
	}
	
	public void mousePos(int x, int y)
	{
	
		int h = LLC_ref.height;
		int w = LLC_ref.width;
		
		if (x < scrollFrameBorder) ThrowScrollEvent("left");
		if(x > w - scrollFrameBorder) ThrowScrollEvent("right");

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
