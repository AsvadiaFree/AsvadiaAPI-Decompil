package fr.asvadia.api.bungee.messaging;

import fr.asvadia.api.common.messaging.RequestManager;
import java.io.ByteArrayOutputStream;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RequestManager extends RequestManager<ProxiedPlayer> {
  public String getKey() {
    return "bibou";
  }
  
  public void sendData(ProxiedPlayer player, ByteArrayOutputStream out) {
    player.getServer().sendData("asvadia:request", out.toByteArray());
  }
}
