package fr.asvadia.api.bungee.packet.listener;

import fr.asvadia.api.bungee.packet.event.PacketReceiveEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.protocol.PacketWrapper;

public class Decoder extends MessageToMessageDecoder<PacketWrapper> {
  ProxiedPlayer proxiedPlayer;
  
  public Decoder(ProxiedPlayer proxiedPlayer) {
    this.proxiedPlayer = proxiedPlayer;
  }
  
  protected void decode(ChannelHandlerContext channelHandlerContext, PacketWrapper wrapper, List<Object> out) throws Exception {
    if (wrapper.packet != null) {
      PacketReceiveEvent event = new PacketReceiveEvent(wrapper.packet, this.proxiedPlayer);
      ProxyServer.getInstance().getPluginManager().callEvent((Event)event);
      if (event.isCancelled())
        return; 
    } 
    out.add(wrapper);
  }
}
