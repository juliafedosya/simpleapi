package com.hatchways.demo.client;

import com.hatchways.demo.dto.BlogResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "blogPostApiClient",
    url = "${assessment.api.url}"
)
public interface BlogPostApiClient {

  @GetMapping(path = "${assessment.api.path}")
  BlogResponseDto findBlogsData(@RequestParam String tag);
}
