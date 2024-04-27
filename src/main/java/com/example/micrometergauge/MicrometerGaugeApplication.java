package com.example.micrometergauge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicrometerGaugeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicrometerGaugeApplication.class, args);
    }

}
