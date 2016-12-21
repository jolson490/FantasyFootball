package com.ilmservice.fantasyfootball.db.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ilmservice.fantasyfootball.db.entities.Player;

public class PlayerRepositoryImpl implements PlayerRepositoryCustom {
  private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private PlayerDao playerDao;

  @Override
  public void deletePlayer(Integer playerPK) {
    logger.debug("in deletePlayer(): playerPK={}", playerPK);

    if (playerPK == null) {
      logger.error("playerPK is null");
    } else {
      final Player playerToDelete = playerRepository.findOne(playerPK);
      if (playerToDelete == null) {
        logger.error("Couldn't find Player to delete for playerPK={}", playerPK);
      } else {
        deletePlayer(playerToDelete);
      }
    }
  }

  @Override
  public void deletePlayer(Player player) {
    logger.debug("in deletePlayer(): player={}", player);

    playerRepository.delete(player);

    // (For simplicity, don't bother to skip calling reorderPlayersRankings when the only deleted player
    // was the last one - i.e. the one with the worst nflRanking.)
    reorderPlayersRankings();
  }

  @Override
  public void addPlayer(Player newPlayer) {
    logger.debug("in addPlayer(): newPlayer={}", newPlayer);

    final int requestedNflRanking = newPlayer.getNflRanking();

    // Make sure the next nflRanking value is correct. (The db (call below to PlayerRepository.save) ignores the value of nflRanking in
    // newPlayer - i.e. the db always uses next generated value; this matters when inserting a new player into the middle of the table.)
    int nextGeneratedValue = requestedNflRanking;
    if ((requestedNflRanking == 0) || (requestedNflRanking > playerRepository.count() + 1)) {
      // Override the requestedNflRanking value (that was requested by the client of this 'addPlayer' method).
      nextGeneratedValue = (int) (playerRepository.count() + 1);
    }
    playerDao.restartNflRanking(nextGeneratedValue);

    logger.debug("nextGeneratedValue={}, about to create/save new player: {}", nextGeneratedValue, newPlayer);
    playerRepository.save(newPlayer);

    // If the user did not specify a value for nflRanking, then the db (in the call above to PlayerRepository.save) will have generated the next
    // highest value for 'nflRanking' (i.e. added the player to the end of the table) - in which case there's no need to tell the db to reorder the players.
    // (Note that we also don't need to go into the following "if" check if the user happened to specify the next value that the db was going to
    // generate, but for simplicity don't bother checking for that.)
    if (requestedNflRanking > 0) {
      reorderPlayersRankings();
    }
  }

  @Override
  public void editPlayer(Player player) {
    logger.debug("in editPlayer(): player={}", player);

    playerRepository.save(player);

    // (For simplicity, don't bother to skip calling reorderPlayersRankings when the nflRanking was not edited.)
    reorderPlayersRankings();
  }

  @Override
  public void reorderPlayersRankings() {
    logger.debug("in reorderPlayersRankings(): playerRepository.count()={}", playerRepository.count());
    // Go through all the players and update their ranking - might not be the
    // most efficient, but requires the least amount of code. :)
    // This method is not a no-op when a player has been inserted/removed somewhere from the middle (with regards to nflRanking).
    List<Player> nflPlayers = playerRepository.findAll();
    for (int playerCounter = 0; playerCounter < nflPlayers.size(); playerCounter++) {
      final int newRanking = (playerCounter + 1);
      Player loopPlayer = nflPlayers.get(playerCounter);

      logger.trace("setting ranking to {} for player: {}", newRanking, nflPlayers.get(playerCounter));
      loopPlayer.setNflRanking(newRanking);
      playerRepository.save(loopPlayer);
    }
  }

}
