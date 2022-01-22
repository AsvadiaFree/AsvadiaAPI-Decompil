package fr.asvadia.api.bukkit.config;

import com.google.common.base.Charsets;
import fr.asvadia.api.common.config.YMLPluginConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YMLPluginConfig extends YMLPluginConfig<Plugin, FileConfiguration> {
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
        InputStream in = ((Plugin)this.plugin).getResource(this.resource);
        if (in != null) {
          Files.copy(in, this.file.toPath(), new java.nio.file.CopyOption[0]);
        } else {
          this.file.createNewFile();
        } 
      } 
      this.configuration = new YamlConfiguration();
      ((FileConfiguration)this.configuration).load(new InputStreamReader(new FileInputStream(this.file), Charsets.UTF_8));
    } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
      e.printStackTrace();
    } 
  }
  
  public void save() {
    try {
      ((FileConfiguration)this.configuration).save(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public boolean isSet(String where) {
    return ((FileConfiguration)this.configuration).isSet(where);
  }
}
