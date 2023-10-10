package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
 
@SpringBootApplication
public class SbbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbbApplication.class, args);
    }
    @Configuration
    public class MyConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
