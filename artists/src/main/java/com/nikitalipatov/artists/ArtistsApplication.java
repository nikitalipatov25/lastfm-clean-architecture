package com.nikitalipatov.artists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ArtistsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtistsApplication.class, args);
    }

}
