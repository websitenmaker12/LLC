package llc.util;

import org.lwjgl.opengl.GL11;

public class RenderUtil {

	public static void drawQuad(double x, double y, double width, double height) {
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(x		 , y);
			GL11.glVertex2d(x + width, y);
			GL11.glVertex2d(x + width, y + height);
			GL11.glVertex2d(x		 , y + height);
		GL11.glEnd();
	}
	
}

