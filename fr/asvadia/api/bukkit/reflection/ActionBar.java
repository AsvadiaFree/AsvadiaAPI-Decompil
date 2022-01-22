package fr.asvadia.api.bukkit.reflection;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBar {
  String message;
  
  public ActionBar(String message) {
    this.message = message;
  }
  
  public void send(Player player) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(this.message));
  }
}
