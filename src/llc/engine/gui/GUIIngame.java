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

	private Logic logic;
	private GameLoader gameLoader;
	
	private Texture left;
	private Texture middle;
	private Texture right;
	
	public GUIIngame(Logic logic, GameLoader gameLoader) {
		this.logic = logic;
		this.gameLoader = gameLoader;
		
		left = new Texture("res/gui/ingame_left.png");
		middle = new Texture("res/gui/ingame_middle.png");
		right = new Texture("res/gui/ingame_right.png");
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(this, 20, Display.getHeight() - 55, 200, 35, "Buy Warrior") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWarrior());
			}
		});
		
		this.elements.add(new GUIButton(this, 20, Display.getHeight() - 110, 200, 35, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWorker());
			}
		});
		
		this.elements.add(new GUIText(this, 20, 20, "Gold: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Gold:" + logic.getGameState().getActivePlayer().getMinerals());
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() - 110, 20, "Player: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Player " + logic.getGameState().getActivePlayer().playerID);
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 - 40, 20, "Turns left: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Turns left: " + (logic.subTurns - logic.getGameState().moveCount) + "/" + logic.subTurns);
			}
		});
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		left.bind();
		
		RenderUtil.drawTexturedQuad(0, Display.getHeight() - 348 / 1920 * Display.getWidth(), 386 / 1920 * Display.getWidth(), 348 / 1920 * Display.getHeight());
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		super.render(renderer, x, y);
	}
}
