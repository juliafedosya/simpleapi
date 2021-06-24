package com.hatchways.demo.service;

import com.hatchways.demo.comparator.PostComparatorFacade;
import com.hatchways.demo.dto.BlogResponseDto;
import com.hatchways.demo.dto.PostResponseDto;
import com.hatchways.demo.util.FutureUtils;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceFacade {

  private static final String TAG_SEPARATOR = ",";

  private final BlogService blogService;

  public BlogResponseDto fetchPosts(String tags, String sortBy, String direction) {
    List<String> parsedTags = Arrays.asList(tags.split(TAG_SEPARATOR));
    List<CompletableFuture<BlogResponseDto>> futureList = parsedTags.stream()
        .map(blogService :: findBlogPosts)
        .collect(Collectors.toList());

    CompletableFuture<Void> allFutures = CompletableFuture
        .allOf(futureList.toArray(new CompletableFuture[0]));
    FutureUtils.getSafety(allFutures, "Unable to retrieve data from hatchways API.");

    List<PostResponseDto> posts = futureList.stream().
        map(CompletableFuture::join)
        .map(BlogResponseDto::getPosts)
        .flatMap(List:: stream)
        .sorted(PostComparatorFacade.resolveComparator(sortBy, direction))
        .distinct()
        .collect(Collectors.toList());
    return BlogResponseDto.builder().posts(posts).build();
  }

}
