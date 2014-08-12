package llc;

import llc.engine.Camera;
import llc.engine.Profiler;
import llc.util.RenderUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class LLC {

	public static LLC instance;
	
	public static final String VERSION = "0.1 INDEV";
	private boolean isRunning = false;
	
	private Profiler profiler = new Profiler();
	private Camera camera;
	
	public int width = 0;
	public int height = 0;
	
	public LLC() {
		instance = this;
		this.camera = new Camera(0, 0);
	}
	
	public void startGame() throws LWJGLException {
		this.profiler.start("Setup Display");
		this.initDisplay();
		this.profiler.endStart("Setup OpenGL");
		this.initOpenGL();
		this.profiler.end();
		this.beginLoop();
	}
	
	public void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setResizable(false);
		Display.setTitle("LLC - " + VERSION);
		Display.create();
	}
	
	public void initOpenGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glClearColor(0F, 0F, 0F, 1F);
	}
	
	public void beginLoop() {
		this.isRunning = true;
		while(this.isRunning) {
			if(Display.isCloseRequested()) this.isRunning = false;
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			
			this.profiler.start("Input updates");
			// TODO implement
			this.profiler.endStart("Render updates");
			GL11.glLoadIdentity();
			this.camera.transformMatrix();
			RenderUtil.drawQuad(0, 0, 100, 100);
			this.profiler.end();
			
			Display.update();
			Display.sync(60);
		}
		
		if(Display.isCreated()) Display.destroy();
		System.out.println(0);
	}
	
}

