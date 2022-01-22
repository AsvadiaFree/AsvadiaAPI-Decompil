package fr.asvadia.api.bukkit.messaging.listener;

import fr.asvadia.api.bukkit.messaging.event.RequestEvent;
import fr.asvadia.api.common.messaging.listener.MessageListener;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener extends MessageListener<Player> implements PluginMessageListener {
  public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
    if (channel.equals("asvadia:request"))
      event(player, bytes); 
  }
  
  public void call(ReceiveRequest<Player> receiveRequest) {
    Bukkit.getPluginManager().callEvent((Event)new RequestEvent(receiveRequest));
  }
}
