package com.gfi.ausbildung.weatherdude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.gfi.ausbildung.weatherdude.entities.repositories")
@EnableScheduling
public class WeatherDudeApp
{
  public static void main(String[] args)
  {
    SpringApplication.run(WeatherDudeApp.class, args);
  }
}
