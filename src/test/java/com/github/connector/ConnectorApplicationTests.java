package com.github.connector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.blockhound.BlockHound;

@SpringBootTest
class ConnectorApplicationTests {

	static {
		BlockHound.install();
	}

	@Test
	void contextLoads() {
	}

}
