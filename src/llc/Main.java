package llc;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

public class Main {

	public static void main(String[] args) {
		try {
			//Display.setDisplayMode(new DisplayMode(640, 480));
			//Display.setResizable(false);
			ContextCapabilities c = GLContext.getCapabilities();
			
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		while(!Display.isCloseRequested()) {
		}
		
		if(Display.isCreated()) Display.destroy();
		System.exit(0);
	}

}
