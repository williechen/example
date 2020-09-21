package org.cwgy.stock.batch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class StockBatchServletInitializer extends SpringBootServletInitializer {

	/**
	 * 打包 war 檔 在 server 可共用網域 不同網址服務
	 */
	protected  SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StockBatchApplication.class);
    }
}
