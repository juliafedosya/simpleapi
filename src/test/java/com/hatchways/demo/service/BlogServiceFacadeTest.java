package com.hatchways.demo.service;

import com.hatchways.demo.comparator.PostComparatorFacade.IdComparator;
import com.hatchways.demo.comparator.PostComparatorFacade.LikesComparator;
import com.hatchways.demo.dto.BlogResponseDto;
import com.hatchways.demo.dto.PostResponseDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BlogServiceFacadeTest {

  private static final String SCIENCE_TAG = "science";
  private static final String TECH_TAG = "tech";
  private static final String POLITICS_TAG = "politics";

  @Mock
  private BlogService blogService;

  private BlogServiceFacade blogServiceFacade;

  @BeforeEach
  void setUp() {
    blogServiceFacade = new BlogServiceFacade(blogService);
  }

  @Test
  void shouldReturnSortedByLikesDistinctPosts() {
    BDDMockito.given(blogService.findBlogPosts(SCIENCE_TAG))
        .willReturn(CompletableFuture.completedFuture(prepareSciencePosts()));

    BDDMockito.given(blogService.findBlogPosts(TECH_TAG))
        .willReturn(CompletableFuture.completedFuture(prepareTechPosts()));

    BDDMockito.given(blogService.findBlogPosts(POLITICS_TAG))
        .willReturn(CompletableFuture.completedFuture(preparePoliticsPosts()));

    BlogResponseDto response = blogServiceFacade.fetchPosts(
        String.join(",", SCIENCE_TAG, POLITICS_TAG, TECH_TAG),
        "likes", "desc");

    Mockito.verify(blogService).findBlogPosts(SCIENCE_TAG);
    Mockito.verify(blogService).findBlogPosts(TECH_TAG);
    Mockito.verify(blogService).findBlogPosts(POLITICS_TAG);

    assertThat(response.getPosts())
        .hasSize(5)
        .doesNotHaveDuplicates()
        .isSortedAccordingTo(new LikesComparator().reversed());
  }

  @Test
  void shouldReturnSortedByDefaultDistinctPosts() {
    BDDMockito.given(blogService.findBlogPosts(SCIENCE_TAG))
        .willReturn(CompletableFuture.completedFuture(prepareSciencePosts()));

    BDDMockito.given(blogService.findBlogPosts(TECH_TAG))
        .willReturn(CompletableFuture.completedFuture(prepareTechPosts()));

    BDDMockito.given(blogService.findBlogPosts(POLITICS_TAG))
        .willReturn(CompletableFuture.completedFuture(preparePoliticsPosts()));

    BlogResponseDto response = blogServiceFacade.fetchPosts(
        String.join(",", SCIENCE_TAG, POLITICS_TAG, TECH_TAG),
        null, null);

    Mockito.verify(blogService).findBlogPosts(SCIENCE_TAG);
    Mockito.verify(blogService).findBlogPosts(TECH_TAG);
    Mockito.verify(blogService).findBlogPosts(POLITICS_TAG);

    assertThat(response.getPosts())
        .hasSize(5)
        .doesNotHaveDuplicates()
        .isSortedAccordingTo(new IdComparator());
  }

  private BlogResponseDto prepareTechPosts() {
    return BlogResponseDto.builder()
        .posts(List.of(
            PostResponseDto.builder()
                .id(1)
                .likes(2)
                .popularity(0.3)
                .author("Rylee Paul")
                .authorId(9)
                .tags(List.of("politics", "tech"))
                .build(),
            PostResponseDto.builder()
                .id(2)
                .likes(22)
                .popularity(0.32)
                .author("O. Smith")
                .authorId(10)
                .build()))
        .build();
  }

  private BlogResponseDto prepareSciencePosts() {
    return BlogResponseDto.builder()
        .posts(List.of(
            PostResponseDto.builder()
                .id(3)
                .likes(2)
                .popularity(0.3)
                .author("Jon Abbott")
                .authorId(8)
                .build(),
            PostResponseDto.builder()
                .id(4)
                .likes(22)
                .popularity(0.32)
                .author("Elisha Friedman")
                .authorId(7)
                .build(),
            PostResponseDto.builder()
                .id(5)
                .likes(22)
                .popularity(0.32)
                .author("Ahmad Dunn")
                .authorId(10)
                .tags(List.of("politics", "science"))
                .build()))
        .build();
  }

  private BlogResponseDto preparePoliticsPosts() {
    return BlogResponseDto.builder()
        .posts(List.of(
            PostResponseDto.builder()
                .id(1)
                .likes(2)
                .popularity(0.3)
                .author("Rylee Paul")
                .authorId(9)
                .tags(List.of("politics", "tech"))
                .build(),
            PostResponseDto.builder()
                .id(5)
                .likes(22)
                .popularity(0.32)
                .author("Ahmad Dunn")
                .authorId(10)
                .tags(List.of("politics", "science"))
                .build()))
        .build();
  }
}