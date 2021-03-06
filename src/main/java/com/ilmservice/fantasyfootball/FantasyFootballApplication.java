package com.ilmservice.fantasyfootball;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = { "com.ilmservice.fantasyfootball.db.entities" })
@EnableJpaRepositories(basePackages = { "com.ilmservice.fantasyfootball.db.repositories" })
public class FantasyFootballApplication {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballApplication.class);

  public FantasyFootballApplication() {
    super();

    // Uncomment the following line to disable ErrorPageFilter - i.e. instead of user getting "Whitelabel Error Page" in their browser, let the server (e.g. Apache Tomcat) handle errors.
    // setRegisterErrorPageFilter(false);
  }
  
  public static void main(String[] args) {
    SpringApplication.run(FantasyFootballApplication.class, args);
  }

  // just to try this out - get warm fuzzy relatively early on during initialization/startup of the application.
  @PostConstruct
  public void logSomething() {
    logger.debug("in logSomething()");
  }
}

// Just to test out a REST controller
@RestController
class GreetingController {
  // e.g. http://localhost:8080/ILMServices-FantasyFootball/hello/JoshO
  @RequestMapping("/hello/{name}")
  public String hello(@PathVariable String name) {
    return "Hello, " + name + "!";
  }
}
