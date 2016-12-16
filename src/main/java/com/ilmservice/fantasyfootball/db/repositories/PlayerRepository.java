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

  // java.sql.SQLSyntaxErrorException: Syntax error: Encountered "?" at line 1, column 58.
  @Transactional
  @Modifying
  @Query(value = "ALTER TABLE players ALTER COLUMN nflRanking RESTART WITH ?#{[0]}", nativeQuery = true)
  void restartNflRanking(int nextGeneratedValue); /// @Param("nextGeneratedValue") int nextGeneratedValue

  // Test out different queries than restartNflRankingHardCoded - but somewhat similar.
  //
  // JPQL positional input parameter
  @Query(nativeQuery = true)
  List<Player> getForPositionalSpecifiedRanking(int theRanking);
  //
  // JPQL named input parameter
  @Query(nativeQuery = true)
  List<Player> getForNamedSpecifiedRanking(@Param("theRanking") int theRanking);
  //
  // SpEL dynamically bound query parameter - access the method argument
  @Query(value = "SELECT * FROM players WHERE nflRanking= ?#{[0]}", nativeQuery = true)
  List<Player> getForSpELSpecifiedRanking(int nextGeneratedValue);
}
