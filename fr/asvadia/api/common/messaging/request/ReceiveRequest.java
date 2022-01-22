package fr.asvadia.api.common.messaging.request;

import java.util.List;
import java.util.UUID;

public class ReceiveRequest<P> extends Request<P> {
  List<String> content;
  
  public ReceiveRequest(UUID id, P player, List<String> content) {
    super(id, player);
    this.content = content;
  }
  
  public List<String> getContent() {
    return this.content;
  }
}
