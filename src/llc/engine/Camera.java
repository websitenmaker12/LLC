package llc.engine;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public final float pixelsPerCell = 100F;
	
	public Vector3f pos; // zelle in der mitte des screen
	public Vector3f viewDir; 
	
	public Camera(Vector3f pos, Vector3f viewDir) {
		this.pos = pos;
		this.viewDir = viewDir;
	}
	
}