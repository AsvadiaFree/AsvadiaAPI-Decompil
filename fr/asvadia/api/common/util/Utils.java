package fr.asvadia.api.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
  public static String safeString(String value) {
    if (value != null)
      return value; 
    return "";
  }
  
  public static Number safeNumber(Number value) {
    if (value != null)
      return value; 
    return Integer.valueOf(0);
  }
  
  public static List<Integer> parseInt(Set<String> set) {
    return parseInt(new ArrayList<>(set));
  }
  
  public static List<Integer> parseInt(List<String> list) {
    return (List<Integer>)list.stream().map(Integer::parseInt).collect(Collectors.toList());
  }
  
  public static <T> List<T> reverse(Set<T> set) {
    return reverse(new ArrayList<>(set));
  }
  
  public static <T> List<T> reverse(List<T> list) {
    Collections.reverse(list);
    return list;
  }
  
  public static List<Integer> order(List<Integer> list) {
    return (List<Integer>)list.stream().sorted(Integer::compareTo).collect(Collectors.toList());
  }
}
