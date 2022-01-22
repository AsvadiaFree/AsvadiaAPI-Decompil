package fr.asvadia.api.bukkit.menu.form;

import java.util.function.Consumer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.impl.ModalFormImpl;

public class Builder implements AForm.Builder {
  String title = "";
  
  String content = "";
  
  AButtonComponent trueButton = AButtonComponent.DEFAULT;
  
  AButtonComponent falseButton = AButtonComponent.DEFAULT;
  
  public Builder title(String title) {
    if (title != null)
      this.title = title; 
    return this;
  }
  
  public Builder content(String content) {
    this.content = ChatColor.translateAlternateColorCodes('&', content);
    return this;
  }
  
  public Builder trueButton(String text) {
    return trueButton(text, null);
  }
  
  public Builder trueButton(String text, Consumer<Player> consumer) {
    return trueButton(new AButtonComponent(text, consumer));
  }
  
  public Builder trueButton(AButtonComponent button) {
    if (button != null)
      this.trueButton = button; 
    return this;
  }
  
  public Builder falseButton(String text) {
    return falseButton(text, null);
  }
  
  public Builder falseButton(String text, Consumer<Player> consumer) {
    return falseButton(new AButtonComponent(text, consumer));
  }
  
  public Builder falseButton(AButtonComponent button) {
    if (button != null)
      this.falseButton = button; 
    return this;
  }
  
  public AFormInfo build(Player player) {
    if (Bukkit.getPluginManager().getPlugin("PlaceHolderAPI") != null) {
      this.title = PlaceholderAPI.setPlaceholders(player, this.title);
      this.content = PlaceholderAPI.setPlaceholders(player, this.content);
      this.trueButton.setText(PlaceholderAPI.setPlaceholders(player, this.trueButton.getText()));
      this.falseButton.setText(PlaceholderAPI.setPlaceholders(player, this.falseButton.getText()));
    } 
    return build();
  }
  
  public AFormInfo build() {
    return new AFormInfo((ModalForm)new ModalFormImpl(this.title, this.content, this.trueButton.getText(), this.falseButton.getText()), this.trueButton, this.falseButton);
  }
}
