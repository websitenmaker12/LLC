package llc.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import llc.engine.res.ObjLoader;
import llc.engine.res.Program;
import llc.engine.res.Shader;
import llc.engine.res.Texture;
import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.logic.Cell;
import llc.logic.GameState;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Renderer {
	
	private static final float sandRegion = 0.15f;
	private static final float terrainScale = 5;
	
	private Texture loadingScreen;
	
	private Texture waterTexture;
	private Texture grassTexture;
	private Texture sandTexture;
	
	private Triangle[][][] triangles;
	
	private int baseID;
	private int workerID;
	private int warriorID;
	
	private Program shaderProg;
	
	List<GradientPoint> colors = new ArrayList<GradientPoint>();

	public Renderer() {
		GL11.glClearColor(0F, 0F, 0F, 1F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		this.loadingScreen = new Texture("res/gui/logo.png");
		this.drawLoadingScreen(Display.getWidth(), Display.getHeight());
		
		waterTexture = new Texture("res/texture/water.png");
		grassTexture = new Texture("res/texture/grass.png");
		sandTexture = new Texture("res/texture/sand.png");
		
		this.baseID = this.loadModel("res/entity/base/base.obj");
		this.workerID = this.loadModel("res/entity/base/base.obj");
		this.warriorID = this.loadModel("res/entity/base/base.obj");
		
		colors.add(new GradientPoint(-1f, new Vector3f(0f, 0f, 1f)));
		colors.add(new GradientPoint(-0.25f,new Vector3f(1f, 1f, 1f)));
		colors.add(new GradientPoint(0.15f,new Vector3f(1f, 1f, 1f)));
		colors.add(new GradientPoint(0.25f,new Vector3f(0.5f, 1f, 1f)));
		colors.add(new GradientPoint(0.5f,new Vector3f(1f, 1f, 1f)));
		
		this.shaderProg = new Program();
		this.shaderProg.addShader(new Shader("res/shaders/test.vert", Shader.vertexShader));
		this.shaderProg.addShader(new Shader("res/shaders/test.frag", Shader.fragmentShader));
		this.shaderProg.validate();
	}

	/**
	 * Loads a model into a Display list
	 */
	private int loadModel(String path) {
		try {
			return ObjLoader.createDisplayList(ObjLoader.loadModel(new File(path)));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
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

		int width = gameState.getGrid().getWidth();
		int height = gameState.getGrid().getHeigth();
		
		drawCoordinateSystem();
		drawGrid(gameState, width, height);
		drawHoveredAndSelectedCells(gameState);
		drawEntities(gameState, width, height);
		drawWaterSurface(width, height);
	}

	private void drawWaterSurface(int width, int height) {
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		waterTexture.bind();
		GL11.glColor4f(1,  1,  1, 0.8f);
		float cellCount = 10;
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glTexCoord2d(0, cellCount);
		GL11.glVertex3f(0, 0, 0);
		GL11.glTexCoord2d(cellCount, cellCount);
		GL11.glVertex3f(width, 0, 0);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3f(0, height, 0);

		GL11.glTexCoord2d(cellCount, cellCount);
		GL11.glVertex3f(width, 0, 0);
		GL11.glTexCoord2d(cellCount, 0);
		GL11.glVertex3f(width, height, 0);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3f(0, height, 0);
		GL11.glEnd();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void generateGridGeometry (GameState state)
	{
		int width = state.getGrid().getWidth();
		int height = state.getGrid().getHeigth();
		Cell[][] cells = state.getGrid().getCells();
		triangles = new Triangle[height][width][2];
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				//Generating heigth coordinates
				float currentHeight = cells[y][x].height;
				float[][] heights = new float [3][3];
				heights[0][0] = y > 0 && x > 0 ? cells[y - 1][x - 1].height : currentHeight;
				heights[0][1] = y > 0 ? cells[y - 1][x].height : currentHeight;
				heights[0][2] = y > 0 && x < width - 1 ? cells[y - 1][x + 1].height : currentHeight;
				heights[1][0] = x > 0 ? cells[y][x - 1].height : currentHeight;
				heights[1][1] = currentHeight;
				heights[1][2] = x < width - 1 ? cells[y][x + 1].height : currentHeight;
				heights[2][0] = y < height - 1 && x > 0 ? cells[y + 1][x - 1].height : currentHeight;
				heights[2][1] = y < height - 1 ? cells[y + 1][x].height : currentHeight;
				heights[2][2] = y < height -1 && x < width - 1 ? cells[y + 1][x + 1].height : currentHeight;
				
				float topRightHeight = (heights[0][1] + heights[0][2] + heights[1][1] + heights[1][2]) / 4f;
				float topLeftHeight = (heights[0][0] + heights[0][1] + heights[1][0] + heights[1][1]) / 4f;
				float bottomRightHeight = (heights[1][1] + heights[1][2] + heights[2][1] + heights[2][2]) / 4f;
				float bottomLeftHeight = (heights[1][0] + heights[1][1] + heights[2][1] + heights[2][0]) / 4f;
				
				topRightHeight = topRightHeight * terrainScale;
				topLeftHeight = topLeftHeight * terrainScale;
				bottomRightHeight = bottomRightHeight * terrainScale;
				bottomLeftHeight = bottomLeftHeight * terrainScale;
				
				Vector3f topLeft = new Vector3f(x, y,  topLeftHeight);
				Vector3f topRight = new Vector3f(x + 1, y, topRightHeight);
				Vector3f bottomLeft = new Vector3f(x, y + 1, bottomLeftHeight);
				Vector3f bottomRight = new Vector3f(x + 1, y + 1, bottomRightHeight);
				
				Vector3f topLeftNormal = calcNormal(x, y, heights[0][0], heights[0][1], heights[1][0], heights[1][1]);
				Vector3f topRightNormal = calcNormal(x + 1, y, heights[0][1], heights[0][2], heights[1][1], heights[1][2]);
				Vector3f bottomLeftNormal = calcNormal(x, y + 1, heights[1][0], heights[1][1], heights[2][1], heights[2][0]);
				Vector3f bottomRightNormal = calcNormal(x + 1, y + 1, heights[1][0], heights[1][1], heights[2][1], heights[2][0]);
				
				triangles[y][x][0] = new Triangle(
						new Vertex(
								topLeft,
								topLeftNormal,
								new Vector2f(0, 1)
								),
						new Vertex(
								topRight,
								topRightNormal,
								new Vector2f(1, 1)
								),
						new Vertex(
								bottomLeft,
								bottomLeftNormal,
								new Vector2f(0, 0)
								)
						);
				triangles[y][x][1] = new Triangle(
						new Vertex(
								topRight,
								topRightNormal,
								new Vector2f(1, 1)
								),
						new Vertex(
								bottomRight,
								bottomRightNormal,
								new Vector2f(1, 0)
								),
						new Vertex(
								bottomLeft,
								bottomLeftNormal,
								new Vector2f(0, 0)
								)
						);
			}
		}
	}
	
	private void drawHoveredAndSelectedCells(GameState state)
	{
		// highlight hovered cell
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.shaderProg.bind();
		if(state.hoveredCell != null && state.selectedCell == state.hoveredCell)
		{
			GL11.glColor3f(1, 0.5f, 1);
			drawCell(state.hoveredCell, state.hoveredCell.y, state.hoveredCell.x, false);
			System.out.println("hovered and selected" + state.hoveredCell.x + " " + state.hoveredCell.y);
		}
		else if (state.hoveredCell != null)
		{
			GL11.glColor3f(1, 0.5f, 0.5f);
			drawCell(state.hoveredCell, state.hoveredCell.y, state.hoveredCell.x, false);
			System.out.println("hovered " + state.hoveredCell.x + " " + state.hoveredCell.y);
		}
		else if (state.selectedCell != null)
		{
			GL11.glColor3f(0.5f, 0.5f, 1f);
			drawCell(state.selectedCell, state.selectedCell.y, state.selectedCell.x, false);
			System.out.println("selected " + state.selectedCell.x + " " + state.selectedCell.y);
		}
		RenderUtil.unbindShader();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	private void drawEntities(GameState state, int width, int height) {
		Cell[][] cells = state.getGrid().getCells();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Cell c = cells[y][x];
				Entity e = c.getEntity();
				
				if(e != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(x + 0.5F, y + 0.5F, c.height + 1);
					if(e instanceof EntityBuildingBase) {
						GL11.glCallList(this.baseID);
					} else if(e instanceof EntityWarrior) {
						GL11.glCallList(this.warriorID);
					} else if(e instanceof EntityWorker) {
						GL11.glCallList(this.workerID);
					}
					GL11.glPopMatrix();
				}
			}
		}
	}

	private void drawGrid(GameState state, int width, int height) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.shaderProg.bind();
		GL11.glColor3f(1, 1, 1);
		
		Cell[][] cells = state.getGrid().getCells();
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				Cell c = cells[y][x];
				drawCell(c, y, x, true);
			}
		}
		
		RenderUtil.unbindShader();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	private void drawCell(Cell c, int y, int x, boolean allowColor) {
		if (c.getEntity() == null) {
			float h = c.getHeight();
			// Render terrain texture
			if (h < -sandRegion)
				waterTexture.bind();
			else if (h > sandRegion)
				grassTexture.bind();
			else 
				sandTexture.bind();
		} 
		
		Triangle[] ts = triangles[y][x];
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		for (int t = 0; t < 2; t++) {
			for (int i = 0; i < 3; i++) {
				if(allowColor) {
					Vector3f color = getTerrainColorFromHeight(ts[t].vertices[i].position.z / terrainScale);
					GL11.glColor3f(color.x, color.y, color.z);
				}
				
				GL11.glTexCoord2d(ts[t].vertices[i].texCoord.x, ts[t].vertices[i].texCoord.y);
				GL11.glNormal3f(ts[t].vertices[i].normal.x, ts[t].vertices[i].normal.y, ts[t].vertices[i].normal.z);
				GL11.glVertex3f(ts[t].vertices[i].position.x, ts[t].vertices[i].position.y, ts[t].vertices[i].position.z);
			}
		}

		GL11.glEnd();
	}

	private Vector3f calcNormal(int x, int y, float topLeftHeight, float topRightHeight, float bottomLeftHeight, float bottomRightHeight) {
		Vector3f topLeft = new Vector3f(x, y,  topLeftHeight);
		Vector3f topRight = new Vector3f(x + 1, y, topRightHeight);
		Vector3f bottomLeft = new Vector3f(x, y + 1, bottomLeftHeight);
		Vector3f bottomRight = new Vector3f(x + 1, y + 1, bottomRightHeight);
		Vector3f diagonal1 = Vector3f.sub(topLeft, bottomRight, null);
		Vector3f diagonal2 = Vector3f.sub(topRight, bottomLeft, null);
		Vector3f normal = Vector3f.cross(diagonal1, diagonal2, null).normalise(null);
		return normal;
	}

	private void drawCoordinateSystem() {
		GL11.glDisable(GL11.GL_TEXTURE_2D); // someone leaves textures enabled, fix this
		
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
	}
	
	private void drawLoadingScreen(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, width, height);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.loadingScreen.bind();
		RenderUtil.drawTexturedQuad(0, 0, width, height);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		Display.update();
	}
	
	private class GradientPoint {
		public float height;
		public Vector3f color;
		
		public GradientPoint(float height, Vector3f color) {
			this.height = height;
			this.color = color;
		}
	}
		
	
	private Vector3f getTerrainColorFromHeight(float height) {
		
		// find gradient colors around height
		int upper;
		for(upper = 0; upper < colors.size(); upper++) {
			if(colors.get(upper).height > height)
				break;
		}
		
		GradientPoint upperColor = colors.get(Math.min(upper, colors.size() - 1));
		GradientPoint lowerColor = colors.get(Math.max(upper - 1, 0));
		
		if(upperColor != lowerColor) {
			float lowerPart = (height - lowerColor.height) / (upperColor.height - lowerColor.height);
		
			Vector3f color = new Vector3f(
				lowerColor.color.x * (1 - lowerPart) + upperColor.color.x * lowerPart,
				lowerColor.color.y * (1 - lowerPart) + upperColor.color.y * lowerPart,
				lowerColor.color.z * (1 - lowerPart) + upperColor.color.z * lowerPart);
			return color;
		}
		else return upperColor.color;
	}
	
}
