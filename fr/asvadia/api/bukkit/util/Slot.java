package fr.asvadia.api.bukkit.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum Slot {
  HEAD("head"),
  CHEST("chest"),
  FEET("feet"),
  LEGS("legs"),
  MAIN_HAND("mainhand");
  
  String key;
  
  Slot(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public static Slot getMaterialSlot(Material material) {
    if (material.toString().contains("HELMET"))
      return HEAD; 
    if (material.toString().contains("CHESTPLATE"))
      return CHEST; 
    if (material.toString().contains("LEGGINGS"))
      return LEGS; 
    if (material.toString().contains("BOOTS"))
      return FEET; 
    return MAIN_HAND;
  }
  
  public static boolean isArmor(Material material) {
    return (getMaterialSlot(material) != MAIN_HAND);
  }
  
  public static ItemStack getItem(Player player, Slot slot) {
    switch (slot) {
      case HEAD:
        return player.getInventory().getHelmet();
      case CHEST:
        return player.getInventory().getChestplate();
      case LEGS:
        return player.getInventory().getLeggings();
      case FEET:
        return player.getInventory().getBoots();
    } 
    return player.getItemInHand();
  }
  
  public static void setItem(Player player, Slot slot, ItemStack itemStack) {
    switch (slot) {
      case HEAD:
        player.getInventory().setHelmet(itemStack);
      case CHEST:
        player.getInventory().setChestplate(itemStack);
      case LEGS:
        player.getInventory().setLeggings(itemStack);
      case FEET:
        player.getInventory().setBoots(itemStack);
        break;
    } 
  }
}
