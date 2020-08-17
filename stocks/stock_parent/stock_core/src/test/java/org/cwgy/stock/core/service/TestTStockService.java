package org.cwgy.stock.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestTStockService extends TestBaseService{
	
	@Autowired
	private TStockService stockService;

	@Test
	void test() {
		Assertions.assertEquals(stockService.getCount(), 1);
	}

}
