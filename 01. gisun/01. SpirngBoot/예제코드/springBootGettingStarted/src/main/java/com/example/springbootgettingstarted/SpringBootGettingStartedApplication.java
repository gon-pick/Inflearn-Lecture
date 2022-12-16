package com.example.springbootgettingstarted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/*
@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
이 세개가 아래 한개를 구성한다.
 */
@SpringBootApplication
public class SpringBootGettingStartedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGettingStartedApplication.class, args);
    }

}
