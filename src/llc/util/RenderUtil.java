package llc.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class RenderUtil {

	public static void drawQuad(double x, double y, double width, double height) {
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(x		 , y);
			GL11.glVertex2d(x + width, y);
			GL11.glVertex2d(x + width, y + height);
			GL11.glVertex2d(x		 , y + height);
		GL11.glEnd();
	}
	
	public static void drawTexturedQuad(double x, double y, double width, double height) {
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glTexCoord2f(0, 0); GL11.glVertex2d(x		  , y);
			GL11.glTexCoord2f(1, 0); GL11.glVertex2d(x + width, y);
			GL11.glTexCoord2f(0, 1); GL11.glVertex2d(x		  , y + height);
			
			GL11.glTexCoord2f(1, 0); GL11.glVertex2d(x + width, y);
			GL11.glTexCoord2f(1, 1); GL11.glVertex2d(x + width, y + height);
			GL11.glTexCoord2f(0, 1); GL11.glVertex2d(x		  , y + height);
		GL11.glEnd();
	}
	
	public static void unbindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public static void unbindShader() {
		GL20.glUseProgram(0);
	}

	public static void clearColor() {
		GL11.glColor4f(1, 1, 1, 1);
	}
	
}

