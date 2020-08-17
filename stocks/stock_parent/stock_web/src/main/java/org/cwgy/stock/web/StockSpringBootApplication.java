package org.cwgy.stock.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"org.cwgy.stock.web.*", "org.cwgy.stock.core.*"})
@EnableTransactionManagement
@MapperScan(basePackages = "org.cwgy.stock.core.dao")
public class StockSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockSpringBootApplication.class, args);
    }

}
