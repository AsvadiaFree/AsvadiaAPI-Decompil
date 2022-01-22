package fr.asvadia.api.common.util;

class Thumbnail {
  private String url;
  
  private Thumbnail(String url) {
    this.url = url;
  }
  
  private String getUrl() {
    return this.url;
  }
}
