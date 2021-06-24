package com.hatchways.demo.client;

import com.hatchways.demo.dto.BlogResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hatchwaysApiClient",
    url = "${hatchways.api.url}"
)
public interface HatchwaysApiClient {

  @GetMapping(path = "${hatchways.api.path}")
  BlogResponseDto findBlogsData(@RequestParam String tag);
}
