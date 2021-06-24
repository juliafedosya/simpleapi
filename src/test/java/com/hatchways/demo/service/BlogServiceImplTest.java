package com.hatchways.demo.service;


import com.hatchways.demo.client.HatchwaysApiClient;
import com.hatchways.demo.dto.BlogResponseDto;
import com.hatchways.demo.dto.PostResponseDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BlogServiceImplTest {

  private static final String TAG = "science";

  @Mock
  private HatchwaysApiClient hatchwaysApiClient;

  private BlogService blogService;

  @BeforeEach
  void setUp() {
    blogService = new BlogServiceImpl(hatchwaysApiClient);
  }

  @Test
  void shouldReturnCompletedFutureWithValues() {
    BDDMockito.given(hatchwaysApiClient.findBlogsData(TAG))
        .willReturn(prepareMockData());

    CompletableFuture<BlogResponseDto> response = blogService.findBlogPosts(TAG);

    assertThat(response).isCompletedWithValue(prepareMockData());
  }

  private BlogResponseDto prepareMockData() {
    return BlogResponseDto.builder()
        .posts(List.of(
            PostResponseDto.builder()
                .id(1)
                .likes(2)
                .popularity(0.3)
                .author("Rylee Paul")
                .authorId(9)
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


}