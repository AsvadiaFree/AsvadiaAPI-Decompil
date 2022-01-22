package fr.asvadia.api.bungee.config;

import fr.asvadia.api.common.config.YMLPluginConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class YMLPluginConfig extends YMLPluginConfig<Plugin, Configuration> {
  public YMLPluginConfig(Plugin plugin, File file) {
    this(plugin, file, file.getName());
  }
  
  public YMLPluginConfig(Plugin plugin, File file, String resource) {
    super(plugin, file, resource);
  }
  
  public void load() {
    try {
      if (!this.file.exists()) {
        this.file.getParentFile().mkdirs();
        InputStream in = ((Plugin)this.plugin).getResourceAsStream(this.resource);
        if (in != null) {
          Files.copy(in, this.file.toPath(), new java.nio.file.CopyOption[0]);
        } else {
          this.file.createNewFile();
        } 
      } 
      this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void save() {
    try {
      ConfigurationProvider.getProvider(YamlConfiguration.class).save((Configuration)this.configuration, this.file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public boolean isSet(String where) {
    return ((Configuration)this.configuration).contains(where);
  }
}
