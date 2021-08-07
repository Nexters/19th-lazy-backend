package com.teamnexters.lazy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.teamnexters.lazy")
@EnableJpaRepositories(basePackages = "com.teamnexters.lazy")
@EntityScan(basePackages = "com.teamnexters.lazy")
public class LazyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LazyApiApplication.class, args);
    }

}
