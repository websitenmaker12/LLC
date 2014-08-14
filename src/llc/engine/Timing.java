package llc.engine;

import org.lwjgl.Sys;

public class Timing {

	private long lastFrame;
	private long lastFPS;
	
	private int fpsCount;
	private int fps;
	
	public void init() {
		this.getDelta();
		this.lastFPS = this.getTime();
	}
	
	public int getDelta() {
		long time = this.getTime();
		int delta = (int)(time - this.lastFrame);
		this.lastFrame = time;
		
		return delta;
	}
	
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS() {
		if(this.getTime() - this.lastFPS > 1000) {
			this.fps = this.fpsCount;
			this.fpsCount = 0;
			this.lastFPS += 1000;
		}
		
		this.fpsCount++;
	}
	
	public int getFPS() {
		return this.fps;
	}
	
}
