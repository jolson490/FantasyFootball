package com.ilmservice.fantasyfootball.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

///import javax.persistence.NamedNativeQueries;
///import javax.persistence.NamedNativeQuery;

///import org.springframework.transaction.annotation.Transactional;

import com.ilmservice.fantasyfootball.db.entities.Player;
///import javax.persistence.PersistenceContext;

///@Repository
///@Transactional
public interface PlayerRepository extends CrudRepository<Player, Integer> {

  // The underscores are included only for readability sake.
  List<Player> findByFirstName_AndLastName_AllIgnoreCase(String firstName, String lastName);

  @Query("SELECT MAX(nflRanking) FROM Player")
  int getMaxNflRanking();

  int countByNflRanking(Integer nflRanking);

  @Transactional
  @Modifying
  @Query(nativeQuery = true)
  void restartNflRankingHardCoded(); /// @Param("nextGeneratedValue") int nextGeneratedValue

  // Test out different queries than restartNflRankingHardCoded - but somewhat similar.
  @Query(nativeQuery = true)
  List<Player> getForPositionalSpecifiedRanking(int theRanking);
  @Query(nativeQuery = true)
  List<Player> getForNamedSpecifiedRanking(@Param("theRanking") int theRanking);
}
