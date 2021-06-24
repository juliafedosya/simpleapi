package com.hatchways.demo.comparator;

import com.hatchways.demo.comparator.PostComparatorFacade.IdComparator;
import com.hatchways.demo.comparator.PostComparatorFacade.LikesComparator;
import com.hatchways.demo.comparator.PostComparatorFacade.PopularityComparator;
import com.hatchways.demo.comparator.PostComparatorFacade.ReadsComparator;
import com.hatchways.demo.dto.PostResponseDto;
import java.util.Comparator;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PostComparatorFacadeTest {

  @ParameterizedTest
  @MethodSource("provideComparatorSourcesPositive")
  void shouldReturnMoreThanZero(PostResponseDto o1, PostResponseDto o2,
      Comparator<PostResponseDto> comparator) {
    assertThat(comparator.compare(o1, o2)).isPositive();
  }

  @ParameterizedTest
  @MethodSource("provideEqualValues")
  void shouldReturnZero(PostResponseDto o1, PostResponseDto o2,
      Comparator<PostResponseDto> comparator) {
    assertThat(comparator.compare(o1, o2)).isZero();
  }

  @ParameterizedTest
  @MethodSource("provideComparatorSourcesNegative")
  void shouldReturnLessThanZero(PostResponseDto o1, PostResponseDto o2,
      Comparator<PostResponseDto> comparator) {
    assertThat(comparator.compare(o1, o2)).isNegative();
  }

  private static Stream<Arguments> provideEqualValues() {
    PostResponseDto o1 = providePostWithGreaterValues();
    return formArgumentsStream(o1, o1);
  }

  private static Stream<Arguments> provideComparatorSourcesPositive() {

    PostResponseDto o1 = providePostWithGreaterValues();
    PostResponseDto o2 = providePostWithLowerValues();

    return formArgumentsStream(o1, o2);
  }

  private static Stream<Arguments> provideComparatorSourcesNegative() {

    PostResponseDto o1 = providePostWithLowerValues();
    PostResponseDto o2 = providePostWithGreaterValues();

    return formArgumentsStream(o1, o2);
  }

  private static Stream<Arguments> formArgumentsStream(PostResponseDto o1, PostResponseDto o2) {
    return Stream.of(
        Arguments.of(o1, o2, new IdComparator(), "Testing id comparator"),
        Arguments.of(o1, o2, new LikesComparator(), "Testing likes comparator"),
        Arguments.of(o1, o2, new PopularityComparator(), "Testing popularity comparator"),
        Arguments.of(o1, o2, new ReadsComparator(), "Testing reads comparator")
    );
  }

  private static PostResponseDto providePostWithGreaterValues() {
    return PostResponseDto.builder()
        .id(2)
        .likes(300)
        .popularity(0.9)
        .reads(2000)
        .build();
  }

  private static PostResponseDto providePostWithLowerValues() {
    return PostResponseDto.builder()
        .id(1)
        .likes(299)
        .popularity(0.5)
        .reads(1999)
        .build();
  }

}