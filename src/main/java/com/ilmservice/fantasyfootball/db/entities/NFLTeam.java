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

  /*
   * //bi-directional many-to-one association to Player
   * 
   * @OneToMany(mappedBy="nflteamBean") private List<Player> players;
   */

  public String getLocationabbreviation() {
    return this.locationAbbreviation;
  }
  public void setLocationabbreviation(String locationabbreviation) {
    this.locationAbbreviation = locationabbreviation;
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

  /*
   * public List<Player> getPlayers() { return this.players; }
   * 
   * public void setPlayers(List<Player> players) { this.players = players; }
   * 
   * public Player addPlayer(Player player) { getPlayers().add(player);
   * player.setNflteamBean(this);
   * 
   * return player; }
   * 
   * public Player removePlayer(Player player) { getPlayers().remove(player);
   * player.setNflteamBean(null);
   * 
   * return player; }
   */
}
