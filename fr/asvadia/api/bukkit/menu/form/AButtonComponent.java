package fr.asvadia.api.bukkit.menu.form;

import java.util.function.Consumer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.util.FormImage;

public class AButtonComponent implements ButtonComponent {
  public static AButtonComponent DEFAULT = new AButtonComponent("Button", null);
  
  String text;
  
  FormImage image;
  
  Consumer<Player> response;
  
  public AButtonComponent(String text, Consumer<Player> response) {
    this(text, null, response);
  }
  
  public AButtonComponent(String text, FormImage image, Consumer<Player> response) {
    if (text != null) {
      this.text = ChatColor.translateAlternateColorCodes('&', text);
    } else {
      this.text = "";
    } 
    if (image != null)
      this.image = image; 
    this.response = response;
  }
  
  public Consumer<Player> getResponse() {
    return this.response;
  }
  
  public String getText() {
    return this.text;
  }
  
  public FormImage getImage() {
    return this.image;
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  public void setResponse(Consumer<Player> response) {
    this.response = response;
  }
}
