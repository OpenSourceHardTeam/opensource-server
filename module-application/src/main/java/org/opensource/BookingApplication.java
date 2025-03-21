package org.opensource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingApplication {
    public static void main(String[] args) {

        System.out.println("hello");
        SpringApplication.run(BookingApplication.class, args);
    }
}