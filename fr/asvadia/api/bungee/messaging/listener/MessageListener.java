package fr.asvadia.api.bungee.messaging.listener;

import fr.asvadia.api.bungee.messaging.event.RequestEvent;
import fr.asvadia.api.common.messaging.listener.MessageListener;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessageListener extends MessageListener<ProxiedPlayer> implements Listener {
  @EventHandler
  public void onPluginMessageReceived(PluginMessageEvent event) {
    if (event instanceof ProxiedPlayer && event.getTag().equals("asvadia:request"))
      event(event.getReceiver(), event.getData()); 
  }
  
  public void call(ReceiveRequest<ProxiedPlayer> receiveRequest) {
    ProxyServer.getInstance().getPluginManager().callEvent((Event)new RequestEvent(receiveRequest));
  }
}
