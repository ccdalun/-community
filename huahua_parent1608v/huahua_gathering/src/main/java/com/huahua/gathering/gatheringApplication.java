package com.huahua.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import huahua.common.utils.IdWorker;
@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class gatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(gatheringApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
