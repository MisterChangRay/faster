package com.github.misterchangray.financial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.**.mapper.intf"})
@SpringBootApplication(scanBasePackages = {"com.github.misterchangray"})
public class FasterFinancialApplication {

    public static void main(String[] args) {
        SpringApplication.run(FasterFinancialApplication.class, args);
    }

}
