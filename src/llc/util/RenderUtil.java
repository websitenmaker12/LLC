package llc.util;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL20;

public class RenderUtil {

	public static void drawQuad(double x, double y, double width, double height) {
		glBegin(GL_QUADS);
			glVertex2d(x		 , y);
			glVertex2d(x + width, y);
			glVertex2d(x + width, y + height);
			glVertex2d(x		 , y + height);
		glEnd();
	}
	
	public static void drawTexturedQuad(double x, double y, double width, double height) {
		glBegin(GL_TRIANGLES);
			glTexCoord2f(0, 0); glVertex2d(x		  , y);
			glTexCoord2f(1, 0); glVertex2d(x + width, y);
			glTexCoord2f(0, 1); glVertex2d(x		  , y + height);
			
			glTexCoord2f(1, 0); glVertex2d(x + width, y);
			glTexCoord2f(1, 1); glVertex2d(x + width, y + height);
			glTexCoord2f(0, 1); glVertex2d(x		  , y + height);
		glEnd();
	}
	
	public static void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public static void unbindShader() {
		GL20.glUseProgram(0);
	}

	public static void clearColor() {
		glColor4f(1, 1, 1, 1);
	}
	
}

