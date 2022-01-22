package fr.asvadia.api.bungee.packet.list;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

public class ResourcePackStatus extends DefinedPacket {
  Status status;
  
  public ResourcePackStatus() {}
  
  public ResourcePackStatus(Status status) {
    this.status = status;
  }
  
  public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
    writeVarInt(this.status.getId(), buf);
  }
  
  public void read(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
    if (protocolVersion <= 110)
      readString(buf); 
    this.status = Status.getById(readVarInt(buf));
  }
  
  public void handle(AbstractPacketHandler abstractPacketHandler) throws Exception {}
  
  public boolean equals(Object o) {
    return false;
  }
  
  public int hashCode() {
    return 0;
  }
  
  public String toString() {
    return null;
  }
  
  public enum Status {
    SUCCESSFULLY_LOADED(0),
    DECLINED(1),
    FAILED_DOWNLOAD(2),
    ACCEPTED(3);
    
    int id;
    
    Status(int id) {
      this.id = id;
    }
    
    public int getId() {
      return this.id;
    }
    
    public static Status getById(int id) {
      for (Status status : values()) {
        if (status.getId() == id)
          return status; 
      } 
      return null;
    }
  }
  
  public Status getStatus() {
    return this.status;
  }
}
