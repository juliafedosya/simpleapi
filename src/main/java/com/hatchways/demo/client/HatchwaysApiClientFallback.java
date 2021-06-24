package com.hatchways.demo.client;

import com.hatchways.demo.dto.BlogResponseDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HatchwaysApiClientFallback implements HatchwaysApiClient {

  @Override
  public BlogResponseDto findBlogsData(String tag) {
    return BlogResponseDto.builder()
        .posts(List.of())
        .build();
  }
}
