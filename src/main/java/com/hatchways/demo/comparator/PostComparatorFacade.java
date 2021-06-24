package com.hatchways.demo.comparator;

import com.hatchways.demo.dto.PostResponseDto;
import java.util.Comparator;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostComparatorFacade {

  private static final Comparator<Double> NULL_SAFE_DOUBLE_COMPARATOR = Comparator
      .nullsFirst(Double::compare);
  private static final Comparator<Integer> NULL_SAFE_INTEGER_COMPARATOR = Comparator
      .nullsFirst(Integer::compare);
  private static final String DEFAULT_SORT = "id";
  private static final String ASC = "asc";
  private static final String DESC = "desc";

  private static final Map<String, Comparator<PostResponseDto>> COMPARATOR_MAP = Map.of("id", new IdComparator(),
      "reads", new ReadsComparator(),
      "likes", new LikesComparator(),
      "popularity", new PopularityComparator());

  public static Comparator<PostResponseDto> resolveComparator(String sortBy, String direction) {
    if(sortBy == null || sortBy.isEmpty()) {
      sortBy = DEFAULT_SORT;
    }
    if(direction == null || direction.isEmpty()) {
      direction = ASC;
    }
    Comparator<PostResponseDto> comparator = COMPARATOR_MAP.get(sortBy);
    if(direction.equals(DESC)) {
      return comparator.reversed();
    }
    return comparator;
  }

  public static class IdComparator implements Comparator<PostResponseDto> {

    @Override
    public int compare(PostResponseDto postResponseDto, PostResponseDto t1) {
      Integer firstId = postResponseDto.getId();
      Integer secondId = t1.getId();
      return NULL_SAFE_INTEGER_COMPARATOR.compare(firstId, secondId);
    }
  }

  public static class ReadsComparator implements Comparator<PostResponseDto> {

    @Override
    public int compare(PostResponseDto postResponseDto, PostResponseDto t1) {
      Integer firstId = postResponseDto.getReads();
      Integer secondId = t1.getReads();
      return NULL_SAFE_INTEGER_COMPARATOR.compare(firstId, secondId);
    }
  }

  public static class LikesComparator implements Comparator<PostResponseDto> {

    @Override
    public int compare(PostResponseDto postResponseDto, PostResponseDto t1) {
      Integer firstId = postResponseDto.getLikes();
      Integer secondId = t1.getLikes();
      return NULL_SAFE_INTEGER_COMPARATOR.compare(firstId, secondId);
    }
  }

  public static class PopularityComparator implements Comparator<PostResponseDto> {

    @Override
    public int compare(PostResponseDto postResponseDto, PostResponseDto t1) {
      Double firstId = postResponseDto.getPopularity();
      Double secondId = t1.getPopularity();
      return NULL_SAFE_DOUBLE_COMPARATOR.compare(firstId, secondId);
    }
  }
}
