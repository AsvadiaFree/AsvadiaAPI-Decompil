package fr.asvadia.api.common.messaging.listener;

import com.google.common.io.ByteStreams;
import fr.asvadia.api.common.messaging.RequestManager;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class MessageListener<P> {
  protected void event(P player, byte[] bytes) {
    RequestManager<P> requestManager = RequestManager.getInstance();
    Map.Entry<UUID, List<String>> result = requestManager.translate(ByteStreams.newDataInput(bytes));
    if (result != null) {
      ReceiveRequest<P> receiveRequest = new ReceiveRequest(result.getKey(), player, result.getValue());
      requestManager.register(receiveRequest);
      call(receiveRequest);
    } 
  }
  
  public abstract void call(ReceiveRequest<P> paramReceiveRequest);
}
