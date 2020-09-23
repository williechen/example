package org.cwgy.stock.core.service;

import org.cwgy.stock.core.Mapper.TStockExample;
import org.cwgy.stock.core.dao.TStockMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

class TestTStockService extends TestBaseService{
	
	@MockBean
	@Qualifier("TStockMapper")
	private TStockMapper stockMapper;
	
	@Autowired
	private TStockService stockService;
	
	@Test
	void test() {
		TStockExample example = new TStockExample();
		example.createCriteria().andStockCodeEqualTo("0050");
		Mockito.when(stockMapper.countByExample(example)).thenReturn(1L);
		
		Assertions.assertEquals(stockService.getCount(), 1);
	}

}
