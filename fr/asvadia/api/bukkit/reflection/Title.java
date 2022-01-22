package fr.asvadia.api.bukkit.reflection;

import org.bukkit.entity.Player;

public class Title {
  String title;
  
  String subTitle;
  
  int fadeIn;
  
  int stay;
  
  int fadeOut;
  
  public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
    this.title = title;
    this.subTitle = subtitle;
    this.fadeIn = fadeIn;
    this.stay = stay;
    this.fadeOut = fadeOut;
  }
  
  public void send(Player player) {
    player.sendTitle(this.title, this.subTitle, this.fadeIn, this.stay, this.fadeOut);
  }
}
