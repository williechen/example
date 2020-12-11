package org.cwgy.stock.core.util;

import java.util.Date;

import org.cwgy.stock.core.TestBaseService;
import org.cwgy.stock.core.model.StockModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestBeanUtils extends TestBaseService {

	@Test
	void testCheckBeanFieldNull() throws IllegalAccessException {
	    StockModel stock = new StockModel();
		
		stock.setStockCode("0050");
		stock.setStockName("50");
		stock.setIssueDate(new Date());
		
	    Assertions.assertFalse(BeanUtils.checkBeanFieldNull(stock.getClass()));
	}

}
