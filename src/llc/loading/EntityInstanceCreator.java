package llc.loading;

import java.lang.reflect.Type;

import llc.entity.Entity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EntityInstanceCreator implements JsonDeserializer<Entity>{

	@Override
	public Entity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object= json.getAsJsonObject();
		
		
		
		return null;
	}


}
