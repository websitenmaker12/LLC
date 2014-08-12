package llc.engine;

import llc.loading.GameState;

import org.lwjgl.opengl.GL11;

public class Renderer {

	public Renderer() {
		GL11.glClearColor(0F, 0F, 0F, 1F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void handleDisplayResize(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public void render(Camera camera, GameState gameState) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		
		GL11.glTranslatef(camera.pos.x, camera.pos.y, camera.pos.z);
	}
	
}
