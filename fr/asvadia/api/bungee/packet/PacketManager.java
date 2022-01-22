package fr.asvadia.api.bungee.packet;

import fr.asvadia.api.bungee.packet.list.ResourcePackStatus;
import fr.asvadia.api.bungee.packet.list.SendResourcePack;
import fr.asvadia.api.bungee.reflection.Reflector;
import java.util.AbstractMap;
import java.util.Map;
import net.md_5.bungee.protocol.Protocol;
import net.md_5.bungee.protocol.ProtocolConstants;

public class PacketManager {
  public static void init() {
    Reflector.registerPacket(SendResourcePack.class, Protocol.GAME, ProtocolConstants.Direction.TO_CLIENT, new Map.Entry[] { entry(Integer.valueOf(735), Integer.valueOf(57)), 
          entry(Integer.valueOf(751), Integer.valueOf(56)), 
          entry(Integer.valueOf(754), Integer.valueOf(56)), 
          entry(Integer.valueOf(755), Integer.valueOf(60)), 
          entry(Integer.valueOf(756), Integer.valueOf(60)), 
          entry(Integer.valueOf(757), Integer.valueOf(60)) });
    Reflector.registerPacket(ResourcePackStatus.class, Protocol.GAME, ProtocolConstants.Direction.TO_SERVER, new Map.Entry[] { entry(Integer.valueOf(735), Integer.valueOf(32)), 
          entry(Integer.valueOf(751), Integer.valueOf(33)), 
          entry(Integer.valueOf(754), Integer.valueOf(33)), 
          entry(Integer.valueOf(755), Integer.valueOf(33)), 
          entry(Integer.valueOf(756), Integer.valueOf(33)), 
          entry(Integer.valueOf(757), Integer.valueOf(33)) });
  }
  
  public static Map.Entry<Integer, Integer> entry(Integer version, Integer id) {
    return new AbstractMap.SimpleEntry<>(version, id);
  }
}
