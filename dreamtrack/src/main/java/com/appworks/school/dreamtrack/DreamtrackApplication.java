package com.appworks.school.dreamtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DreamtrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamtrackApplication.class, args);
    }

}
