package com.ilmservice.fantasyfootball.db.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilmservice.fantasyfootball.db.entities.NFLTeam;

@Repository("nflTeamRepository")
public interface NFLTeamRepository extends CrudRepository<NFLTeam, String> {
}
