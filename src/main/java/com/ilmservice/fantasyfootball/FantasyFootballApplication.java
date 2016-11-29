package com.ilmservice.fantasyfootball;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.ilmservice.fantasyfootball.db.entities" })
@EnableJpaRepositories(basePackages = { "com.ilmservice.fantasyfootball.db.repositories" })
public class FantasyFootballApplication {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(FantasyFootballApplication.class, args);
  }

  @PostConstruct
  public void logSomething() {
    logger.debug("in logSomething()");
  }
}
