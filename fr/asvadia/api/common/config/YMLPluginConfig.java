package fr.asvadia.api.common.config;

import java.io.File;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public abstract class YMLPluginConfig<P, C> {
  protected File file;
  
  protected P plugin;
  
  protected C configuration;
  
  protected String resource;
  
  public YMLPluginConfig(P plugin, File file, String resource) {
    this.plugin = plugin;
    this.file = file.getName().contains(".yml") ? file : new File("" + file + ".yml");
    this.resource = resource.contains(".yml") ? resource : (resource + ".yml");
    if (this.file.isDirectory())
      throw new IllegalArgumentException("Only file are allowed."); 
    load();
  }
  
  public abstract void load();
  
  public abstract void save();
  
  public C getResource() {
    return this.configuration;
  }
  
  public File getFile() {
    return this.file;
  }
  
  public abstract boolean isSet(String paramString);
  
  public <T> T get(String where) {
    return get(where, true);
  }
  
  public <T> T get(String where, boolean color) {
    if (where != null)
      try {
        Object data = this.configuration.getClass().getMethod("get", new Class[] { String.class }).invoke(this.configuration, new Object[] { where });
        if (color)
          if (data instanceof List) {
            if (((List)data).size() > 0 && ((List)data).get(0) instanceof String)
              ((List)data).replaceAll(s -> ChatColor.translateAlternateColorCodes('&', s)); 
          } else if (data instanceof String) {
            data = ChatColor.translateAlternateColorCodes('&', (String)data);
          }  
        return (T)data;
      } catch (Exception exception) {} 
    return null;
  }
}
