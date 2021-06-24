package com.hatchways.demo.util;


import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomRequestValidator {

  private static final Set<String> SORT_OPTIONS = Set.of("id", "reads", "likes", "popularity");
  private static final Set<String> DIRECTIONS = Set.of("asc", "desc");

  public static boolean isTagsInvalid(String tags) {
    return tags == null || tags.isEmpty();
  }

  public static boolean isSortingOptionInvalid(String sortBy) {
    return sortBy != null && !SORT_OPTIONS.contains(sortBy);
  }

  public static boolean isDirectionInvalid(String direction) {
    return direction != null && !DIRECTIONS.contains(direction);
  }
}
