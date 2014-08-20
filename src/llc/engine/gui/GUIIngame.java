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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUIIngame extends GUI {

	protected static final String strGold = Translator.translate("gui.gold") + ": ";
	protected static final String strPlayer = Translator.translate("gui.player") + ": ";
	protected static final String strTurns = Translator.translate("gui.turnsLeft") + ": ";
	
	protected Logic logic;
	private Texture ui;
	
	private GUIGroup baseGroup;
	private GUIGroup workerGroup;
	
	public GUIIngame(Logic logic, GameLoader gameLoader) {
		this.logic = logic;
		
		this.ui = new Texture("res/gui/ui.png");
	}
	
	@Override
	public void onOpen() {
		super.onOpen();
		
		float scaleX = (float)Display.getWidth() / 1024F;
		float scaleY = (float)Display.getHeight() / 768F;
		
		this.elements.add(new GUIText(this, 10 * scaleX, 614 * scaleY, 0, 0, "", Color.white) {
			public void update(int x, int y) {
				this.setText(strGold + logic.getGameState().getActivePlayer().getMinerals());
				if (logic.markMinerals) {
					this.mark(Color.red, 1500);
					logic.markMinerals = false;
				}
			}
		});
		
		this.elements.add(new GUIText(this, 10 * scaleX, 640 * scaleY, 0, 0, "", Color.white) {
			public void update(int x, int y) {
				this.setText(strPlayer + logic.getGameState().getActivePlayer().playerID);
			}
		});
		
		this.elements.add(new GUIText(this, 10 * scaleX, 666 * scaleY, 0, 0, "", Color.white) {
			public void update(int x, int y) {
				this.setText(strTurns + (logic.subTurns - logic.getGameState().moveCount));
			}
		});
		
		// Groups
		this.baseGroup = new GUIGroup(this, false);
		this.workerGroup = new GUIGroup(this, false);
		this.elements.add(this.baseGroup);
		this.elements.add(this.workerGroup);
		
		this.baseGroup.add(new GUIHotkeyButton(this, (811 + 50 * 0) * scaleX, 611 * scaleY, 46 * scaleX, 46 * scaleY, Translator.translate("gui.desc.buyWarrior"), Keyboard.KEY_A) {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWarrior());
			}
		});
		
		this.baseGroup.add(new GUIHotkeyButton(this, (811 + 50 * 1) * scaleX, 611 * scaleY, 46 * scaleX, 46 * scaleY, Translator.translate("gui.desc.buyWorker"), Keyboard.KEY_W) {
			public void onClick(int x, int y) {
				logic.buyEntity(new EntityWorker());
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
				this.baseGroup.setVisible(entity instanceof EntityBuildingBase);
				this.workerGroup.setVisible(entity instanceof EntityWorker);
			}
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		super.render(renderer, x, y);
	}
}
