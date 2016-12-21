package com.ilmservice.fantasyfootball;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This (Fantasy Football Error Controller) class only gets utilized if setRegisterErrorPageFilter is not called.
@RestController
public class FFErrorController implements ErrorController {
  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String handleError() {
    return "There was an error.";
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
