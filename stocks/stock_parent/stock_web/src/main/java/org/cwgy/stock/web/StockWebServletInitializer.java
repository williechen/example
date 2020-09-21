package org.cwgy.stock.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class StockWebServletInitializer extends SpringBootServletInitializer {
	
	/**
	 * 打包 war 檔 在 server 可共用網域 不同網址服務
	 */
	protected  SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StockWebApplication.class);
    }
}
