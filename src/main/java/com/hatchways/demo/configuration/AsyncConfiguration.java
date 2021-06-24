package com.hatchways.demo.configuration;

import com.hatchways.demo.properties.AsyncExecutorProperties;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RequiredArgsConstructor
@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncExecutorProperties.class)
public class AsyncConfiguration {

  private final AsyncExecutorProperties asyncExecutorProperties;

  /**
   * A bean required for executing @Async-annotated methods. asynchronously
   */
  @Bean(name = "asyncExecutor")
  public Executor asyncExecutor() {
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(asyncExecutorProperties.getCorePoolSize());
    executor.setMaxPoolSize(asyncExecutorProperties.getMaxPoolSize());
    executor.setQueueCapacity(asyncExecutorProperties.getQueueCapacity());
    executor.setThreadNamePrefix(asyncExecutorProperties.getThreadNamePrefix());
    executor.initialize();
    return executor;
  }
}
