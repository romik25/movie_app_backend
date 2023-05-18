package com.cts.mba;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieBookingServiceApplicationTests {

	@Test
	void contextLoads() {
		
		 assertEquals("One", "one");
		 fail("Test fails");
	}

}
