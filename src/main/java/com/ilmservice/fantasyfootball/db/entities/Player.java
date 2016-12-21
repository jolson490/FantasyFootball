package com.ilmservice.fantasyfootball.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "Players")
@NamedQuery(name = "Player.findAll", query = "select p from Player p order by p.nflRanking")
public class Player {

  // Both FantasyTeam and this Player class have an INT primary key.
  //  * But FantasyTeam simply only has the "Id" & "GeneratedValue" (& "Column") annotations on its PK (named 'Id') because its PK is created with "GENERATED ALWAYS AS IDENTITY".
  //  * This 'playerPK' field is not created with "GENERATED ... AS IDENTITY" (the 'nflRanking' field is created with that - and SQL doesn't allow more than one identity in a given table), hence the sequence is needed.
  // (It seems like it would be more accurate if the "Id" annotation were named "Pk" - because this annotation is to be used with a PK, which might not necessarily be AS IDENTITY.)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playerPK_generator")
  @SequenceGenerator(name = "playerPK_generator", schema = "APP", sequenceName = "playerPK_seq", initialValue = 1, allocationSize = 1)
  @Column(name = "PLAYERPK")
  private Integer playerPK;

  @Column(name = "FNAME")
  private String firstName;

  @Column(name = "LNAME")
  private String lastName;

  @Column(name = "POSITION")
  private String position;

  // This 'nflRanking' field is created with "GENERATED BY DEFAULT AS IDENTITY", but "Id" & "GeneratedValue" (& "SequenceGenerator") can only be used for primary keys. So instead
  // of creating a separate entity generator class (perhaps with its own table?), I decided to use @Generated (even though it's not JPA - it introduces a dependency on Hibernate).
  // Also, note that "a GENERATED BY DEFAULT column does not guarantee uniqueness", and there is no PK/unique constraint on this field - which allows the
  // application code to create a new player that has the same ranking as an existing player (by first bumping down the ranking of all subsequent players).
  @Column(name = "NFLRANKING")
  @Generated(GenerationTime.INSERT)
  @NotNull(message = "The nflRanking is required") // (I couldn't come up with a situation (with browser nor curl) where this error message gets presented.)
  private int nflRanking;

  @OneToOne
  @JoinColumn(name = "NFLTEAM")
  private NFLTeam nflTeam;

  public Player() {
  }

  public Player(String firstName, String lastName, String position, int nflRanking, NFLTeam nflTeam) {
    setFirstName(firstName);
    setLastName(lastName);
    setPosition(position);
    setNflRanking(nflRanking);
    setNflTeam(nflTeam);
  }

  public Integer getPlayerPK() {
    return playerPK;
  }

  public void setPlayerPK(Integer playerPK) {
    this.playerPK = playerPK;
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

  public int getNflRanking() {
    return nflRanking;
  }
  public void setNflRanking(int nflRanking) {
    this.nflRanking = nflRanking;
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
        "Player[playerPK=%5d, firstName='%12s', lastName='%20s', position='%3s', nflRanking=%3d, nflTeam='%25s']",
        playerPK, firstName, lastName, position, nflRanking, nflTeam);
  }

  //@formatter:off
  /*
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (playerPK == null || obj == null || getClass() != obj.getClass())
      return false;
    Player that = (Player) obj;
    return playerPK.equals(that.playerPK);
  }
  @Override
  public int hashCode() {
    return playerPK == null ? 0 : playerPK.hashCode();
  }
   */
  //@formatter:on
}
