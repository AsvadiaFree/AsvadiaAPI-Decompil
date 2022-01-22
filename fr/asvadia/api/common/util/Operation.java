package fr.asvadia.api.common.util;

public enum Operation {
  ADD(1, "add"),
  SET(0, "set"),
  REMOVE(-1, "remove");
  
  int coef;
  
  String key;
  
  Operation(int coef, String key) {
    this.coef = coef;
    this.key = key;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public int getCoef() {
    return this.coef;
  }
}
