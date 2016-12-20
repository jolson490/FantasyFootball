package com.ilmservice.fantasyfootball.db.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ilmservice.fantasyfootball.db.entities.Player;

public class PlayerRepositoryImpl implements PlayerRepositoryCustom {
  private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

  @Autowired
  private PlayerRepository playerRepository;

  @Override
  public void logAndDeletePlayer(Player player) {
    logger.debug("in logAndDeletePlayer(): player={}", player);
    playerRepository.delete(player);
  }

}
