package fr.asvadia.api.bungee.packet.listener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.netty.ChannelWrapper;

public class ConnectionListener implements Listener {
  @EventHandler
  public void onServerConnected(ServerConnectedEvent event) {
    ProxiedPlayer p = event.getPlayer();
    ServerConnection server = (ServerConnection)event.getServer();
    if (p != null && 
      server != null) {
      ChannelWrapper wrapper = server.getCh();
      if (wrapper != null)
        try {
          wrapper.getHandle().pipeline().addAfter("packet-decoder", "custom-decoder", (ChannelHandler)new Decoder(p));
          wrapper.getHandle().pipeline().addAfter("packet-encoder", "custom-encoder", (ChannelHandler)new Encoder(p));
        } catch (Exception e) {
          System.out.println("[BungeePackets] Failed to inject server connection for " + event.getPlayer().getName());
        }  
    } 
  }
  
  @EventHandler
  public void onPostLogin(PostLoginEvent event) {
    try {
      ProxiedPlayer p = event.getPlayer();
      Field chF = p.getClass().getDeclaredField("ch");
      chF.setAccessible(true);
      Object ch = chF.get(p);
      Method method = ch.getClass().getDeclaredMethod("getHandle", new Class[0]);
      Channel channel = (Channel)method.invoke(ch, new Object[0]);
      channel.pipeline().addAfter("packet-decoder", "custom-decoder", (ChannelHandler)new Decoder(p));
      channel.pipeline().addAfter("packet-encoder", "custom-encoder", (ChannelHandler)new Encoder(p));
    } catch (Exception e) {
      System.out.println("[BungeePackets] Failed to inject client connection for " + event.getPlayer().getName());
    } 
  }
}
