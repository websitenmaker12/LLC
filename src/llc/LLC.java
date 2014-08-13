package llc;

import llc.engine.Camera;
import llc.engine.GUIRenderer;
import llc.engine.Profiler;
import llc.engine.Renderer;
import llc.engine.gui.GUIIngame;
import llc.input.Input;
import llc.loading.GameLoader;
import llc.logic.Cell;
import llc.logic.Logic;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class LLC {

	public static final String VERSION = "0.1 INDEV";
	private boolean isRunning = false;
	
	private Profiler profiler = new Profiler();
	private Camera camera;
	private Input input;
	private Renderer renderer;
	private GameLoader gameLoader;
	private Logic logic;
	private GUIRenderer guiRenderer;
	
	public int width = 0;
	public int height = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean lastButtonState = false;
	
	public LLC() {
		this.camera = new Camera(new Vector3f(4, 4, 10), new Vector3f(0, 1.5f, -1), new Vector3f(0, 0, 1));
		this.input = new Input(this, this.camera);
		this.gameLoader = new GameLoader();
		this.logic = new Logic(this.gameLoader.createNewGame("res/maps/areas/map-1_areas.png"));
		this.guiRenderer.openGUI(new GUIIngame(this.logic));
		
		this.input.addFireListener(new Input.LogicListener() {

			@Override
			public void onScroll(Input.Direction d) {
				camera.scroll(d);
				float yOffset = camera.pos.z * (camera.viewDir.y / camera.viewDir.z);
				float xOffset = camera.pos.z * (camera.viewDir.x / camera.viewDir.z);
				Cell[][] cells = logic.getGameState().getGrid().getCells();
				if (camera.pos.x < 0 + xOffset)	camera.pos.x = 0;
				if (camera.pos.y < 0 + yOffset)	camera.pos.y = 0 + yOffset;
				if (camera.pos.y > cells.length + yOffset)	camera.pos.y = cells.length + yOffset;
				if (camera.pos.x > cells[0].length + xOffset) camera.pos.x = cells[0].length + xOffset;
			}

			@Override
			public void onCellClicked(int cell_x, int cell_y) {
				logic.clickCell(cell_x, cell_y);
			}
		});
	}
	
	/**
	 * Setups the Display and OpenGL. Finally starts the Main-Loop
	 */
	public void startGame() throws LWJGLException {
		this.profiler.start("Setup Display");
		this.initDisplay();
		this.profiler.endStart("Setup OpenGL");
		this.renderer = new Renderer();
		this.profiler.endStart("Setup GUI Renderer");
		this.guiRenderer = new GUIRenderer();
		this.profiler.end();
		this.beginLoop();
	}
	
	/**
	 * Setups the Display
	 */
	private void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setResizable(true);
		Display.setTitle("LLC - " + VERSION);
		Display.create();
	}
	
	/**
	 * Enters the Main-Loop
	 */
	private void beginLoop() {
		this.isRunning = true;
		
		while(this.isRunning) {
			this.handleDisplayResize();
			if(Display.isCloseRequested()) this.isRunning = false;

			this.profiler.start("Input updates");
			
			this.mouseX = Mouse.getX();
			this.mouseY = this.height - Mouse.getY();
			this.input.mousePos(this.mouseX, this.mouseY);
			if(Mouse.isButtonDown(0) && !this.lastButtonState) this.input.mouseClick(this.mouseX, this.mouseY);
			this.lastButtonState = Mouse.isButtonDown(0);
			
			this.profiler.endStart("Render game");
			this.renderer.render(this.camera, this.logic.getGameState());
			this.profiler.endStart("Render GUI");
			this.guiRenderer.render(this.width, this.height, this.mouseX, this.mouseY);
			this.profiler.end();
			
			Display.update();
			Display.sync(60);
			
			int error = GL11.glGetError();
			if(error != GL11.GL_NO_ERROR) System.out.println("GLError " + error + ": " + GLU.gluErrorString(error));
		}
		
		if(Display.isCreated()) Display.destroy();
		System.out.println(0);
	}
	
	/**
	 * Handles a Display-Resize-Event
	 */
	private void handleDisplayResize() {
		if(this.width != Display.getWidth() || this.height != Display.getHeight()) {
			this.width = Display.getWidth();
			this.height = Display.getHeight();
			
			this.renderer.handleDisplayResize(this.width, this.height);
		}
	}
	
}

