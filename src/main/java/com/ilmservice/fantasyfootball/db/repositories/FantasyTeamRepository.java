package com.ilmservice.fantasyfootball.db.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilmservice.fantasyfootball.db.entities.FantasyTeam;

@Repository("fantasyTeamRepository")
public interface FantasyTeamRepository extends CrudRepository<FantasyTeam, Integer> {
}
