package fr.asvadia.api.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JSONConfig {
  public static final Gson GSON = new Gson();
  
  public static final Gson PRETTY_GSON = (new GsonBuilder()).setPrettyPrinting().create();
  
  JsonObject jsonObject;
  
  public JSONConfig() {
    this(new JsonObject());
  }
  
  public JSONConfig(String json) {
    this((new JsonParser()).parse(json).getAsJsonObject());
  }
  
  public JSONConfig(JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }
  
  public JsonObject getConfig() {
    return this.jsonObject;
  }
  
  public String toJson() {
    return GSON.toJson((JsonElement)this.jsonObject);
  }
  
  public String toJsonPretty() {
    return PRETTY_GSON.toJson((JsonElement)this.jsonObject);
  }
  
  public boolean isSet(String path) {
    JsonObject getter = this.jsonObject;
    for (String arg : path.split("\\.")) {
      if (getter.get(arg) == null)
        return false; 
    } 
    return true;
  }
  
  public JsonElement get(String path) {
    JsonElement jsonElement;
    JsonObject jsonObject = this.jsonObject;
    String[] args = path.split("\\.");
    for (int i = 0; i < args.length; i++) {
      jsonElement = jsonObject.getAsJsonObject().get(args[i]);
      if (jsonElement == null)
        return null; 
      if (!jsonElement.isJsonObject()) {
        if (i == args.length - 1)
          break; 
        return null;
      } 
    } 
    return jsonElement;
  }
  
  public JSONConfig getConfig(String path) {
    return new JSONConfig(get(path).getAsJsonObject());
  }
  
  public Integer getInt(String path) {
    return Integer.valueOf(get(path).getAsInt());
  }
  
  public Double getDouble(String path) {
    return Double.valueOf(get(path).getAsDouble());
  }
  
  public String getString(String path) {
    return get(path).getAsString();
  }
  
  public Boolean getBoolean(String path) {
    return Boolean.valueOf(get(path).getAsBoolean());
  }
  
  public Long getLong(String path) {
    return Long.valueOf(get(path).getAsLong());
  }
  
  public void set(String path, Object object) {
    if (object instanceof JsonElement) {
      set(path, (JsonElement)object);
    } else if (object instanceof Number) {
      set(path, (JsonElement)new JsonPrimitive((Number)object));
    } else if (object instanceof Boolean) {
      set(path, (JsonElement)new JsonPrimitive((Boolean)object));
    } else {
      set(path, GSON.toJsonTree(object));
    } 
  }
  
  public void set(String path, JsonElement jsonElement) {
    JsonObject section = this.jsonObject;
    String[] args = path.split("\\.");
    for (int i = 0; i < args.length - 1; i++) {
      JsonObject jsonObject;
      JsonElement value = section.get(args[i]);
      if (value == null || !value.isJsonObject()) {
        jsonObject = new JsonObject();
        section.add(args[i], (JsonElement)jsonObject);
      } 
      section = jsonObject.getAsJsonObject();
    } 
    section.add(args[args.length - 1], jsonElement);
  }
  
  public int size() {
    return this.jsonObject.entrySet().size();
  }
}
