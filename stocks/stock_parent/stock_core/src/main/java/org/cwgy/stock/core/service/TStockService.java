package org.cwgy.stock.core.service;

import org.cwgy.stock.core.Mapper.TStockExample;
import org.cwgy.stock.core.dao.TStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TStockService {
	
	@Autowired
	private TStockMapper stockMapper;
	
	public long getCount() {
		TStockExample example = new TStockExample();

	    TStockExample.Criteria criteria = example.createCriteria();
		criteria.andStockCodeEqualTo("0050");
		
		return stockMapper.countByExample(example);
	}

}
