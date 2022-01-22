package fr.asvadia.api.bukkit.menu.inventory.button;

import fr.asvadia.api.bukkit.menu.inventory.AInventoryGUI;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ClickButton extends Button {
  TriConsumer<Player, AInventoryGUI, ClickType> consumer;
  
  public ClickButton(int slot, TriConsumer<Player, AInventoryGUI, ClickType> consumer) {
    super(slot);
    this.consumer = consumer;
  }
  
  public TriConsumer<Player, AInventoryGUI, ClickType> getConsumer() {
    return this.consumer;
  }
}
