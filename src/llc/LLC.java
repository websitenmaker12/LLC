package llc;

import llc.engine.Camera;
import llc.engine.Profiler;
import llc.engine.Renderer;
import llc.input.input;
import llc.loading.GameLoader;
import llc.logic.Logic;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

public class LLC {

	public static final String VERSION = "0.1 INDEV";
	private boolean isRunning = false;
	
	private Profiler profiler = new Profiler();
	private Camera camera;
	private input input;
	private Renderer renderer;
	private GameLoader gameLoader;
	private Logic logic;
	
	public int width = 0;
	public int height = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean lastButtonState = false;
	
	public LLC() {
		this.camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1));
		this.input = new input();
		
		this.gameLoader = new GameLoader();
		this.logic = new Logic(this.gameLoader.loadMap("res/maps/areas/map-1_areas.png"));
	}
	
	public void startGame() throws LWJGLException {
		this.profiler.start("Setup Display");
		this.initDisplay();
		this.profiler.endStart("Setup OpenGL");
		this.renderer = new Renderer();
		this.profiler.end();
		this.beginLoop();
	}
	
	private void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("LLC - " + VERSION);
		Display.create();
	}
	
	private void beginLoop() {
		this.isRunning = true;
		boolean firstClick = true; // assume no click in this frame jet
		
		while(this.isRunning) {
			this.handleDisplayResize();
			if(Display.isCloseRequested()) this.isRunning = false;

			this.profiler.start("Input updates");
			
			this.mouseX = Mouse.getX();
			this.mouseY = this.height - Mouse.getY();
			this.input.mousePos(this.mouseX, this.mouseY);
<<<<<<< HEAD
			if(Mouse.isButtonDown(0)) this.input.mouseClick(this.mouseX, this.mouseY, this.lastButtonState);
			this.lastButtonState = Mouse.isButtonDown(0);
=======
			
			if(Mouse.isButtonDown(0)  && firstClick == true) 
				{
				this.input.mouseClick(this.mouseX, this.mouseY); 
				firstClick = false;
				}
>>>>>>> origin/master
			
			this.profiler.endStart("Render updates");
			this.renderer.render(this.camera, this.logic.getGameState());
			this.profiler.end();
			
			Display.update();
			Display.sync(60);
		}
		
		if(Display.isCreated()) Display.destroy();
		System.out.println(0);
	}
	
	private void handleDisplayResize() {
		if(this.width != Display.getWidth() || this.height != Display.getHeight()) {
			this.width = Display.getWidth();
			this.height = Display.getHeight();
			
			this.renderer.handleDisplayResize();
		}
	}
	
}

