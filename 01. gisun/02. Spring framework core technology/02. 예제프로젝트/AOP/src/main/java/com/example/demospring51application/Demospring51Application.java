package com.example.demospring51application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demospring51Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Demospring51Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
