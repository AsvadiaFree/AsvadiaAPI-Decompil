package fr.asvadia.api.common.util;

class Image {
  private String url;
  
  private Image(String url) {
    this.url = url;
  }
  
  private String getUrl() {
    return this.url;
  }
}
