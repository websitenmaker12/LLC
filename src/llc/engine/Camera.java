package llc.engine;

import llc.input.Input;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public final float pixelsPerCell = 100F;
	
	public Vector3f pos;
	public Vector3f viewDir;
	public Vector3f up;
	private double scrollSpeed = 0.6;
	
	public Camera(Vector3f pos, Vector3f viewDir, Vector3f up) {
		this.pos = pos;
		this.viewDir = viewDir.normalise(null);
		this.up = up.normalise(null);
	}

	public void scroll(Input.Direction d) {
		switch(d){
		case right: pos.x += scrollSpeed; break;
		case down: pos.y -= scrollSpeed; break;
		case left: pos.x -= scrollSpeed; break;
		case up: pos.y += scrollSpeed; break;
		}
		
	}
	
}