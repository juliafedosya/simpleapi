package com.hatchways.demo.service;

import com.hatchways.demo.client.BlogPostApiClient;
import com.hatchways.demo.dto.BlogResponseDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogPostApiClient blogPostApiClient;

  @Override
  @Async("asyncExecutor")
  public CompletableFuture<BlogResponseDto> findBlogPosts(String tag) {
    return CompletableFuture.completedFuture(blogPostApiClient.findBlogsData(tag));
  }

}
