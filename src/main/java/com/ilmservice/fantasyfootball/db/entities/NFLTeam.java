package com.ilmservice.fantasyfootball.db.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the NFLTEAMS database table.
 *
 */
@Entity
@Table(name="NFLTEAMS")
@NamedQuery(name="NFLTeam.findAll", query="SELECT n FROM NFLTeam n")
public class NFLTeam implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String locationabbreviation;

  private String location;

  private String mascot;

  //bi-directional many-to-one association to Player
  @OneToMany(mappedBy="nflteamBean")
  private List<Player> players;

  public NFLTeam() {
  }

  public String getLocationabbreviation() {
    return this.locationabbreviation;
  }

  public void setLocationabbreviation(String locationabbreviation) {
    this.locationabbreviation = locationabbreviation;
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

  public List<Player> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public Player addPlayer(Player player) {
    getPlayers().add(player);
    player.setNflteamBean(this);

    return player;
  }

  public Player removePlayer(Player player) {
    getPlayers().remove(player);
    player.setNflteamBean(null);

    return player;
  }

}