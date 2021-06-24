package com.hatchways.demo.api;

import static com.hatchways.demo.util.ErrorConstants.DIRECTION_INVALID;
import static com.hatchways.demo.util.ErrorConstants.SORT_INVALID;
import static com.hatchways.demo.util.ErrorConstants.TAG_REQUIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatchways.demo.comparator.PostComparatorFacade.IdComparator;
import com.hatchways.demo.comparator.PostComparatorFacade.LikesComparator;
import com.hatchways.demo.config.ExternalApiStubsConfiguration;
import com.hatchways.demo.dto.BlogResponseDto;
import com.hatchways.demo.dto.ErrorDto;
import com.hatchways.demo.dto.PostResponseDto;
import com.hatchways.demo.dto.SuccessDto;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ExternalApiStubsConfiguration.class})
@AutoConfigureMockMvc
class DemoControllerTest {
  private static final String PING_PATH = "/api/ping";
  private static final String POSTS_PATH = "/api/posts";

  private static final Comparator<PostResponseDto> ID_COMPARATOR = new IdComparator();
  private static final Comparator<PostResponseDto> LIKES_COMPARATOR = new LikesComparator();

  private static final String NON_FALLBACK_TAG = "science,politics";

  private static final String FALLBACK_TAG = "healthcare";



  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldReturnSuccessOk() throws Exception {
    MvcResult mvcResult = executePingRequest();
    assertEqualResponse(mvcResult, new SuccessDto(true));
  }

  @Test
  void shouldReturnDistinctPosts() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequest(NON_FALLBACK_TAG, "likes", "desc",
        HttpStatus.OK);
    BlogResponseDto response = getActualResponseForBlogResponseDto(mvcResult);
    assertThat(response.getPosts())
        .isNotEmpty()
        .doesNotHaveDuplicates()
        .isSortedAccordingTo(LIKES_COMPARATOR.reversed());
  }

  @Test
  void shouldReturnSortedByIdAsc() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequestNoSort(NON_FALLBACK_TAG, HttpStatus.OK);
    BlogResponseDto response = getActualResponseForBlogResponseDto(mvcResult);
    assertThat(response.getPosts())
    .isNotEmpty()
    .doesNotHaveDuplicates()
    .isSortedAccordingTo(ID_COMPARATOR);
  }

  @Test
  void shouldReturnOkWhenFallBackHappens() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequest(FALLBACK_TAG, "likes", "desc",
        HttpStatus.OK);
    BlogResponseDto response = getActualResponseForBlogResponseDto(mvcResult);
    assertThat(response.getPosts()).isEmpty();
  }

  @Test
  void shouldReturnErrorTagsValidation() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequest("", "likes", "desc", HttpStatus.BAD_REQUEST);
    assertEqualResponse(mvcResult, new ErrorDto(TAG_REQUIRED));
  }

  @Test
  void shouldReturnErrorSortByValidation() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequest(NON_FALLBACK_TAG, "liwkes", "desc",
        HttpStatus.BAD_REQUEST);
    assertEqualResponse(mvcResult, new ErrorDto(SORT_INVALID));
  }

  @Test
  void shouldReturnErrorDirectionValidation() throws Exception {
    MvcResult mvcResult = executeFetchingPostsRequest(NON_FALLBACK_TAG, "likes", "descs",
        HttpStatus.BAD_REQUEST);
    assertEqualResponse(mvcResult, new ErrorDto(DIRECTION_INVALID));
  }

  private MvcResult executePingRequest() throws Exception {
    return mockMvc.perform(get(PING_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  private MvcResult executeFetchingPostsRequest(String tags, String sortBy, String direction,
      HttpStatus httpStatus) throws Exception {
    return mockMvc.perform(get(POSTS_PATH)
        .param("tags", tags)
        .param("sortBy", sortBy)
        .param("direction", direction))
        .andExpect(status().is(httpStatus.value()))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  private MvcResult executeFetchingPostsRequestNoSort(String tags, HttpStatus httpStatus)
      throws Exception {
    return mockMvc.perform(get(POSTS_PATH)
        .param("tags", tags))
        .andExpect(status().is(httpStatus.value()))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  private  <T> void assertEqualResponse(MvcResult mvcResult, T expected) throws Exception {
    JavaType javaType = objectMapper.getTypeFactory().constructType(expected.getClass());
    T actual = getActualResponse(mvcResult, javaType);
    assertThat(actual).isEqualTo(expected);
  }

  private <T> T getActualResponse(MvcResult mvcResult, JavaType javaType)
      throws Exception {
    return objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), javaType);
  }

  private <T> T getActualResponseForBlogResponseDto(MvcResult mvcResult) throws Exception {
    JavaType javaType = objectMapper.getTypeFactory().constructType(BlogResponseDto.class);
    return objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), javaType);
  }
}