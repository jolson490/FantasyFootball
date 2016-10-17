package com.ilmservice.fantasyfootball;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fantasyteams")
public class FantasyTeam {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name = "USERNAME")
  String username;
  @Column(name = "FANTASYMASCOT")
  String mascot;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMascot() {
    return mascot;
  }

  public void setMascot(String mascot) {
    this.mascot = mascot;
  }

}
