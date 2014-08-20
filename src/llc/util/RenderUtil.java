package llc.util;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

public class RenderUtil {

	/**
	 * Draws a quad
	 */
	public static void drawQuad(double x, double y, double width, double height) {
		glBegin(GL_QUADS);
			glVertex2d(x		 , y);
			glVertex2d(x + width, y);
			glVertex2d(x + width, y + height);
			glVertex2d(x		 , y + height);
		glEnd();
	}
	
	
	/**
	 * Draws a textured quad
	 */
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
	
	/**
	 * Unbinds the current texture
	 */
	public static void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	/**
	 * Unbinds the current shader
	 */
	public static void unbindShader() {
		GL20.glUseProgram(0);
	}

	/**
	 * Clears the color to RGBA 255 255 255 255
	 */
	public static void clearColor() {
		glColor4f(1, 1, 1, 1);
	}

	/**
	 * Renders the hover box
	 */
	public static void drawHoverBox(float x, float y, boolean ignoreWindowBorder, TrueTypeFont font, String... lines) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 1);
		
		float width = 0F;
		float height = lines.length * (font.getHeight() + 1);
		for(String line : lines) if(font.getWidth(line) > width) width = font.getWidth(line);
		
		float offX = 0;
		if(x + 4 + width > Display.getWidth() && !ignoreWindowBorder) offX = 8 + width;
		
		Color.darkGray.bind();
		drawQuad(x - offX, y, width + 8, height + 4);
		
		TextureImpl.bindNone();
		for(int i = 0; i < lines.length; i++) font.drawString(x - offX + 4, y + 2 + (font.getHeight() + 1) * i, lines[i]);
		
		GL11.glPopMatrix();
	}
	
}

