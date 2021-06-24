package com.hatchways.demo.properties;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "async.async-executor")
@Component
public class AsyncExecutorProperties {

  private int corePoolSize;

  private int maxPoolSize;

  private int queueCapacity;

  private String threadNamePrefix;

}
