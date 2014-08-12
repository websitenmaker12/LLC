package llc;

import llc.engine.Camera;
import llc.engine.Profiler;
import llc.input.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class LLC {

	public static LLC instance;
	
	public static final String VERSION = "0.1 INDEV";
	private boolean isRunning = false;
	
	private Profiler profiler = new Profiler();
	private Camera camera;
	private input input;
	
	public int width = 0;
	public int height = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	
	public LLC() {
		instance = this;
		this.camera = new Camera(0, 0);
		this.input = new input();
	}
	
	public void startGame() throws LWJGLException {
		this.profiler.start("Setup Display");
		this.initDisplay();
		this.profiler.endStart("Setup OpenGL");
		this.initOpenGL();
		this.profiler.end();
		this.beginLoop();
	}
	
	private void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("LLC - " + VERSION);
		Display.create();
	}
	
	private void initOpenGL() {
		this.handleDisplayResize();
		
		GL11.glClearColor(0F, 0F, 0F, 1F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void beginLoop() {
		this.isRunning = true;
		boolean firstClick = true; // assume no click in this frame jet
		
		while(this.isRunning) {
			this.handleDisplayResize();
			if(Display.isCloseRequested()) this.isRunning = false;
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			
			this.profiler.start("Input updates");
			
			this.mouseX = Mouse.getX();
			this.mouseY = this.height - Mouse.getY();
			this.input.mousePos(this.mouseX, this.mouseY);
			
			if(Mouse.isButtonDown(0)  && firstClick == true) 
				{
				this.input.mouseClick(this.mouseX, this.mouseY); 
				firstClick = false;
				}
			
			this.profiler.endStart("Render updates");
			GL11.glLoadIdentity();
			this.camera.transformMatrix();
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
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
		}
	}
	
}

