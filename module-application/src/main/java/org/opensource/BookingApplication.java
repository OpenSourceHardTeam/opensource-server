package org.opensource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class BookingApplication {
    public static void main(String[] args) {

        System.out.println("hello");
        SpringApplication.run(BookingApplication.class, args);
    }
}