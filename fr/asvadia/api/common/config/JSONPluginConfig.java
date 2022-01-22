package fr.asvadia.api.common.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import org.bukkit.plugin.Plugin;

public class JSONPluginConfig {
  JsonObject object;
  
  File file;
  
  String resource;
  
  Plugin plugin;
  
  public JSONPluginConfig(Plugin plugin, File file) {
    this(plugin, file, file.getName());
  }
  
  public JSONPluginConfig(Plugin plugin, File file, String resource) {
    this.plugin = plugin;
    this.file = file.getName().contains(".json") ? file : new File("" + file + ".json");
    this.resource = resource.contains(".json") ? resource : (resource + ".json");
    load();
  }
  
  public void load() {
    try {
      if (!this.file.exists()) {
        this.file.getParentFile().mkdirs();
        InputStream in = this.plugin.getResource(this.resource);
        if (in != null) {
          Files.copy(in, this.file.toPath(), new java.nio.file.CopyOption[0]);
        } else {
          this.file.createNewFile();
        } 
      } 
      JsonElement reader = (new JsonParser()).parse(new FileReader(this.file));
      if (reader.isJsonObject()) {
        this.object = (JsonObject)reader;
      } else {
        this.object = new JsonObject();
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void save() {
    try {
      FileWriter fw = new FileWriter(this.file);
      fw.write((new GsonBuilder()).setPrettyPrinting().create().toJson((JsonElement)this.object));
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public JsonObject getObject() {
    return this.object;
  }
  
  public File getFile() {
    return this.file;
  }
  
  public <T> T get(String key) {
    if (this.object.has(key))
      return (T)this.object.get(key); 
    return null;
  }
}
