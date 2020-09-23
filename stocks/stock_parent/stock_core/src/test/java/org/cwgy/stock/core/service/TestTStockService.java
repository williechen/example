package org.cwgy.stock.core.service;

import org.cwgy.stock.core.dao.TStockMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class TestTStockService extends TestBaseService{
	
	@MockBean
	private TStockMapper stockMapper;
	
	@Autowired
	private TStockService stockService;
	
	@Test
	void test() {
		Mockito.when(stockService.getCount()).thenReturn(1L);
		
		Assertions.assertEquals(stockService.getCount(), 1);
	}

}
