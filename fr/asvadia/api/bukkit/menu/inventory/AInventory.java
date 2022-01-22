package fr.asvadia.api.bukkit.menu.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface AInventory {
  Inventory getInventory();
  
  default void remove() {
    getInventory().getViewers().forEach(humanEntity -> remove((Player)humanEntity));
  }
  
  void remove(Player paramPlayer);
  
  public static interface Builder {
    AInventory build();
  }
}
