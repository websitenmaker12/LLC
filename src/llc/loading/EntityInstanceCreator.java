package llc.loading;

import java.lang.reflect.Type;

import llc.entity.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EntityInstanceCreator implements JsonDeserializer<Entity>, JsonSerializer<Entity>{
	
	public EntityInstanceCreator() {
		
	}
	
	@Override
	public Entity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object= json.getAsJsonObject();
		
		//System.out.println(object.toString());
		Entity en = null;
		
		switch (object.get("type").getAsString()) {
		case "warrior":
			en = new EntityWarrior();
			break;
		case "worker":
			en =  new EntityWorker();
			break;
		case "base":
			en = new EntityBuildingBase();
			break;
		}
		if (en instanceof EntityMovable) {
			((EntityMovable) en).setMoveRange(object.get("moveRange").getAsInt());
		}
		en.setX(object.get("x").getAsFloat());
		en.setY(object.get("y").getAsFloat());
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
		System.out.println("Deserizaler:" + e);
		return e;
	}
}
