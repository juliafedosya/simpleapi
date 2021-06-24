package com.hatchways.demo.service;

import com.hatchways.demo.dto.BlogResponseDto;
import java.util.concurrent.CompletableFuture;

public interface BlogService {
  CompletableFuture<BlogResponseDto> findBlogPosts(String tag);
}
