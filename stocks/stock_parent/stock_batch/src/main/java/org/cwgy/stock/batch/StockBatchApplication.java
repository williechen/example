package org.cwgy.stock.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//停止內建的ThymeleafAutoConfiguration
@SpringBootApplication(scanBasePackages = { "org.cwgy.stock.core.*",
		"org.cwgy.stock.batch.*" }, exclude = org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class)
@EnableTransactionManagement
@EnableScheduling
public class StockBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockBatchApplication.class, args);
	}
}
