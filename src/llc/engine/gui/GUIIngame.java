package llc.engine.gui;

import llc.engine.GUIRenderer;
import llc.engine.Translator;
import llc.engine.res.Texture;
import llc.entity.Entity;
import llc.entity.EntityBuildingBase;
import llc.entity.EntityWarrior;
import llc.entity.EntityWorker;
import llc.loading.GameLoader;
import llc.logic.Cell;
import llc.logic.Logic;
import llc.util.RenderUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIIngame extends GUI {

	protected Logic logic;
	
	private Texture ui;
	private Texture map;
	
	public GUIIngame(Logic logic, GameLoader gameLoader) {
		this.logic = logic;
		
		this.ui = new Texture("res/gui/ui.png");
		this.map = new Texture(this.logic.getGameState().levelPath);
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		this.elements.add(new GUIButton(this, Display.getWidth() - 212, Display.getHeight() - 55, 200, 35, "Buy Warrior") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWarrior());
			}
		});
		
		this.elements.add(new GUIButton(this, Display.getWidth() - 212, Display.getHeight() - 110, 200, 35, "Buy Worker") {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWorker());
			}
		});
		
		this.elements.add(new GUIText(this, 50, 20, 0, 0, "Gold: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Gold:" + logic.getGameState().getActivePlayer().getMinerals());
				if (logic.markMinerals) {
					this.mark(Color.red, 1500);
					logic.markMinerals = false;
				}
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() - 60, 20, 0, 0, "Player: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Player " + logic.getGameState().getActivePlayer().playerID);
			}
		});
		
		this.elements.add(new GUIText(this, Display.getWidth() / 2 , 20, 0, 0, "Turns left: ", Color.orange) {
			@Override
			public void update(int x, int y) {
				setText("Turns left: " + (logic.subTurns - logic.getGameState().moveCount) + "/" + logic.subTurns);
			}
		});
	}
	
	@Override
	public void render(GUIRenderer renderer, int x, int y) {
		float scaleX = (float)Display.getWidth() / 1024F;
		float scaleY = (float)Display.getHeight() / 768F;
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// UI Texture
		this.ui.bind();
		RenderUtil.drawTexturedQuad(0, 0, Display.getWidth(), Display.getHeight());
		
		// Mini-Map
		this.map.bind();
		
		GL11.glPushMatrix();
		RenderUtil.drawTexturedQuad(10 * scaleX, 614 * scaleY, 167 * scaleX, 144 * scaleY);
		GL11.glPopMatrix();
		
		// Entity data
		if(this.logic.getGameState().selectedCell != null) {
			Cell cell = this.logic.getGameState().selectedCell;
			
			if(cell.containsEntity()) {
				Entity entity = cell.getEntity();
				
				// Name
				String s = Translator.translate(entity.getName());
				renderer.font.drawString(497 * scaleX - renderer.font.getWidth(s) / 2, 603F * scaleY, s, Color.white);
				
				// Health
				s = Translator.translate("gui.health") + ": " + entity.health + "/" + entity.maxHealth;
				renderer.font.drawString(237 * scaleX, 660F * scaleY, s, Color.white);
				
				// Functions
				if(entity instanceof EntityBuildingBase) {
				} else if(entity instanceof EntityWorker) {
				}
			}
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		super.render(renderer, x, y);
	}
}
