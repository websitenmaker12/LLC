package llc.engine;

import llc.logic.Cell;
import llc.logic.CellType;
import llc.engine.res.TextureLoader;
import llc.logic.GameState;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {

	private TextureLoader loader;
	public Renderer(TextureLoader textureLoader) {
		GL11.glClearColor(0F, 0F, 0F, 1F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		loader = textureLoader;
	}
	
	/**
	 * Handles the OpenGL-Part when the Display was resized
	 */
	public void handleDisplayResize(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45, (float)width / (float)height, 0.1F, 100F);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	/**
	 * Is called each Display-Tick to render the game
	 */
	public void render(Camera camera, GameState gameState) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		GL11.glTranslatef(camera.pos.x, camera.pos.y, camera.pos.z);
		// Render grid
		Cell[][] cells = gameState.getGrid().getCells();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glNormal3f(0, 0, 1);
		for (int y = 0; y > cells.length; y++)
		{
			for (int x = 0; x > cells[0].length; x++)
			{
				if (cells[y][x].getEntity() == null){
					//Render terrain texture
					if (cells[y][x].getType() == CellType.SOLID)
					{
						loader.getTexture("SOLID").bind();
					}
					if (cells[y][x].getType() == CellType.WALKABLE)
					{
						loader.getTexture("WALKABLE").bind();
					}
					
				}
				else
				{
					//Render entity texture
					//No Entity texture loaded at the moment
//					if (cells[y][x].getEntity(). == CellType.SOLID)
//					{
//						loader.getTexture("SOLID").bind();
//					}
//					if (cells[y][x].getEntity() == CellType.WALKABLE)
//					{
//						loader.getTexture("WALKABLE").bind();
//					}
				}
				GL11.glBegin(GL11.GL_TRIANGLES);
				GL11.glTexCoord2d(0, 0); GL11.glVertex2d(x, y);
				GL11.glTexCoord2d(1, 0); GL11.glVertex2d(x + 1, y);
				GL11.glTexCoord2d(0, 1); GL11.glVertex2d(x, y + 1);
				
				GL11.glTexCoord2d(1, 0); GL11.glVertex2d(x + 1, y);
				GL11.glTexCoord2d(1, 1); GL11.glVertex2d(x + 1, y + 1);
				GL11.glTexCoord2d(0, 1); GL11.glVertex2d(x, y + 1);

				GL11.glEnd();
				
			}
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
}
