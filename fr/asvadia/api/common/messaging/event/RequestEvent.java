package fr.asvadia.api.common.messaging.event;

import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import java.util.List;

public interface RequestEvent<P> {
  ReceiveRequest<P> getRequest();
  
  default List<String> getContent() {
    return getRequest().getContent();
  }
  
  default P getPlayer() {
    return (P)getRequest().getPlayer();
  }
}
