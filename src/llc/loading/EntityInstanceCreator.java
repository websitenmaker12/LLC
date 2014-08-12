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
		}
		
		en.health = object.get("health").getAsInt();
		
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
		System.out.println(e);
		return e;
	}


}
