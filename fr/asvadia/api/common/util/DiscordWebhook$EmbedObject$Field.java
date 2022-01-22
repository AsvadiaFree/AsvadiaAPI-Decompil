package fr.asvadia.api.common.util;

class Field {
  private String name;
  
  private String value;
  
  private boolean inline;
  
  private Field(String name, String value, boolean inline) {
    this.name = name;
    this.value = value;
    this.inline = inline;
  }
  
  private String getName() {
    return this.name;
  }
  
  private String getValue() {
    return this.value;
  }
  
  private boolean isInline() {
    return this.inline;
  }
}
