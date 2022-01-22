package fr.asvadia.api.bukkit.util;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {
  public static final EnumSet<Material> container = EnumSet.of(Material.CHEST, new Material[] { Material.DROPPER, Material.HOPPER, Material.DISPENSER, Material.TRAPPED_CHEST, Material.BREWING_STAND, Material.FURNACE });
  
  public static boolean safeRemoveItem(ItemStack itemStack) {
    if (itemStack.getAmount() > 1) {
      itemStack.setAmount(itemStack.getAmount() - 1);
      return false;
    } 
    return true;
  }
  
  public static List<String> tabComplete(String[] args, Collection<?> complete) {
    String var2 = args[args.length - 1];
    ArrayList<String> var3 = Lists.newArrayList();
    if (!complete.isEmpty()) {
      Iterator<String> var4 = Iterables.transform(complete, Functions.toStringFunction()).iterator();
      while (var4.hasNext()) {
        String var5 = var4.next();
        if (tabComplete(var2, var5))
          var3.add(var5); 
      } 
      if (var3.isEmpty()) {
        var4 = (Iterator)complete.iterator();
        while (var4.hasNext()) {
          Object var6 = var4.next();
          if (tabComplete(var2, (String)var6))
            var3.add(String.valueOf(var6)); 
        } 
      } 
    } 
    return var3;
  }
  
  public static boolean tabComplete(String arg, String complete) {
    return complete.regionMatches(true, 0, arg, 0, arg.length());
  }
  
  public static Location randomLocationAround(Location location, int radius) {
    int x = (new Random()).nextInt(radius);
    if ((new Random()).nextBoolean())
      x *= -1; 
    int z = (new Random()).nextInt(radius);
    if ((new Random()).nextBoolean())
      z *= -1; 
    location.add(x, 0.0D, z);
    return location;
  }
  
  public static String toKeyJSON(Location location) {
    return location.getWorld().getName() + ";" + location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY();
  }
  
  public static Location parseLocationJSON(String location) {
    String[] data = location.split(";");
    try {
      return new Location(Bukkit.getWorld(data[0]), 
          Double.parseDouble(data[1]), 
          Double.parseDouble(data[2]), 
          Double.parseDouble(data[3]));
    } catch (Exception e) {
      return null;
    } 
  }
}
