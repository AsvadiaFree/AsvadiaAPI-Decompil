package fr.asvadia.api.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginFinder {
  public static Plugin here() {
    return find(0);
  }
  
  public static Plugin last() {
    return find(1);
  }
  
  public static Plugin find(int value) {
    Class<?> clazz;
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (stackTraceElements.length < 4 + value)
      return null; 
    try {
      clazz = Class.forName(stackTraceElements[3 + value].getClassName());
    } catch (ClassNotFoundException e) {
      return null;
    } 
    int hashSourceCode = clazz.getProtectionDomain().getCodeSource().hashCode();
    for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
      if (plugin.getClass().getProtectionDomain().getCodeSource().hashCode() == hashSourceCode)
        return plugin; 
    } 
    return null;
  }
}
