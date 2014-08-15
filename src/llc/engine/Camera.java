package llc.engine;

import llc.input.Input;
import llc.logic.Cell;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public final float pixelsPerCell = 100F;
	private final float scrollSpeed = 0.6F;
	
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
	
	public void zoom(int dir) {
		this.pos.y -= 2F * dir * this.scrollSpeed * 2;
		this.pos.z += 1F * dir * this.scrollSpeed * 2;
	}

	public void focusCell(Cell cell) {
		Vector3f b = cell.getCenterPos();
		float t = (this.pos.z - b.z) / this.viewDir.z;
		b = (Vector3f)new Vector3f(this.viewDir).scale(t);
		Vector3f c = Vector3f.add(b, this.viewDir, null);
		
		this.pos = c;
	}
	
}