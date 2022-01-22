package fr.asvadia.api.bukkit.menu.form;

import org.bukkit.entity.Player;

public interface Builder {
  AForm build(Player paramPlayer);
  
  AForm build();
}
