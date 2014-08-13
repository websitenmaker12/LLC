package llc.engine;

import llc.input.input.Direction;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public final float pixelsPerCell = 100F;
	
	public Vector3f pos;
	public Vector3f viewDir; 
	
	public Camera(Vector3f pos, Vector3f viewDir) {
		this.pos = pos;
		this.viewDir = viewDir;
	}

	public void scroll(Direction d) {
		switch(d){
		case right: pos.x += 0.5; break;
		case down: pos.y -= 0.5; break;
		case left: pos.x -= 0.5; break;
		case up: pos.y += 0.5; break;
		}
		
	}
	
}