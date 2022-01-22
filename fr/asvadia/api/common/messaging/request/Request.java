package fr.asvadia.api.common.messaging.request;

import java.util.UUID;

public class Request<P> {
  UUID id;
  
  P player;
  
  public Request(P player) {
    this(UUID.randomUUID(), player);
  }
  
  public Request(UUID id, P player) {
    this.id = id;
    this.player = player;
  }
  
  public UUID getId() {
    return this.id;
  }
  
  public P getPlayer() {
    return this.player;
  }
}
