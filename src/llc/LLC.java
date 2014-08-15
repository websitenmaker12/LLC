package llc;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;
import llc.engine.Camera;
import llc.engine.GUIRenderer;
import llc.engine.Profiler;
import llc.engine.Renderer;
import llc.engine.Timing;
import llc.engine.audio.AudioEngine;
import llc.engine.gui.GUI;
import llc.engine.gui.GUIIngame;
import llc.engine.gui.GUIIngameMenu;
import llc.input.IKeybindingListener;
import llc.input.Input;
import llc.input.KeyBinding;
import llc.input.KeyboardListener;
import llc.loading.GameLoader;
import llc.logic.Cell;
import llc.logic.Logic;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class LLC implements IKeybindingListener {

	public static final String VERSION = "0.1 INDEV";
	private boolean isRunning = false;
	
	private Profiler profiler = new Profiler();
	private Camera camera;
	private Input input;
	private Renderer renderer;
	private GameLoader gameLoader;
	private Logic logic;
	private GUIRenderer guiRenderer;
	private AudioEngine audioEngine;
	private Timing timing;
	private KeyboardListener keyboardListener;
	
	public int width = 0;
	public int height = 0;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean lastButtonState = false;
	
	private boolean isFullscreen = false;
	private final DisplayMode standartDisplayMode = new DisplayMode(640, 480);
	
	private GUI prevGui = null;
	private boolean isGamePaused = false;
	
	public LLC() {
		this.camera = new Camera(new Vector3f(4, 4, 10), new Vector3f(0, 1.5f, -1), new Vector3f(0, 0, 1));
		this.input = new Input(this, this.camera);
		this.gameLoader = new GameLoader();
		this.logic = new Logic(this.gameLoader.createNewGame("res/maps/areas/map-2_areas.png", this.camera), this.input);
		
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
			}

			@Override
			public void onNewCellHovered(int cell_x, int cell_y) {
			}
		});
		
		this.audioEngine = new AudioEngine();
		this.timing = new Timing();

		this.keyboardListener = new KeyboardListener();
		this.keyboardListener.registerEventHandler(this);
		this.keyboardListener.registerKeyBinding(new KeyBinding("func.fullscreen", Keyboard.KEY_F11, false));
		this.keyboardListener.registerKeyBinding(new KeyBinding("gui.pause", Keyboard.KEY_ESCAPE, false));
	}
	
	/**
	 * Setups the Display and OpenGL. Finally starts the Main-Loop
	 */
	public void startGame() throws LWJGLException {
		this.profiler.start("Setup Display");
		this.initDisplay();
		this.profiler.endStart("Setup OpenGL");
		this.renderer = new Renderer();
		this.renderer.generateGridGeometry(this.logic.getGameState());
		this.profiler.endStart("Setup GUI Renderer");
		this.guiRenderer = new GUIRenderer(this.input, this.audioEngine);
		this.guiRenderer.openGUI(new GUIIngame(this.logic, gameLoader));
		this.profiler.endStart("Setup Audio Engine");
		this.audioEngine.initAudioEngine();
		this.profiler.end();
		this.beginLoop();
	}
	
	/**
	 * Setups the Display
	 */
	private void initDisplay() throws LWJGLException {
		Display.setDisplayMode(this.standartDisplayMode);
		Display.setResizable(true);
		Display.setVSyncEnabled(true);
		Display.setTitle("LLC - " + VERSION);
		Display.create();
		
		Keyboard.create();
		Mouse.create();
	}
	
	/**
	 * Enters the Main-Loop
	 */
	private void beginLoop() {
		this.isRunning = true;
		
		// TODO Remove
//		this.audioEngine.playMusic(EnumMusic.MUSIC1);
		
		this.timing.init();
		while(this.isRunning) {
			int delta = this.timing.getDelta();
			Display.setTitle("FPS " + String.valueOf(this.timing.getFPS())); // TODO Remove
			
			this.handleDisplayResize();
			if(Display.isCloseRequested()) this.isRunning = false;

			// Mouse updates
			this.profiler.start("Mouse updates");
			this.mouseX = Mouse.getX();
			this.mouseY = this.height - Mouse.getY();
			
			if(!this.isGamePaused) {
				this.input.mousePos(this.mouseX, this.mouseY);
				if(Mouse.isButtonDown(0) && !this.lastButtonState) this.input.mouseClick(this.mouseX, this.mouseY);
				this.lastButtonState = Mouse.isButtonDown(0);
			}
			
			// Scrolling
			if(!this.isGamePaused) {
				int scroll = Mouse.getDWheel();
				if(scroll > 0) this.camera.zoom(-1);
				else if(scroll < 0) this.camera.zoom(1);
			}
			
			// Keyboard updates
			this.profiler.endStart("Keyboard updates");
			this.keyboardListener.update();
			
			// Rendering
			this.profiler.endStart("Render game");
			this.renderer.render(this.camera, this.logic.getGameState());
			this.profiler.endStart("Render GUI");
			this.guiRenderer.render(this.width, this.height, this.mouseX, this.mouseY);
			this.profiler.end();
			
			Display.update();
			Display.sync(60);
			
			int error = glGetError();
			if(error != GL_NO_ERROR) System.out.println("GLError " + error + ": " + GLU.gluErrorString(error));
		
			this.timing.updateFPS();
		}
		
		if(Display.isCreated()) Display.destroy();
		this.audioEngine.dispose();
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
			this.guiRenderer.handleDisplayResize(this.width, this.height);
		}
	}

	@Override
	public void onKeyBindingUpdate(KeyBinding keyBinding, boolean isPressed) {
		try {
			if(keyBinding.name.equals("func.fullscreen")) this.toggleFullscreen();
			else if(keyBinding.name.equals("gui.pause") && isPressed) this.togglePauseMenu();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void toggleFullscreen() throws LWJGLException {
		this.isFullscreen = !this.isFullscreen;
	    Display.setDisplayMode(this.isFullscreen ? Display.getDesktopDisplayMode() : this.standartDisplayMode);
	    Display.setFullscreen(this.isFullscreen);
	}
	
	private void togglePauseMenu() {
		this.isGamePaused = !this.isGamePaused;
		if(this.isGamePaused) {
			this.prevGui = this.guiRenderer.getGUI();
			this.guiRenderer.openGUI(new GUIIngameMenu(this.gameLoader, this.logic));
		} else {
			this.guiRenderer.openGUI(this.prevGui);
		}
	}
	
	/**
	 * Closes the Game
	 */
	public void closeGame() {
		this.isRunning = false;
	}
	
}