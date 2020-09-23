package org.cwgy.stock.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// 停止內建的ThymeleafAutoConfiguration
@SpringBootApplication(scanBasePackages = { "org.cwgy.stock.web.*",
		"org.cwgy.stock.core.*" }, exclude = org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan(basePackages = "org.cwgy.stock.core.dao")
public class StockWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockWebApplication.class, args);
	}

}
