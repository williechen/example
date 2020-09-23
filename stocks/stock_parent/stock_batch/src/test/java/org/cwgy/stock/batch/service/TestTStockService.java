package org.cwgy.stock.batch.service;

import org.cwgy.stock.core.dao.TStockMapper;
import org.cwgy.stock.core.service.TStockService;
import org.cwgy.stock.core.service.UtilsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class TestTStockService extends TestBaseService {
	
	@MockBean
	private TStockMapper stockMapper;
	
	@Autowired
	private TStockService stockService;
	
	@Autowired
	private UtilsService utils;

	@Test
	void test() {
		Mockito.when(stockService.getCount()).thenReturn(1L);
		
		Assertions.assertEquals(stockService.getCount(), 1);
	}
	
	@Test
	void test1() {
		Assertions.assertEquals(utils.show(), "Hello World!");
	}

}
