package fr.asvadia.api.bukkit.messaging;

import fr.asvadia.api.bukkit.BukkitAPI;
import fr.asvadia.api.common.messaging.RequestManager;
import java.io.ByteArrayOutputStream;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RequestManager extends RequestManager<Player> {
  public String getKey() {
    return "bibou";
  }
  
  public void sendData(Player player, ByteArrayOutputStream out) {
    player.sendPluginMessage((Plugin)BukkitAPI.INSTANCE, "asvadia:request", out.toByteArray());
  }
}
