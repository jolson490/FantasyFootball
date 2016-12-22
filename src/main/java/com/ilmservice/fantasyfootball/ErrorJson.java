package com.ilmservice.fantasyfootball;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorJson {
  private static final Logger logger = LoggerFactory.getLogger(ErrorJson.class);

  public Integer status;

  public String timeStamp;
  public String error;
  public String exception;
  public String message;
  public String errors;
  // public String trace;
  public String path;

  public ErrorJson(int status, Map<String, Object> errorAttributes) {
    logger.debug("in ErrorJson(): status={} errorAttributes.size()={}", status, errorAttributes.size());

    this.status = status;

    // Determine the class type for each attribute.
    // errorAttributes.forEach((key, value) -> logger.debug("key={} value.getClass(): {} value: {}", key, value.getClass(), value));

    this.timeStamp = errorAttributes.get("timestamp").toString(); // "timestamp" = java.util.Date
    this.error = (String) errorAttributes.get("error");
    this.exception = (String) errorAttributes.get("exception");
    this.message = (String) errorAttributes.get("message");
    this.errors = (String) errorAttributes.get("errors");
    // this.trace = (String) errorAttributes.get("trace");
    this.path = (String) errorAttributes.get("path");
  }
}
