package com.ilmservice.fantasyfootball.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private int id;

  @Column(name = "FNAME")
  String firstName;

  @Column(name = "LNAME")
  String lastName;

  @Column(name = "POSITION")
  String position;

  @OneToOne
  @JoinColumn(name = "NFLTEAM")
  NFLTeam nflTeam;

  @Column(name = "POSITIONRANKING")
  int positionRanking;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }

  public NFLTeam getNflTeam() {
    return nflTeam;
  }
  public void setNflTeam(NFLTeam nflTeam) {
    this.nflTeam = nflTeam;
  }

  public int getPositionRanking() {
    return positionRanking;
  }
  public void setPositionRanking(int positionRanking) {
    this.positionRanking = positionRanking;
  }
}
