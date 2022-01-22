package fr.asvadia.api.common.util;

import java.util.List;

public class PlaceHolder {
  static Character tag = Character.valueOf('%');
  
  String key;
  
  String value;
  
  public PlaceHolder(String key) {
    this(key, null);
  }
  
  public PlaceHolder(String key, String value) {
    this.key = key;
    if (value == null) {
      this.value = "?";
    } else {
      this.value = value;
    } 
  }
  
  public String getKey() {
    return this.key;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public static String replace(String element, List<PlaceHolder> placeHolders) {
    return replace(element, placeHolders.<PlaceHolder>toArray(new PlaceHolder[0]));
  }
  
  public static String replace(String element, PlaceHolder... placeHolders) {
    for (PlaceHolder placeHolder : placeHolders) {
      if (placeHolder.getValue() != null)
        element = element.replaceAll(getStringTag() + getStringTag() + placeHolder.getKey(), placeHolder.getValue()); 
    } 
    return element;
  }
  
  public static List<String> replaceList(List<String> element, List<PlaceHolder> placeHolders) {
    return replaceList(element, placeHolders.<PlaceHolder>toArray(new PlaceHolder[0]));
  }
  
  public static List<String> replaceList(List<String> element, PlaceHolder... placeHolders) {
    for (int i = 0; i < element.size(); i++)
      element.set(i, replace(element.get(i), placeHolders)); 
    return element;
  }
  
  public static void setTag(Character tag) {
    PlaceHolder.tag = tag;
  }
  
  public static Character getTag() {
    return tag;
  }
  
  private static String getStringTag() {
    return Character.toString(tag.charValue());
  }
  
  public PlaceHolder clone() {
    return new PlaceHolder(this.key, this.value);
  }
}
