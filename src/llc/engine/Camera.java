package llc.engine;

import org.lwjgl.opengl.GL11;

public class Camera {

	public double posX = 0;
	public double posY = 0;
	
	public Camera(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void transformMatrix() {
		GL11.glTranslated(this.posX, this.posY, 0);
	}
	
}
