package llc.loading;

import java.lang.reflect.Type;

import llc.entity.*;
import llc.logic.GameState;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EntityInstanceCreator implements JsonDeserializer<Entity>, JsonSerializer<Entity>{

	private GameState state;
	
	public EntityInstanceCreator(GameState state) {
		this.state = state;
	}
	
	@Override
	public Entity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object= json.getAsJsonObject();
		
		System.out.println(object.toString());
		Entity en = null;
		
		switch (object.get("type").getAsString()) {
		case "warrior":
			en = new EntityWarrior();
		case "worker":
			en =  new EntityWorker();
		case "base":
			en = new EntityBuildingBase();
		}
		
		en.health = object.get("health").getAsInt();
		
		if (object.get("player").getAsInt() == 1) {
			en.setPlayer(state.getPlayer1());
		}
		if (object.get("player").getAsInt() == 2) {
			en.setPlayer(state.getPlayer2());
		}
		return en;
	}

	@Override
	public JsonElement serialize(Entity entity, Type type, JsonSerializationContext context) {
		
		JsonElement e = context.serialize(entity);
		
		if (entity instanceof EntityWarrior) {
			e.getAsJsonObject().addProperty("type", "warrior");
		}
		else if (entity instanceof EntityWorker) {
			e.getAsJsonObject().addProperty("type", "worker");
		}
		else if (entity instanceof EntityBuildingBase) {
			e.getAsJsonObject().addProperty("type", "base");
		}
		if (entity.getPlayer().equals(state.getPlayer1())) {
			e.getAsJsonObject().remove("player");
			e.getAsJsonObject().addProperty("player", 1);
		}
		if (entity.getPlayer().equals(state.getPlayer2())) {
			e.getAsJsonObject().remove("player");
			e.getAsJsonObject().addProperty("player", 2);
		}
		//System.out.println(e);
		return e;
	}
	
	public GameState getState() {
		return state;
	}

	public void setGameState(GameState s) {
		state = s;
	}
}
