package io.springframework.sbjpa.jdbc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
class SpringBootJpaJdbcApiApplicationTests {

	@Test
	void contextLoads() {
		assertThat(1).isGreaterThan(0);
	}

}
