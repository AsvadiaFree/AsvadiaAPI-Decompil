package fr.asvadia.api.common.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EmbedObject {
  private String title;
  
  private String description;
  
  private String url;
  
  private Color color;
  
  private Footer footer;
  
  private Thumbnail thumbnail;
  
  private Image image;
  
  private Author author;
  
  private List<Field> fields = new ArrayList<>();
  
  public String getTitle() {
    return this.title;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public Footer getFooter() {
    return this.footer;
  }
  
  public Thumbnail getThumbnail() {
    return this.thumbnail;
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public Author getAuthor() {
    return this.author;
  }
  
  public List<Field> getFields() {
    return this.fields;
  }
  
  public EmbedObject setTitle(String title) {
    this.title = title;
    return this;
  }
  
  public EmbedObject setDescription(String description) {
    this.description = description;
    return this;
  }
  
  public EmbedObject setUrl(String url) {
    this.url = url;
    return this;
  }
  
  public EmbedObject setColor(Color color) {
    this.color = color;
    return this;
  }
  
  public EmbedObject setFooter(String text, String icon) {
    this.footer = new Footer(text, icon);
    return this;
  }
  
  public EmbedObject setThumbnail(String url) {
    this.thumbnail = new Thumbnail(url);
    return this;
  }
  
  public EmbedObject setImage(String url) {
    this.image = new Image(url);
    return this;
  }
  
  public EmbedObject setAuthor(String name, String url, String icon) {
    this.author = new Author(name, url, icon);
    return this;
  }
  
  public EmbedObject addField(String name, String value, boolean inline) {
    this.fields.add(new Field(name, value, inline));
    return this;
  }
  
  private class Footer {
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
  
  private class Thumbnail {
    private String url;
    
    private Thumbnail(String url) {
      this.url = url;
    }
    
    private String getUrl() {
      return this.url;
    }
  }
  
  private class Image {
    private String url;
    
    private Image(String url) {
      this.url = url;
    }
    
    private String getUrl() {
      return this.url;
    }
  }
  
  private class Author {
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
  
  private class Field {
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
}
