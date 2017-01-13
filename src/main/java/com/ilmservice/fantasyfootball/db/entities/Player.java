package com.ilmservice.fantasyfootball.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "players")
@NamedQuery(name = "Player.findAll", query = "select p from Player p order by p.nflRanking")
public class Player {

  @Id
  @GeneratedValue
  @Column(name = "PLAYERPK")
  private Integer playerPK;

  // (For a required string field, "@NotNull" alone won't do the trick - also need "@Size(min = 1)".)

  @Column(name = "FNAME")
  @NotNull @Size(min = 1, message = "First Name is required")
  private String firstName;

  @Column(name = "LNAME")
  @NotNull @Size(min = 1, message = "Last Name is required")
  private String lastName;

  @Column(name = "POSITION")
  @NotNull @Size(min = 1, message = "Position is required")
  // Without the @Size, then for the following curl command, instead of (for position) getting form error "size must be between ...", would get an exception (for POSITION_CONSTRAINT being violated):
  //  * curl -X POST -F firstName=Steve -F lastName=Rodgers -F position="" -F nflRanking=40 -F nflTeam=TB http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer
  private String position;

  // Note that there is no PK/unique constraint on this field - which allows the
  // application code to create a new player that (temporarily) has the same ranking as an existing player (by bumping down the ranking of all subsequent players).
  @Column(name = "NFLRANKING")
  @NotNull(message = "NFL Ranking is required") // Similar to @Size above (for 'position'), @NotNull is needed here (for 'nflRanking') to avoid an exception for an invalid curl command (when nflRanking is omitted - and thus null).
  private Integer nflRanking;

  @OneToOne
  @JoinColumn(name = "NFLTEAM")
  @NotNull // Similar to @Size above (for 'position'), @NotNull is needed here (for 'nflTeam') to avoid an exception for an invalid curl command.
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

  public Integer getNflRanking() {
    return nflRanking;
  }

  public void setNflRanking(Integer nflRanking) {
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
