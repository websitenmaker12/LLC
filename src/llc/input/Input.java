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
 
	private Vector2f lastHoveredCell = null;
	private LLC LLC_ref;
	
	public Input(LLC reference, Camera camera)
	{
		LLC_ref = reference;
		Cam = camera;
	}
	
	private Vector2f rayCast(int x, int y)
	{
		//System.out.println("pos: " + x + " " + y);
		
		float windowWidth = LLC_ref.width;
		float windowHeight = LLC_ref.height;
		y = LLC_ref.height - y;
		float near = 0.1F;
		float fovy = 45;
		float aspect = windowWidth / windowHeight;
		
		float worldHeight = (float) (Math.tan(Math.toRadians(fovy / 2.0f)) * near) * 2.0f;
		float worldWidth = worldHeight * aspect;

//		float xWorld = (x / windowWidth) * worldWidth + xMin;
//		float yWorld = (y / windowHeight) * worldHeight + yMin;
		
		Vector3f xImagePlanePixelDeltaVector =  Vector3f.cross(Cam.viewDir, Cam.up, null);
		Vector3f yImagePlanePixelDeltaVector =  Vector3f.cross(xImagePlanePixelDeltaVector, Cam.viewDir, null);
		
		xImagePlanePixelDeltaVector.normalise().scale(worldWidth / (windowWidth - 1));
		yImagePlanePixelDeltaVector.normalise().scale(worldHeight / (windowHeight - 1));
		
		//System.out.println("x delta length: " + xImagePlanePixelDeltaVector.length());
		//System.out.println("y delta length: " + yImagePlanePixelDeltaVector.length());
		
		xImagePlanePixelDeltaVector.scale(x - windowWidth / 2.0f);
		yImagePlanePixelDeltaVector.scale(y - windowHeight / 2.0f);
		
		//System.out.println("x delta " + xImagePlanePixelDeltaVector);
		//System.out.println("y delta " + yImagePlanePixelDeltaVector);
		
		Vector3f nearVector = (Vector3f) new Vector3f(Cam.viewDir).normalise().scale(near);
		Vector3f imagePlaneMousePos = new Vector3f(
				Cam.pos.x + nearVector.x + xImagePlanePixelDeltaVector.x + yImagePlanePixelDeltaVector.x,
				Cam.pos.y + nearVector.y + xImagePlanePixelDeltaVector.y + yImagePlanePixelDeltaVector.y,
				Cam.pos.z + nearVector.z + xImagePlanePixelDeltaVector.z + yImagePlanePixelDeltaVector.z
				);
		
		//System.out.println("image plane: " + imagePlaneMousePos);
		
		Vector3f rayDirection = (Vector3f) Vector3f.sub(imagePlaneMousePos, Cam.pos, null).normalise();
		//System.out.println("ray: " + rayDirection);
		float t = (0 - Cam.pos.z) / rayDirection.z; // z of the grid is 0
		
		Vector3f intersection = Vector3f.add(Cam.pos, (Vector3f)rayDirection.scale(t), null);
		//System.out.println("Intersect: " + intersection);
		
		int cell_x = (int) intersection.x;
		int cell_y = (int) intersection.y;
		
		//System.out.println("clicked " + cell_x + " " + cell_y);
		
		Vector2f cellPos = new Vector2f(cell_x, cell_y);
		return cellPos;
	}
	
	public void mouseClick(int x, int y)
	{		
		Vector2f Ray = rayCast(x, y);
		//FireCellClickedEvent((int) Ray.x,(int) Ray.y);
	}
	
	public void mousePos(int x, int y)
	{
	
		int h = LLC_ref.height;
		int w = LLC_ref.width;
		
		
		if (x < scrollFrameBorder) FireScrollEvent(Direction.left);
		if (x > w - scrollFrameBorder) FireScrollEvent(Direction.right);

		if (y < scrollFrameBorder) FireScrollEvent(Direction.up);
		if (y > h - scrollFrameBorder) FireScrollEvent(Direction.down);

		
		Vector2f hoveredCell = rayCast(x, y);
		if (hoveredCell != lastHoveredCell) 
			{
			lastHoveredCell = hoveredCell; 
			FireNewCellHoveredEvent((int)hoveredCell.x,(int)hoveredCell.y);
			}
	}
	
	// ------------- Interface for the Listener -------------
	public interface LogicListener
	{
		public void onScroll(Direction d);
		public void onCellClicked(int cell_x, int cell_y);
		public void onNewCellHovered(int cell_x, int cell_y);
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
    
    public void FireNewCellHoveredEvent(int cell_x, int cell_y) 
    {
        for (LogicListener hl : listeners) hl.onNewCellHovered(cell_x,cell_y);
    }
    

	
	
}
