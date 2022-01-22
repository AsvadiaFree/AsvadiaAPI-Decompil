package fr.asvadia.api.common.util;

class Author {
  private String name;
  
  private String url;
  
  private String iconUrl;
  
  private Author(String name, String url, String iconUrl) {
    this.name = name;
    this.url = url;
    this.iconUrl = iconUrl;
  }
  
  private String getName() {
    return this.name;
  }
  
  private String getUrl() {
    return this.url;
  }
  
  private String getIconUrl() {
    return this.iconUrl;
  }
}
