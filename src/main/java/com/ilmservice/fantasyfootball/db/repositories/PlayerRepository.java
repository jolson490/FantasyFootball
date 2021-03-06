package com.ilmservice.fantasyfootball.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ilmservice.fantasyfootball.db.entities.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer>, PlayerRepositoryCustom {

  // The underscores are included only for readability sake.
  List<Player> findByFirstName_AndLastName_AllIgnoreCase(String firstName, String lastName);

  List<Player> findByNflRanking(Integer nflRanking);

  @Override
  @Query
  List<Player> findAll();
}
