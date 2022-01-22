package fr.asvadia.api.bukkit.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Reflector {
  public static Class<?> getClass(String path) {
    try {
      return Class.forName(path);
    } catch (ClassNotFoundException e) {
      return null;
    } 
  }
  
  public static PlayerConnection getPlayerConnection(Player player) {
    return (((CraftPlayer)player).getHandle()).b;
  }
  
  public static void sendPackets(Player player, List<Packet<?>> packets) {
    PlayerConnection playerConnection = getPlayerConnection(player);
    packets.forEach(packet -> sendPacket(playerConnection, packet));
  }
  
  public static void sendPacket(Player player, Packet<?> packet) {
    sendPacket(getPlayerConnection(player), packet);
  }
  
  private static void sendPacket(PlayerConnection playerConnection, Packet<?> packet) {
    try {
      playerConnection.sendPacket(packet);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void setAttributes(ItemStack item, Map<Attribute, Double> attributes) {
    ItemMeta itemMeta = item.getItemMeta();
    if (itemMeta.hasAttributeModifiers())
      for (Attribute key : itemMeta.getAttributeModifiers().keySet())
        itemMeta.removeAttributeModifier(key);  
    if (attributes != null)
      attributes.forEach((attribute, aDouble) -> itemMeta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKey().getNamespace(), aDouble.doubleValue(), AttributeModifier.Operation.ADD_NUMBER))); 
    item.setItemMeta(itemMeta);
  }
  
  public static Map<Attribute, Double> getAttributes(ItemStack item) {
    HashMap<Attribute, Double> map = new HashMap<>();
    item.getItemMeta().getAttributeModifiers().forEach((attribute, attributeModifier) -> map.put(attribute, Double.valueOf(attributeModifier.getAmount())));
    return map;
  }
  
  public static ItemStack addNbtItem(ItemStack item, Map<String, String> values) {
    try {
      NBTTagCompound compound;
      ItemStack itemStack = getItemStack(item);
      NBTTagCompound nbt = getNbt(itemStack);
      if (nbt.hasKey("asvadia")) {
        compound = nbt.getCompound("asvadia");
      } else {
        compound = new NBTTagCompound();
        nbt.set("asvadia", (NBTBase)compound);
      } 
      for (String key : values.keySet())
        compound.setString(key, values.get(key)); 
      itemStack.setTag(nbt);
      item.setItemMeta(CraftItemStack.asBukkitCopy(itemStack).getItemMeta());
    } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException|InstantiationException e) {
      e.printStackTrace();
    } 
    return item;
  }
  
  public static void removeNbtItem(ItemStack item, String key) {
    try {
      ItemStack itemStack = getItemStack(item);
      NBTTagCompound nbt = getNbt(itemStack);
      if (nbt.hasKey("asvadia")) {
        nbt.getCompound("asvadia").remove(key);
        itemStack.setTag(nbt);
        item.setItemMeta(CraftItemStack.asBukkitCopy(itemStack).getItemMeta());
      } 
    } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException|InstantiationException e) {
      e.printStackTrace();
    } 
  }
  
  public static String getNbtItem(ItemStack item, String key) {
    try {
      String element = getNbt(getItemStack(item)).getCompound("asvadia").getString(key);
      if (!element.equals(""))
        return element; 
    } catch (IllegalAccessException|NoSuchMethodException|InvocationTargetException|InstantiationException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  static ItemStack getItemStack(ItemStack itemStack) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    return CraftItemStack.asNMSCopy(itemStack);
  }
  
  static NBTTagCompound getNbt(ItemStack itemStack) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
    return itemStack.hasTag() ? itemStack.getTag() : new NBTTagCompound();
  }
  
  public static CommandMap getCommandMap() {
    try {
      Server server = Bukkit.getServer();
      Field field = server.getClass().getDeclaredField("commandMap");
      field.setAccessible(true);
      return (CommandMap)field.get(Bukkit.getServer());
    } catch (IllegalAccessException|NoSuchFieldException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static PluginCommand registerPluginCommand(Plugin plugin, String name) {
    try {
      Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(new Class[] { String.class, Plugin.class });
      constructor.setAccessible(true);
      PluginCommand pluginCommand = constructor.newInstance(new Object[] { name, plugin });
      getCommandMap().register(plugin.getName(), (Command)pluginCommand);
      return pluginCommand;
    } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
      return null;
    } 
  }
  
  public static void unRegisterPluginCommand(PluginCommand pluginCommand) {
    try {
      CommandMap commandMap = getCommandMap();
      Field field = commandMap.getClass().getDeclaredField("knownCommands");
      field.setAccessible(true);
      ((HashMap)field.get(commandMap)).remove(pluginCommand.getLabel(), pluginCommand);
    } catch (NoSuchFieldException|IllegalAccessException e) {
      e.printStackTrace();
    } 
  }
}
