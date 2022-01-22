package fr.asvadia.api.bungee;

import fr.asvadia.api.bungee.config.YMLPluginConfig;
import fr.asvadia.api.bungee.messaging.RequestManager;
import fr.asvadia.api.bungee.messaging.listener.MessageListener;
import fr.asvadia.api.bungee.packet.PacketManager;
import fr.asvadia.api.bungee.packet.listener.ConnectionListener;
import java.util.HashMap;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeAPI extends Plugin {
  public static BungeeAPI INSTANCE;
  
  YMLPluginConfig config;
  
  MessageListener messageListener;
  
  HashMap<String, RequestManager> requestManagers;
  
  public void onEnable() {
    INSTANCE = this;
    this.requestManagers = new HashMap<>();
    this.messageListener = new MessageListener();
    getProxy().getPluginManager().registerListener(this, (Listener)this.messageListener);
    PacketManager.init();
    RequestManager requestManager = new RequestManager();
    getProxy().registerChannel("asvadia:request");
    getProxy().getPluginManager().registerListener(this, (Listener)new ConnectionListener());
  }
  
  public HashMap<String, RequestManager> getRequestManagers() {
    return this.requestManagers;
  }
  
  public RequestManager getRequestManager(String channel) {
    return this.requestManagers.get(channel);
  }
  
  public MessageListener getMessageListener() {
    return this.messageListener;
  }
}
