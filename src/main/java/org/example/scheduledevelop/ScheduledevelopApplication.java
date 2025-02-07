package org.example.scheduledevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduledevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledevelopApplication.class, args);
    }

}
