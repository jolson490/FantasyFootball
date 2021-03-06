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
    reorderPlayersRankings(null);
  }

  @Override
  public void addPlayer(Player newPlayer) {
    logger.debug("in addPlayer(): newPlayer={}", newPlayer);

    final int requestedNflRanking = newPlayer.getNflRanking();
    final int actualNflRanking = getActualNflRanking(requestedNflRanking);
    newPlayer.setNflRanking(actualNflRanking);

    logger.debug("about to create/save new player: {}", newPlayer);
    playerRepository.save(newPlayer);

    // If the user did not specify a value for nflRanking, then 'getActualNflRanking' will have set it to the next
    // highest value (i.e. added the player to the end of the table) - in which case there's no need to tell the db to reorder the players.
    // (Note that we also don't need to go into the following "if" check if the user happened to specify the next value that the db was going to
    // generate, but for simplicity don't bother checking for that.)
    if (requestedNflRanking > 0) {
      reorderPlayersRankings(newPlayer);
    }
  }

  // Given the requested Nfl Ranking, return what value can actually be allowed.
  private int getActualNflRanking(final int requestedNflRanking) {
    int actualNflRanking = requestedNflRanking;
    if ((requestedNflRanking <= 0) || (requestedNflRanking > playerRepository.count() + 1)) {
      // Override the requestedNflRanking value
      actualNflRanking = (int) (playerRepository.count() + 1);
    }
    logger.debug("getActualNflRanking(): requestedNflRanking={} actualNflRanking={}", requestedNflRanking, actualNflRanking);
    return actualNflRanking;
  }

  @Override
  public void editPlayer(Player player) {
    logger.debug("in editPlayer(): player={}", player);

    // Handle a request that omitted the nflRanking (in which case the default value is 0) - e.g.:
    //  * curl http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/4/saveEditedNFLPlayer --data "firstName=Jordy&lastName=Nelson&nflTeam=GB&position=WR"
    final int requestedNflRanking = player.getNflRanking();
    final int actualNflRanking = getActualNflRanking(requestedNflRanking);
    player.setNflRanking(actualNflRanking);

    playerRepository.save(player);

    // (For simplicity, don't bother to skip calling reorderPlayersRankings when the nflRanking was not edited.)
    reorderPlayersRankings(player);
  }

  // This method is not a no-op when a player has been inserted/removed somewhere from the middle (with regards to nflRanking).
  // 'updatedPlayer' is the added or edited player.
  @Override
  public void reorderPlayersRankings(Player updatedPlayer) {
    final Integer updatedRanking = (updatedPlayer != null) ? updatedPlayer.getNflRanking() : 0;
    logger.debug("in reorderPlayersRankings(): playerRepository.count()={} updatedPlayer={} updatedRanking={}",
        playerRepository.count(), updatedPlayer, updatedRanking);

    // Go through all the players and update their ranking - might not be the
    // most efficient, but requires the least amount of code. :)
    List<Player> nflPlayers = playerRepository.findAll(); // gets players ordered by nflRanking
    for (int playerCounter = 0; playerCounter < nflPlayers.size(); playerCounter++) {
      final int newRanking = (playerCounter + 1);
      Player loopPlayer = nflPlayers.get(playerCounter);

      logger.trace("setting ranking to {} for player: {}", newRanking, nflPlayers.get(playerCounter));
      loopPlayer.setNflRanking(newRanking);
      playerRepository.save(loopPlayer);
    }

    // The above for loop isn't perfect - need to check for & handle the
    // situation where the actualNflRanking was already in use by an existing
    // player, in which case the above call to findAll might not have returned
    // the players in an order such that would continue to have 'updatedPlayer'
    // have its ranking equal to actualNflRanking.
    // e.g., before adding the following block of code, if (with the default
    // data from data.sql) you edit Randall Cobb's ranking from 7 to 2, he will
    // actually end up with 3 (and Matthew Stafford will stay at 2).
    if (updatedPlayer != null) {
      // Get the player whose ranking might've been set to a wrong value by the for loop above.
      Player theReupdatedPlayer = playerRepository.findOne(updatedPlayer.getPlayerPK());

      final int rankingAfterLoop = theReupdatedPlayer.getNflRanking();
      logger.debug("theReupdatedPlayer={} rankingAfterLoop={}", theReupdatedPlayer, rankingAfterLoop);

      // (At this point, 'updatedPlayer' can be used to retrieve anything except
      // the ranking - so use 'updatedRanking'.)
      // Check if the updated player did indeed get their ranking set to a wrong value.
      if (updatedRanking != rankingAfterLoop) {
        // Find the player who has the ranking that the updated player is supposed to have.
        List<Player> otherPlayerList = playerRepository.findByNflRanking(updatedRanking);
        if (otherPlayerList.size() != 1) {
          logger.error("unexpected otherPlayerList: {}", otherPlayerList);
        } else {
          // Swap the ranking of the two players.

          Player otherPlayer = otherPlayerList.get(0);
          otherPlayer.setNflRanking(rankingAfterLoop);
          playerRepository.save(otherPlayer);

          theReupdatedPlayer.setNflRanking(updatedRanking);
          playerRepository.save(theReupdatedPlayer);

          logger.debug("after swap: otherPlayer={} theReupdatedPlayer={}", otherPlayer, theReupdatedPlayer);
        }
      }
    } // end if not null
  } // end reorderPlayersRankings
}
