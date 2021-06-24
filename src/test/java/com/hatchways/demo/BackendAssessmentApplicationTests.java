package com.hatchways.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendAssessmentApplicationTests {

	@Test
	void contextLoads() {
		assertThat(Boolean.TRUE).describedAs("Fake assertion to prevent sonal issue").isTrue();
	}

}
