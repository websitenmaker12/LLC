package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.engine.res.Texture;
import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.loading.GameLoader;
import llc.logic.Logic;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIIngame extends GUI {

	protected Logic logic;
	
	private Texture ui;
	
	public GUIIngame(Logic logic, GameLoader gameLoader) {
		this.logic = logic;
		
		this.ui = new Texture("res/gui/ui.png");
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
//		this.elements.add(new GUIButton(this, Display.getWidth() - 212, Display.getHeight() - 55, 200, 35, "Buy Warrior") {
//			public void onClick(int x, int y) {
//				logic.buyEntity(new EntityWarrior());
//			}
//		});
//		
//		this.elements.add(new GUIButton(this, Display.getWidth() - 212, Display.getHeight() - 110, 200, 35, "Buy Worker") {
//			public void onClick(int x, int y) {
//				logic.buyEntity(new EntityWorker());
//			}
//		});
//		
//		this.elements.add(new GUIText(this, 50, 20, 0, 0, "Gold: ", Color.orange) {
//			@Override
//			public void update(int x, int y) {
//				setText("Gold:" + logic.getGameState().getActivePlayer().getMinerals());
//				if (logic.markMinerals) {
//					this.mark(Color.red, 1500);
//					logic.markMinerals = false;
//				}
//			}
//		});
//		
//		this.elements.add(new GUIText(this, Display.getWidth() - 60, 20, 0, 0, "Player: ", Color.orange) {
//			@Override
//			public void update(int x, int y) {
//				setText("Player " + logic.getGameState().getActivePlayer().playerID);
//			}
//		});
//		
//		this.elements.add(new GUIText(this, Display.getWidth() / 2 , 20, 0, 0, "Turns left: ", Color.orange) {
//			@Override
//			public void update(int x, int y) {
//				setText("Turns left: " + (logic.subTurns - logic.getGameState().moveCount) + "/" + logic.subTurns);
//			}
//		});
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// UI Texture
		this.ui.bind();
		RenderUtil.drawTexturedQuad(0, 0, Display.getWidth(), Display.getHeight());
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		super.render(renderer, x, y);
	}
}
