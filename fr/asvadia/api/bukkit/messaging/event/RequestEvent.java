package fr.asvadia.api.bukkit.messaging.event;

import fr.asvadia.api.common.messaging.event.RequestEvent;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RequestEvent extends Event implements RequestEvent<Player> {
  private static final HandlerList HANDLERS = new HandlerList();
  
  ReceiveRequest<Player> request;
  
  public RequestEvent(ReceiveRequest<Player> request) {
    this.request = request;
  }
  
  public ReceiveRequest<Player> getRequest() {
    return this.request;
  }
  
  public HandlerList getHandlers() {
    return HANDLERS;
  }
  
  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
}
