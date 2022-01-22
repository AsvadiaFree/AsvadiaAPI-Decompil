package fr.asvadia.api.common.messaging;

import com.google.common.io.ByteArrayDataInput;
import fr.asvadia.api.common.messaging.request.ReceiveRequest;
import fr.asvadia.api.common.messaging.request.Request;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class RequestManager<P> {
  static RequestManager INSTANCE;
  
  HashMap<UUID, ReceiveRequest<P>> receivingRequests;
  
  public static <P> RequestManager<P> getInstance() {
    return INSTANCE;
  }
  
  public RequestManager() {
    INSTANCE = this;
    this.receivingRequests = new HashMap<>();
  }
  
  public void register(ReceiveRequest<P> request) {
    this.receivingRequests.put(request.getId(), request);
  }
  
  public ReceiveRequest<P> getReceivingRequest(UUID id) {
    return this.receivingRequests.get(id);
  }
  
  public HashMap<UUID, ReceiveRequest<P>> getReceivingRequests() {
    return this.receivingRequests;
  }
  
  public abstract String getKey();
  
  public Map.Entry<UUID, List<String>> translate(ByteArrayDataInput in) {
    String code = in.readUTF();
    if (code.equals(getKey())) {
      UUID id = UUID.fromString(in.readUTF());
      List<String> content = new ArrayList<>();
      try {
        while (true)
          content.add(in.readUTF()); 
      } catch (Exception e) {
        return new AbstractMap.SimpleEntry<>(id, content);
      } 
    } 
    return null;
  }
  
  public void request(P player, String... contents) {
    request(new Request(player), contents);
  }
  
  public void request(Request<P> request, String... contents) {
    request((Consumer<ReceiveRequest<P>>)null, request, contents);
  }
  
  public void request(Consumer<ReceiveRequest<P>> consumer, P player, String... contents) {
    request(consumer, new Request(player), contents);
  }
  
  public void request(final Consumer<ReceiveRequest<P>> consumer, final Request<P> request, String... contents) {
    if (contents.length > 0) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream = new DataOutputStream(out);
      try {
        dataOutputStream.writeUTF(getKey());
        dataOutputStream.writeUTF(request.getId().toString());
        for (String content : contents)
          dataOutputStream.writeUTF(content); 
      } catch (IOException e) {
        e.printStackTrace();
      } 
      sendData((P)request.getPlayer(), out);
      if (consumer != null) {
        this.receivingRequests.remove(request.getId());
        (new Timer()).schedule(new TimerTask() {
              int time;
              
              public void run() {
                this.time += 5;
                if (RequestManager.this.getReceivingRequests().get(request.getId()) != null) {
                  consumer.accept(RequestManager.this.getReceivingRequest(request.getId()));
                  cancel();
                } else if (this.time >= 4000) {
                  consumer.accept(null);
                  cancel();
                } 
              }
            },  0L, 20L);
      } 
    } else {
      throw new IllegalArgumentException();
    } 
  }
  
  public abstract void sendData(P paramP, ByteArrayOutputStream paramByteArrayOutputStream);
}
