package org.cwgy.stock.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"org.cwgy.stock.core.*", "org.cwgy.stock.batch.*"})
@EnableTransactionManagement
@EnableScheduling
public class StockSpringBootApplication {

	 public static void main(String[] args) {
	        SpringApplication.run(StockSpringBootApplication.class, args);
	    }
}
