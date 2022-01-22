package fr.asvadia.api.bukkit.menu.form;

import org.bukkit.entity.Player;
import org.geysermc.cumulus.Form;

public interface AForm {
  Form getForm();
  
  public static interface Builder {
    AForm build(Player param1Player);
    
    AForm build();
  }
}
