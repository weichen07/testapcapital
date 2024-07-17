package com.weitest.testapcapital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.weitest.testapcapital.mapper")
public class TestapcapitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestapcapitalApplication.class, args);
	}

}
