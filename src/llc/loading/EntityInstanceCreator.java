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
		en.setPlayer(object.get("player").getAsInt());
		return en;
	}

	@Override
	public JsonElement serialize(Entity entity, Type type, JsonSerializationContext context) {
		
		JsonElement e = context.serialize(entity);
		
		//System.out.println(e);
		
		if (entity instanceof EntityWarrior) {
			e.getAsJsonObject().addProperty("type", "warrior");
		}
		else if (entity instanceof EntityWorker) {
			e.getAsJsonObject().addProperty("type", "worker");
		}
		else if (entity instanceof EntityBuildingBase) {
			e.getAsJsonObject().addProperty("type", "base");
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
