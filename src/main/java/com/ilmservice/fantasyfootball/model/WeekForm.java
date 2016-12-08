package com.ilmservice.fantasyfootball.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class WeekForm {
  @Min(1)
  @Max(5)
  private int week;

  public int getWeek() {
    return week;
  }

  public void setWeek(int week) {
    this.week = week;
  }
}
