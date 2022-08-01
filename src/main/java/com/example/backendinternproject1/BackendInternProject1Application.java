package com.example.backendinternproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class BackendInternProject1Application {

  public static void main(String[] args) {
    SpringApplication.run(BackendInternProject1Application.class, args);
  }

  @Bean
  public Jedis getJedis() {
    return new Jedis();
  }

}
