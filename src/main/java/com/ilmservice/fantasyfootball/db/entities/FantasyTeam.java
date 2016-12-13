package com.ilmservice.fantasyfootball.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fantasyTeams")
public class FantasyTeam implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "Id")
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "fantasyMascot")
  private String mascot;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

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

  @Override
  public String toString() {
    return String.format("FantasyTeam[id=%2d, username='%10s', mascot='%20s']", id, username, mascot);
  }

}
