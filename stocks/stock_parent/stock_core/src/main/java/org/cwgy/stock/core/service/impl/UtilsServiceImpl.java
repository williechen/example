package org.cwgy.stock.core.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cwgy.stock.core.service.UtilsService;
import org.springframework.stereotype.Component;

@Component
public class UtilsServiceImpl implements UtilsService{
	Logger log = LogManager.getLogger(getClass());
	
	public String show() {
		return "Hello World!";
	}

}
