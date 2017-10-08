package com.app;

import com.config.MvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MvcConfig.class)
public class Application {
    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
