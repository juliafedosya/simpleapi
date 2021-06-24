package com.hatchways.demo.api;

import static com.hatchways.demo.util.ErrorConstants.DIRECTION_INVALID;
import static com.hatchways.demo.util.ErrorConstants.SORT_INVALID;
import static com.hatchways.demo.util.ErrorConstants.TAG_REQUIRED;

import com.hatchways.demo.dto.ErrorDto;
import com.hatchways.demo.dto.SuccessDto;
import com.hatchways.demo.service.BlogServiceFacade;
import com.hatchways.demo.util.CustomRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {

  private final BlogServiceFacade blogServiceFacade;

  @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessDto> pingApi() {
    return ResponseEntity.ok(new SuccessDto(true));
  }

  @GetMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> fetchPosts(@RequestParam(required = false) String tags,
      @RequestParam(required = false) String sortBy,
      @RequestParam(required = false) String direction) {

    if (CustomRequestValidator.isTagsInvalid(tags)) {
      return ResponseEntity.badRequest().body(new ErrorDto(TAG_REQUIRED));
    }

    if (CustomRequestValidator.isSortingOptionInvalid(sortBy)) {
      return ResponseEntity.badRequest().body(new ErrorDto(SORT_INVALID));
    }

    if (CustomRequestValidator.isDirectionInvalid(direction)) {
      return ResponseEntity.badRequest().body(new ErrorDto(DIRECTION_INVALID));
    }

    return ResponseEntity.ok(blogServiceFacade.fetchPosts(tags, sortBy, direction));
  }
}
