package com.example.autoconfigure;

import me.whiteship.Holoman;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoConfigureApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AutoConfigureApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);

    }
}
