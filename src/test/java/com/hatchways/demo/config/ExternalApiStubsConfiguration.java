package com.hatchways.demo.config;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class ExternalApiStubsConfiguration {

  private static final String MAPPING_SOURCE = "src/test/resources/stubs";

  private static WireMockServer server;


  @PostConstruct
  public void initSecurityServer() {
    server = new WireMockServer(options()
        .port(8089)
        .notifier(new ConsoleNotifier(false))
        .mappingSource(new JsonFileMappingsSource(new SingleRootFileSource(MAPPING_SOURCE))));

    server.start();

    WireMock.configureFor("localhost", 8089);
  }

  @PreDestroy
  public void stopSecurityServer() {
    server.stop();
  }

}
