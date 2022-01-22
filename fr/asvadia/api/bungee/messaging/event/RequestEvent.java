package fr.asvadia.api.bungee.messaging.event;

import fr.asvadia.api.common.messaging.event.RequestEvent;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class RequestEvent extends Event implements RequestEvent<ProxiedPlayer> {
  ReceiveRequest<ProxiedPlayer> request;
  
  public RequestEvent(ReceiveRequest<ProxiedPlayer> request) {
    this.request = request;
  }
  
  public ReceiveRequest<ProxiedPlayer> getRequest() {
    return this.request;
  }
}
