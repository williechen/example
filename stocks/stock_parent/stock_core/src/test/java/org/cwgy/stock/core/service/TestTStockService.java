package org.cwgy.stock.core.service;

import org.cwgy.stock.core.Mapper.TStockExample;
import org.cwgy.stock.core.dao.TStockMapper;
import org.cwgy.stock.core.service.impl.TStockServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class TestTStockService extends TestBaseService{
	
	@Mock
	private TStockMapper stockMapper;

	@InjectMocks
	private TStockServiceImpl stockService;

	@Test
	void test() {
		Mockito.when(stockMapper.countByExample(Mockito.isA(TStockExample.class))).thenReturn(1L);
		
		Assertions.assertEquals(stockService.getCount(), 1);
	}

}
