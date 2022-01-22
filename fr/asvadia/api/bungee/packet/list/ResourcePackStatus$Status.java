package fr.asvadia.api.bungee.packet.list;

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
