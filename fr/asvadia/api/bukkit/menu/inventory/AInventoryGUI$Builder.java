package fr.asvadia.api.bukkit.menu.inventory;

import fr.asvadia.api.bukkit.menu.inventory.button.Button;
import fr.asvadia.api.bukkit.menu.inventory.button.ClickButton;
import fr.asvadia.api.bukkit.menu.inventory.button.VoidButton;
import java.util.HashMap;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class Builder implements AInventory.Builder {
  String title = "";
  
  int size = 27;
  
  HashMap<Integer, ItemStack> items = new HashMap<>();
  
  HashMap<Integer, Button> buttons = new HashMap<>();
  
  public Builder title(String title) {
    this.title = title;
    return this;
  }
  
  public Builder size(int size) {
    this.size = size;
    return this;
  }
  
  public Builder item(int slot, ItemStack itemStack) {
    this.items.put(Integer.valueOf(slot), itemStack);
    return this;
  }
  
  public Builder clickButton(int slot, TriConsumer<Player, AInventoryGUI, ClickType> consumer) {
    return button(slot, (Button)new ClickButton(slot, consumer));
  }
  
  public Builder voidButton(int slot) {
    return button(slot, (Button)new VoidButton(slot));
  }
  
  public Builder button(int slot, Button button) {
    this.buttons.put(Integer.valueOf(slot), button);
    return this;
  }
  
  public HashMap<Integer, ItemStack> getItems() {
    return this.items;
  }
  
  public HashMap<Integer, Button> getButtons() {
    return this.buttons;
  }
  
  public AInventoryGUI build() {
    AInventoryGUI inventoryGUI = new AInventoryGUI(Bukkit.createInventory(null, this.size, this.title), this.buttons);
    this.items.forEach((integer, itemStack) -> inventoryGUI.getInventory().setItem(integer.intValue(), itemStack));
    return inventoryGUI;
  }
}
