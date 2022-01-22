package fr.asvadia.api.common.util;

import java.util.UUID;
import org.geysermc.floodgate.api.FloodgateApi;

public enum PlatForm {
  ALL, JAVA, BEDROCK;
  
  public static PlatForm getByUUID(UUID uuid) {
    try {
      if (FloodgateApi.getInstance().isFloodgateId(uuid))
        return BEDROCK; 
      return JAVA;
    } catch (NoClassDefFoundError e) {
      return ALL;
    } 
  }
}
