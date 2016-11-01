package com.ilmservice.fantasyfootball;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FantasyFootballApplication {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballApplication.class);

  public static void main(String[] args) {
    logger.debug("begin main()");
    System.out.println("begin main() - System.out.println");

    ApplicationContext ctx = SpringApplication.run(FantasyFootballApplication.class, args);
    logger.debug("Let's inspect the beans provided by Spring Boot:");
    String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      logger.debug(beanName);
    }

    // TODO why not get output?
    logger.debug("end main");
    System.out.println("end main - System.out.println");
  }
}
