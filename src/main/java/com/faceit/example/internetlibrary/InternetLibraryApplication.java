package com.faceit.example.internetlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableCaching
public class InternetLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetLibraryApplication.class, args);
    }
}
