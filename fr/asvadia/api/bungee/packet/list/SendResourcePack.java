package fr.asvadia.api.bungee.packet.list;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.protocol.AbstractPacketHandler;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.ProtocolConstants;

public class SendResourcePack extends DefinedPacket {
  Map<PackFormat, String> urls;
  
  public SendResourcePack() {}
  
  public SendResourcePack(Map<PackFormat, String> urls) {
    this.urls = urls;
  }
  
  public void handle(AbstractPacketHandler abstractPacketHandler) throws Exception {}
  
  public void write(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
    String url = this.urls.get(PackFormat.getNearPackFormat(protocolVersion, new ArrayList<>(this.urls.keySet())));
    writeString(url, buf);
    String hash = Hashing.sha1().hashString(url, Charsets.UTF_8).toString();
    writeString(hash, buf);
    if (protocolVersion >= 755) {
      buf.writeBoolean(true);
      buf.writeBoolean(false);
    } 
  }
  
  public boolean equals(Object o) {
    if (o == this)
      return true; 
    if (o instanceof SendResourcePack) {
      SendResourcePack packetPlayOutResourcePackSend = (SendResourcePack)o;
      return (this.urls == packetPlayOutResourcePackSend.getUrls());
    } 
    return false;
  }
  
  public int hashCode() {
    return this.urls.hashCode();
  }
  
  public String toString() {
    return "PacketPlayOutResourcePackSend(urls=" + this.urls.toString() + ")";
  }
  
  public Map<PackFormat, String> getUrls() {
    return this.urls;
  }
  
  public enum PackFormat {
    FORMAT_1((String)new Integer[] { Integer.valueOf(47) }),
    FORMAT_2((String)new Integer[] { Integer.valueOf(107), Integer.valueOf(108), Integer.valueOf(109), Integer.valueOf(110), Integer.valueOf(210) }),
    FORMAT_3((String)new Integer[] { Integer.valueOf(315), Integer.valueOf(316), Integer.valueOf(335), Integer.valueOf(338), Integer.valueOf(340) }),
    FORMAT_4((String)new Integer[] { Integer.valueOf(393), Integer.valueOf(401), Integer.valueOf(404), Integer.valueOf(477), Integer.valueOf(480), Integer.valueOf(485), Integer.valueOf(490), Integer.valueOf(498) }),
    FORMAT_5((String)new Integer[] { Integer.valueOf(573), Integer.valueOf(575), Integer.valueOf(578), Integer.valueOf(735), Integer.valueOf(736) }),
    FORMAT_6((String)new Integer[] { Integer.valueOf(751), Integer.valueOf(753), Integer.valueOf(754) }),
    FORMAT_7((String)new Integer[] { Integer.valueOf(755) });
    
    Integer[] versions;
    
    PackFormat(Integer... versions) {
      this.versions = versions;
    }
    
    public Integer[] getVersions() {
      return this.versions;
    }
    
    public static PackFormat getNearPackFormat(int version) {
      return getNearPackFormat(version, Arrays.asList(new PackFormat[] { FORMAT_1, FORMAT_2, FORMAT_3, FORMAT_4, FORMAT_5, FORMAT_6 }));
    }
    
    public static PackFormat getNearPackFormat(int version, List<PackFormat> packFormats) {
      PackFormat packFormat = null;
      int lastDiff = -1;
      for (PackFormat pF : packFormats) {
        Integer[] arrayOfInteger;
        int i;
        byte b;
        for (arrayOfInteger = pF.getVersions(), i = arrayOfInteger.length, b = 0; b < i; ) {
          int ver = arrayOfInteger[b].intValue();
          int diff = version - ver;
          if (diff < 0)
            diff *= -1; 
          if (diff == 0)
            return pF; 
          if (diff < lastDiff) {
            packFormat = pF;
            lastDiff = diff;
            break;
          } 
          b++;
        } 
      } 
      return packFormat;
    }
  }
}
