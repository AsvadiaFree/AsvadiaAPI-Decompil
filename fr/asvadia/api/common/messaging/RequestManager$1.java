package fr.asvadia.api.common.messaging;

import fr.asvadia.api.common.messaging.request.Request;
import java.util.TimerTask;
import java.util.function.Consumer;

class null extends TimerTask {
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
}
