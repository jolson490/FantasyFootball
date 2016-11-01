package com.ilmservice.fantasyfootball.db.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ilmservice.fantasyfootball.db.entities.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
