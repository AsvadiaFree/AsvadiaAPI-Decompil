package fr.asvadia.api.bukkit.menu.form;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.impl.SimpleFormImpl;

public class AFormList implements AForm, ButtonForm {
  SimpleForm form;
  
  public AFormList(SimpleForm form) {
    this.form = form;
  }
  
  public SimpleForm getForm() {
    return this.form;
  }
  
  public AButtonComponent getButtonResponse(String s) {
    return (AButtonComponent)this.form.parseResponse(s).getClickedButton();
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public static class Builder implements AForm.Builder {
    String title = "";
    
    String content = "";
    
    List<AButtonComponent> buttons = new ArrayList<>();
    
    public Builder title(String title) {
      if (title != null)
        this.title = title; 
      return this;
    }
    
    public Builder button(String text) {
      return button(text, null);
    }
    
    public Builder button(String text, Consumer<Player> consumer) {
      return button(new AButtonComponent(text, consumer));
    }
    
    public Builder button(AButtonComponent button) {
      if (button != null)
        this.buttons.add(button); 
      return this;
    }
    
    public Builder content(String content) {
      this.content = ChatColor.translateAlternateColorCodes('&', content);
      return this;
    }
    
    public AFormList build(Player player) {
      if (Bukkit.getPluginManager().getPlugin("PlaceHolderAPI") != null) {
        this.title = PlaceholderAPI.setPlaceholders(player, this.title);
        this.content = PlaceholderAPI.setPlaceholders(player, this.content);
        this.buttons.forEach(button -> button.setText(PlaceholderAPI.setPlaceholders(player, button.getText())));
      } 
      return build();
    }
    
    public AFormList build() {
      return new AFormList((SimpleForm)new SimpleFormImpl(this.title, this.content, new ArrayList<>(this.buttons)));
    }
  }
}
