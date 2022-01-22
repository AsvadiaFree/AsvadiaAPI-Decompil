package fr.asvadia.api.bungee.reflection;

import com.google.common.collect.Multimap;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.protocol.Protocol;
import net.md_5.bungee.protocol.ProtocolConstants;

public class Reflector {
  public static Multimap<Plugin, Command> getCommands() {
    try {
      ProxyServer proxyServer = ProxyServer.getInstance();
      Class<?> pluginManager = proxyServer.getPluginManager().getClass();
      Field field = pluginManager.getDeclaredField("commandsByPlugin");
      field.setAccessible(true);
      return (Multimap<Plugin, Command>)field.get(proxyServer.getPluginManager());
    } catch (NoSuchFieldException|IllegalAccessException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static <T extends net.md_5.bungee.protocol.DefinedPacket> Class<T> registerPacket(Class<T> definedPacket, Protocol protocol, ProtocolConstants.Direction dir, Map.Entry<Integer, Integer>... versions) {
    if (versions.length > 0)
      try {
        Field directionField = Protocol.class.getDeclaredField(dir.toString());
        directionField.setAccessible(true);
        Object direction = directionField.get(protocol);
        Method map = Protocol.class.getDeclaredMethod("map", new Class[] { int.class, int.class });
        map.setAccessible(true);
        Class<?> protocolMapping = map.getReturnType();
        Class<?> protocolMappingArray = Array.newInstance(protocolMapping, 1).getClass();
        Method registerPacket = direction.getClass().getDeclaredMethod("registerPacket", new Class[] { Class.class, protocolMappingArray });
        registerPacket.setAccessible(true);
        Object[] array = (Object[])Array.newInstance(protocolMapping, versions.length);
        int i = 0;
        for (Map.Entry<Integer, Integer> values : versions) {
          array[i] = map.invoke(null, new Object[] { values.getKey(), values.getValue() });
          i++;
        } 
        registerPacket.invoke(direction, new Object[] { definedPacket, array });
        return definedPacket;
      } catch (IllegalAccessException|NoSuchFieldException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
        e.printStackTrace();
      }  
    return null;
  }
}
