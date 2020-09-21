package org.cwgy.stock.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "org.cwgy.stock.core.dao")
public class StockCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockCoreApplication.class, args);
    }

}
