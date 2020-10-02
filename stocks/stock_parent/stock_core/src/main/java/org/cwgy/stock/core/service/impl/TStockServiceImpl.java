package org.cwgy.stock.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cwgy.stock.core.dao.StockDao;
import org.cwgy.stock.core.model.StockModelExample;
import org.cwgy.stock.core.service.TStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TStockServiceImpl implements TStockService {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private StockDao stockMapper;
	
	public long getCount() {
		log.debug("getCount start ...");
		StockModelExample example = new StockModelExample();

	    StockModelExample.Criteria criteria = example.createCriteria();
		criteria.andStockCodeEqualTo("0050");
		
		return stockMapper.countByExample(example);
	}

}
