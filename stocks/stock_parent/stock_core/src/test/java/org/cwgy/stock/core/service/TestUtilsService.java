package org.cwgy.stock.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestUtilsService extends TestBaseService {
	
	@Autowired
	private UtilsService utils;

	@Test
	void test() {
		Assertions.assertEquals(utils.show(), "Hello World!");
	}

}
