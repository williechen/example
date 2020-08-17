package org.cwgy.stock.web.service;

import org.cwgy.stock.core.service.TStockService;
import org.cwgy.stock.core.service.UtilsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class TestTStockService extends TestBaseService{
	
	@Autowired
	private TStockService stockService;
	
	@Autowired
	private UtilsService utils;

	@Test
	void test() {
		Assertions.assertEquals(stockService.getCount(), 1);
	}
	
	@Test
	void test1() {
		Assertions.assertEquals(utils.show(), "Hello World!");
	}

}
