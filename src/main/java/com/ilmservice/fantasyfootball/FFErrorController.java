package com.ilmservice.fantasyfootball;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

// Fantasy Football Error Controller class
@RestController
public class FFErrorController implements ErrorController {
  private final String PATH = "/error";

  @Autowired
  private ErrorAttributes errorAttributes;

  // (This mapping only gets utilized if SpringBootServletInitializer.setRegisterErrorPageFilter is not called.)
  @RequestMapping(value = PATH)
  ErrorJson handleError(HttpServletRequest request, HttpServletResponse response) {
    // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
    // Here we just define response body.
    return new ErrorJson(response.getStatus(), getErrorAttributes(request, false));
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
    RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }

  // For testing purposes only - shows that if/when using Tomcat (not the 'handleError' method above) then the application stack trace is exposed to the client/browser.
  // @formatter:off
  /* 
  @GetMapping("/throwException")
  public String exception1() {
    throw new NullPointerException("Sample null pointer exception text");
  }
  */
  // @formatter:on
}
