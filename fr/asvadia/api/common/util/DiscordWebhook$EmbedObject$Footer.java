package fr.asvadia.api.common.util;

class Footer {
  private String text;
  
  private String iconUrl;
  
  private Footer(String text, String iconUrl) {
    this.text = text;
    this.iconUrl = iconUrl;
  }
  
  private String getText() {
    return this.text;
  }
  
  private String getIconUrl() {
    return this.iconUrl;
  }
}
