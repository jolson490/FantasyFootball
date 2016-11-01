package com.ilmservice.fantasyfootball.db.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ilmservice.fantasyfootball.db.entities.FantasyTeam;

public interface FantasyTeamRepository extends CrudRepository<FantasyTeam, Long> {

}
