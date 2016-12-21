package com.ilmservice.fantasyfootball.db.repositories;

import com.ilmservice.fantasyfootball.db.entities.Player;

public interface PlayerRepositoryCustom {
  public void deletePlayer(Integer playerPK);

  public void deletePlayer(Player player);

  public void addPlayer(Player newPlayer);

  public void editPlayer(Player player);

  public void reorderPlayersRankings();
}
