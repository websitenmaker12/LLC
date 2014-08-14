package llc.engine;

import llc.input.Input;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public final float pixelsPerCell = 100F;
	private final float scrollSpeed = 0.5F;
	
	public Vector3f pos;
	public Vector3f viewDir;
	public Vector3f up;
	
	public Camera(Vector3f pos, Vector3f viewDir, Vector3f up) {
		this.pos = pos;
		this.viewDir = viewDir.normalise(null);
		this.up = up.normalise(null);
	}

	public void scroll(Input.Direction d) {
		switch(d){
		case right: pos.x += this.scrollSpeed; break;
		case down: pos.y -= this.scrollSpeed; break;
		case left: pos.x -= this.scrollSpeed; break;
		case up: pos.y += this.scrollSpeed; break;
		}
		
	}
	
}