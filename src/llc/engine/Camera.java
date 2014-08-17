package llc.engine;

import java.io.Serializable;

import llc.input.Input;
import llc.logic.Cell;
import llc.util.MathUtil;

import org.lwjgl.util.vector.Vector3f;

public class Camera implements Serializable {

	private static final long serialVersionUID = 11L;
	
	public final float pixelsPerCell = 100F;
	private final float scrollSpeed = 0.6F;
	
	public Vector3f pos;
	public Vector3f viewDir;
	public Vector3f up;
	
	private Vector3f animateTo;
	
	public Camera(Vector3f pos, Vector3f viewDir, Vector3f up) {
		this.pos = pos;
		this.viewDir = viewDir.normalise(null);
		this.up = up.normalise(null);
	}

	public void scroll(Input.Direction d) {
		if(this.animateTo != null) return;
		
		switch(d) {
			case right: pos.x += this.scrollSpeed; break;
			case down: pos.y -= this.scrollSpeed; break;
			case left: pos.x -= this.scrollSpeed; break;
			case up: pos.y += this.scrollSpeed; break;
		}
		
	}
	
	public void zoom(int dir) {
		if(this.animateTo != null) return;
		
		this.pos.y -= 2F * dir * this.scrollSpeed * 2;
		this.pos.z += 1F * dir * this.scrollSpeed * 2;
	}

	public void focusCell(Cell cell, boolean animate) {
		Vector3f b = cell.getCenterPos();
		float t = (this.pos.z - b.z) / this.viewDir.z;
		Vector3f baseTrans = (Vector3f)new Vector3f(this.viewDir).scale(t);
		Vector3f c = Vector3f.add(b, baseTrans, null);
		
		if(animate) this.animateTo = c;
		else this.pos = c;
	}
	
	public void update(int delta) {
		if(this.animateTo != null) {
			this.pos = MathUtil.lerpVector(this.pos, this.animateTo, (float)delta / 400F);
			if(MathUtil.areEquals(this.pos, this.animateTo, 0.2F)) this.animateTo = null;
		}
	}
	
}