package fr.asvadia.api.bukkit.menu;

import fr.asvadia.api.bukkit.menu.form.AButtonComponent;
import fr.asvadia.api.bukkit.menu.form.AForm;
import fr.asvadia.api.bukkit.menu.form.AFormCustom;
import fr.asvadia.api.bukkit.menu.form.ButtonForm;
import fr.asvadia.api.bukkit.menu.inventory.AInventory;
import fr.asvadia.api.bukkit.menu.inventory.AInventoryGUI;
import fr.asvadia.api.bukkit.menu.inventory.button.Button;
import fr.asvadia.api.bukkit.menu.inventory.button.ClickButton;
import fr.asvadia.api.bukkit.util.Creator;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.geysermc.floodgate.api.FloodgateApi;

public class MenuAPI implements Listener {
  static Map<Inventory, AInventory> inventories = new HashMap<>();
  
  public static void init() {
    inventories = new HashMap<>();
  }
  
  public static AInventoryGUI gui(ConfigurationSection section) {
    return guiBuilder(section).build();
  }
  
  public static AInventoryGUI.Builder guiBuilder(ConfigurationSection section) {
    AInventoryGUI.Builder builder = AInventoryGUI.builder();
    if (section.isSet("title"))
      builder.title(section.getString("title")); 
    if (section.isSet("size"))
      builder.size(section.getInt("size")); 
    if (section.isSet("item")) {
      ConfigurationSection itemSection = section.getConfigurationSection("item");
      itemSection.getKeys(false).forEach(key -> {
            ItemStack itemStack = Creator.item(itemSection.getConfigurationSection(key));
            if (key.contains(";")) {
              for (String s : key.split(";"))
                builder.item(Integer.parseInt(s), itemStack); 
            } else {
              builder.item(Integer.parseInt(key), itemStack);
            } 
          });
    } 
    return builder;
  }
  
  public static void sendForm(Player player, AForm aform) {
    if (aform instanceof ButtonForm) {
      ButtonForm aFormButton = (ButtonForm)aform;
      aform.getForm().setResponseHandler(s -> {
            AButtonComponent aButtonComponent = aFormButton.getButtonResponse(s);
            if (aButtonComponent != null && aButtonComponent.getResponse() != null)
              aButtonComponent.getResponse().accept(player); 
          });
    } else if (aform instanceof AFormCustom) {
      AFormCustom aFormCustom = (AFormCustom)aform;
      aform.getForm().setResponseHandler(s -> {
            if (aFormCustom.getResponse() != null)
              aFormCustom.getResponse().accept(player, aFormCustom.getForm().parseResponse(s)); 
          });
    } 
    FloodgateApi.getInstance().sendForm(player.getUniqueId(), aform.getForm());
  }
  
  public static Map<Inventory, AInventory> getInventories() {
    return inventories;
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getInventory().getType() == InventoryType.CHEST && 
      inventories.containsKey(event.getInventory())) {
      if ((event.getClick() != ClickType.SHIFT_LEFT && event.getClick() != ClickType.SHIFT_RIGHT) || event.getInventory() == event.getClickedInventory()) {
        AInventory inventory = inventories.get(event.getClickedInventory());
        if (inventory instanceof AInventoryGUI) {
          AInventoryGUI inventoryGUI = (AInventoryGUI)inventory;
          if (inventoryGUI.getButtons().containsKey(Integer.valueOf(event.getSlot()))) {
            Button button = inventoryGUI.getButton(event.getSlot());
            if (button instanceof fr.asvadia.api.bukkit.menu.inventory.button.VoidButton)
              return; 
            if (button instanceof ClickButton) {
              event.setCancelled(true);
              ((ClickButton)button).getConsumer().accept(event.getWhoClicked(), inventoryGUI, event.getClick());
            } 
          } 
        } 
      } 
      event.setCancelled(true);
    } 
  }
  
  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    if (event.getInventory().getType() == InventoryType.CHEST && 
      inventories.containsKey(event.getInventory())) {
      if (event.getType() == DragType.SINGLE) {
        AInventory inventory = inventories.get(event.getInventory());
        if (inventory instanceof AInventoryGUI) {
          AInventoryGUI inventoryGUI = (AInventoryGUI)inventory;
          int slot = ((Integer)event.getInventorySlots().iterator().next()).intValue();
          if (inventoryGUI.getButtons().containsKey(Integer.valueOf(slot))) {
            Button button = inventoryGUI.getButton(slot);
            if (button instanceof fr.asvadia.api.bukkit.menu.inventory.button.VoidButton)
              return; 
            if (button instanceof ClickButton) {
              event.setCancelled(true);
              ((ClickButton)button).getConsumer().accept(event.getWhoClicked(), inventoryGUI, ClickType.LEFT);
            } 
          } 
        } 
      } 
      event.setCancelled(true);
    } 
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (event.getInventory().getType() == InventoryType.CHEST && 
      inventories.containsKey(event.getInventory()))
      ((AInventory)inventories.get(event.getInventory())).remove((Player)event.getPlayer()); 
  }
}
