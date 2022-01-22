package fr.asvadia.api.bukkit;

import fr.asvadia.api.bukkit.menu.MenuAPI;
import fr.asvadia.api.bukkit.menu.inventory.AInventory;
import fr.asvadia.api.bukkit.messaging.RequestManager;
import fr.asvadia.api.bukkit.messaging.listener.MessageListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BukkitAPI extends JavaPlugin {
  public static BukkitAPI INSTANCE;
  
  public void onEnable() {
    INSTANCE = this;
    Bukkit.getPluginManager().registerEvents((Listener)new MenuAPI(), (Plugin)INSTANCE);
    RequestManager requestManager = new RequestManager();
    MessageListener messageListener = new MessageListener();
    Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)this, "asvadia:request", (PluginMessageListener)messageListener);
    Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "asvadia:request");
  }
  
  public void onDisable() {
    MenuAPI.getInventories().values().forEach(AInventory::remove);
  }
}
