package com.hatchways.demo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

  private String author;

  private Integer authorId;

  private Integer id;

  private Integer likes;

  private Double popularity;

  private Integer reads;

  private List<String> tags;

}
