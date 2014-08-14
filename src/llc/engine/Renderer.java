package llc.engine;

import llc.engine.model.ObjModel;
import llc.engine.res.Texture;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.logic.Cell;
import llc.logic.CellType;
import llc.logic.GameState;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {

	private Texture warriorTexture;
	private Texture workerTexture;
	private Texture baseTexture;
	private Texture solidTexture;
	private Texture walkableTexture;

	private ObjModel modelBase;

	public Renderer() {
		GL11.glClearColor(0F, 0F, 0F, 1F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		warriorTexture = new Texture("res/entity/moveable/warrior/warrior.png");
		workerTexture = new Texture("res/entity/moveable/miner/miner.png");
		baseTexture = new Texture("res/entity/building/Base.png");
		solidTexture = new Texture("res/texture/water.png");
		walkableTexture = new Texture("res/texture/grass.png");

//		 try {
//		 this.modelBase = ObjLoader.loadObj("res/entity/base/base.obj");
//		 } catch (IOException e) {
//		 e.printStackTrace();
//		 }
	}

	/**
	 * Handles the OpenGL-Part when the Display was resized
	 */
	public void handleDisplayResize(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45, (float) width / (float) height, 0.1F, 100F);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Is called each Display-Tick to render the game
	 */
	public void render(Camera camera, GameState gameState) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// Apply camera transformation
		GL11.glLoadIdentity();
		GLU.gluLookAt(camera.pos.x, camera.pos.y, camera.pos.z, camera.pos.x
				+ camera.viewDir.x, camera.pos.y + camera.viewDir.y,
				camera.pos.z + camera.viewDir.z, camera.up.x, camera.up.y,
				camera.up.z);

		// Draw coordinate system
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(10, 0, 0);

		GL11.glColor3f(0, 1, 0);
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(0, 10, 0);

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0, 0, 0);
		GL11.glVertex3f(0, 0, 10);
		GL11.glEnd();

		// Render grid
		Cell[][] cells = gameState.getGrid().getCells();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glNormal3f(0, 0, 1);
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[0].length; x++) {
				// highlight hovered cell
				if (cells[y][x] == gameState.hoveredCell)
					GL11.glColor3f(1, 0.5f, 0.5f);
				else
					GL11.glColor3f(1, 1, 1);
				
				if (cells[y][x].getEntity() == null) {
					// Render terrain texture
					if (cells[y][x].getType() == CellType.SOLID)
						solidTexture.bind();

					if (cells[y][x].getType() == CellType.WALKABLE)
						walkableTexture.bind();
				} else {
					// Render entity texture
					if (cells[y][x].getEntity() instanceof EntityWarrior)
						warriorTexture.bind();

					if (cells[y][x].getEntity() instanceof EntityWorker)
						workerTexture.bind();

					if (cells[y][x].getEntity() instanceof EntityBuildingBase)
						baseTexture.bind();
				}
				
				GL11.glBegin(GL11.GL_TRIANGLES);
				GL11.glTexCoord2d(0, 1);
				GL11.glVertex2d(x, y);
				GL11.glTexCoord2d(1, 1);
				GL11.glVertex2d(x + 1, y);
				GL11.glTexCoord2d(0, 0);
				GL11.glVertex2d(x, y + 1);

				GL11.glTexCoord2d(1, 1);
				GL11.glVertex2d(x + 1, y);
				GL11.glTexCoord2d(1, 0);
				GL11.glVertex2d(x + 1, y + 1);
				GL11.glTexCoord2d(0, 0);
				GL11.glVertex2d(x, y + 1);

				GL11.glColor3f(1, 1, 1);
				GL11.glEnd();
			}
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		// System.out.println(GL11.glGetError());
	}
}
