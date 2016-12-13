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
  private String firstName;

  @Column(name = "LNAME")
  private String lastName;

  @Column(name = "POSITION")
  private String position;

  @Column(name = "POSITIONRANKING")
  private int positionRanking;

  @OneToOne
  @JoinColumn(name = "NFLTEAM")
  private NFLTeam nflTeam;

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

  public int getPositionRanking() {
    return positionRanking;
  }
  public void setPositionRanking(int positionRanking) {
    this.positionRanking = positionRanking;
  }

  public NFLTeam getNflTeam() {
    return nflTeam;
  }
  public void setNflTeam(NFLTeam nflTeam) {
    this.nflTeam = nflTeam;
  }

  @Override
  public String toString() {
    return String.format(
        "Player[id=%2d, firstName='%12s', lastName='%20s', position='%3s', positionRanking=%3d, nflTeam='%25s']",
        id, firstName, lastName, position, positionRanking, nflTeam);
  }
}
