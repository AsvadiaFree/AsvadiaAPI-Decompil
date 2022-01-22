package fr.asvadia.api.bungee.packet.listener;

import fr.asvadia.api.bungee.packet.event.PacketSendEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.protocol.DefinedPacket;

public class Encoder extends MessageToMessageEncoder<DefinedPacket> {
  ProxiedPlayer proxiedPlayer;
  
  public Encoder(ProxiedPlayer proxiedPlayer) {
    this.proxiedPlayer = proxiedPlayer;
  }
  
  protected void encode(ChannelHandlerContext channelHandlerContext, DefinedPacket packet, List<Object> out) throws Exception {
    PacketSendEvent event = new PacketSendEvent(packet, this.proxiedPlayer);
    ProxyServer.getInstance().getPluginManager().callEvent((Event)event);
    if (event.isCancelled())
      return; 
    out.add(packet);
  }
}
