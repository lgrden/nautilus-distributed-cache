package io.wegetit.nautilus.simple;

import io.wegetit.nautilus.commons.cache.EnableDistributedCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedCache
public class SimpleMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(SimpleMicroservice.class, args);
    }
}
