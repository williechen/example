package org.cwgy.stock.core.util;

import java.util.Date;

import org.cwgy.stock.core.TestBaseService;
import org.cwgy.stock.core.model.StockModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestBeanUtils extends TestBaseService {
	
	@Autowired
	private BeanUtils beanUtils;

	@Test
	void testCheckBeanFieldNull() {
		StockModel stock = new StockModel();
		
		stock.setStockCode("0050");
		stock.setStockName("50");
		stock.setIssueDate(new Date());
		
	    Assert.assertFalse(beanUtils.checkBeanFieldNull(stock));
	}

}
