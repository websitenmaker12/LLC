package llc.input;

import java.util.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import llc.LLC;
import llc.engine.Camera;


public class Input 
{
	private Camera Cam;
	
	public int scrollFrameBorder = 30;
 
	private LLC LLC_ref;
	
	public Input(LLC reference, Camera camera)
	{
		LLC_ref = reference;
		Cam = camera;
	}
	public void mouseClick(int x, int y)
	{		
		int width = LLC_ref.width;
		int height = LLC_ref.height;
		
		// calculate dimensions of image plane
		float fovy = 45f;    // TODO: see gluPerspective in Renderer and refactor to constant
		float aspect = 1.3f; // TODO: see gluPerspective in Renderer and refactor to constant
		float znear = 0.1f;  // TODO: see gluPerspective in Renderer and refactor to constant
		
		float ymax = znear * (float)Math.tan(fovy * Math.PI / 360.0);
		float ymin = -ymax;
		float xmax = ymax * aspect;
		float xmin = -xmax;
		
		float planeWidth = (xmax - xmin);
		float planeHeight = (ymax - ymin);
		
		// transform window coordinates into world coordinates
		float wx = (x / (float)width ) * planeWidth + xmin;
		float wy = (y / (float)height) * planeHeight + ymin;
		
		// determine x and y vector of camera transformed image plane
		Vector3f xVec = (Vector3f)Vector3f.cross(Cam.viewDir, Cam.up, null).normalise(null).scale(wx);
		Vector3f yVec = (Vector3f)Vector3f.cross(xVec, Cam.viewDir, null).normalise(null).scale(wy);
		
		// determine the position the user clicked on the image plane in world coordinates
		Vector3f nearVec = new Vector3f(Cam.viewDir);
		nearVec.scale(znear);
		Vector3f clickPos = Vector3f.add(Vector3f.add(Vector3f.add(Cam.pos, nearVec, null), xVec, null), yVec, null);

		
		Vector3f rayDir = Vector3f.sub(clickPos, Cam.pos, null);

		float t = (0 - Cam.pos.z) / rayDir.z; // z of the grid is 0
		
		rayDir.scale(t);
		Vector3f intersection = Vector3f.add(Cam.pos, rayDir, null);
		
		int cell_x = (int) intersection.x;
		int cell_y = (int) intersection.y;
		
		System.out.println("clicked " + cell_x + " " + cell_y);
		FireCellClickedEvent(cell_x, cell_y);
	}
	
	public void mousePos(int x, int y)
	{
	
		int h = LLC_ref.height;
		int w = LLC_ref.width;
		
		if (x < scrollFrameBorder) FireScrollEvent(Direction.left);
		if (x > w - scrollFrameBorder) FireScrollEvent(Direction.right);

		if (y < scrollFrameBorder) FireScrollEvent(Direction.up);
		if (y > h - scrollFrameBorder) FireScrollEvent(Direction.down);

		// TODO: fire cell hovered
	}
	
	// ------------- Interface for the Listener -------------
	public interface LogicListener
	{
		public void onScroll(Direction d);
		public void onCellClicked(int cell_x, int cell_y);
		// TODO: add cell hovered event
	}
	
	public enum Direction
	{
		left,
		right,
		down,
		up;
	}
	// -------------------------------------------------------
	
	List<LogicListener> listeners = new ArrayList<LogicListener>();
	
	
	// ------------ Function to add yourself as Listener
	public void addFireListener(LogicListener toAdd){ listeners.add(toAdd); }

    public void FireScrollEvent(Direction d) 
    {
        for (LogicListener hl : listeners) hl.onScroll(d);
    }
    
    public void FireCellClickedEvent(int cell_x, int cell_y) 
    {
        for (LogicListener hl : listeners) hl.onCellClicked(cell_x,cell_y);
    }
	
	
}
