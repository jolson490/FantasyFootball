package com.ilmservice.fantasyfootball.db.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nflTeams")
public class NFLTeam {

  @Id
  private String locationAbbreviation;

  private String location;

  private String mascot;

  public String getLocationAbbreviation() {
    return this.locationAbbreviation;
  }
  public void setLocationAbbreviation(String locationAbbreviation) {
    this.locationAbbreviation = locationAbbreviation;
  }

  public String getLocation() {
    return this.location;
  }
  public void setLocation(String location) {
    this.location = location;
  }

  public String getMascot() {
    return this.mascot;
  }
  public void setMascot(String mascot) {
    this.mascot = mascot;
  }

  @Override
  public String toString() {
    return (location + " " + mascot);
  }

}
