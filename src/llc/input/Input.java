package llc.input;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;
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
	
	private Vector2f rayCast(int x, int y)
	{
		float windowWidth = LLC_ref.width;
		float windowHeight = LLC_ref.height;
		float near = 0.1F;
		float fovy = 45;
		float aspect = windowWidth / windowHeight;
		
		float yMax = (float) (Math.tan(Math.toRadians(fovy)) * near);
		
		float yMin = -yMax;
		
		float xMax = yMax * aspect;
		float xMin = - xMax;
		
		float worldWidth = xMax - xMin;
		float worldHeight = worldWidth / aspect;
		
		float xWorld = x/windowWidth * worldWidth + xMin;
		float yWorld = y/windowHeight * worldHeight + yMin;
		
		Vector3f xImagePlaneVector =  Vector3f.cross(Cam.viewDir, Cam.up, null);
		Vector3f yImagePlaneVector =  Vector3f.cross(xImagePlaneVector, Cam.viewDir, null);
		
		xImagePlaneVector.normalise().scale(xWorld);
		yImagePlaneVector.normalise().scale(yWorld);
		
		Vector3f nearVector = (Vector3f) new Vector3f(Cam.viewDir).normalise().scale(near);
		Vector3f ImagePlaneMousePos = Vector3f.add(Vector3f.add(Vector3f.add(Cam.pos, nearVector, null),xImagePlaneVector, null),yImagePlaneVector, null);
		
		Vector3f rayDirection = Vector3f.sub(ImagePlaneMousePos, Cam.pos, null);
		
		float t = (0 - Cam.pos.z) / rayDirection.z; // z of the grid is 0
		
		Vector3f intersection = Vector3f.add(Cam.pos, (Vector3f)rayDirection.scale(t), null);
		
		int cell_x = (int) intersection.x;
		int cell_y = (int) intersection.y;
		
		System.out.println("clicked " + cell_x + " " + cell_y);
		
		Vector2f cellPos = new Vector2f(cell_x, cell_y);
		return cellPos;
	}
	
	public void mouseClick(int x, int y)
	{		
		Vector2f Ray = rayCast(x, y);
		FireCellClickedEvent((int) Ray.x,(int) Ray.y);
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
