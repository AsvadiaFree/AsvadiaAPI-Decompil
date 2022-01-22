package fr.asvadia.api.bungee.packet.event;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.protocol.DefinedPacket;

public class PacketSendEvent extends Event implements Cancellable {
  ProxiedPlayer player;
  
  DefinedPacket packet;
  
  boolean cancel;
  
  public PacketSendEvent(DefinedPacket packet, ProxiedPlayer proxiedPlayer) {
    this.packet = packet;
    this.player = proxiedPlayer;
  }
  
  public boolean isCancelled() {
    return false;
  }
  
  public DefinedPacket getPacket() {
    return this.packet;
  }
  
  public ProxiedPlayer getPlayer() {
    return this.player;
  }
  
  public void setCancelled(boolean b) {
    this.cancel = b;
  }
}
