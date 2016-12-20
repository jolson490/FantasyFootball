package com.ilmservice.fantasyfootball.db.repositories;

import com.ilmservice.fantasyfootball.db.entities.Player;

public interface PlayerRepositoryCustom {
  public void logAndDeletePlayer(Player player);
}