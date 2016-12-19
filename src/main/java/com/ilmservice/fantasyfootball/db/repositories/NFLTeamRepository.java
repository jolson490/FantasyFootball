package com.ilmservice.fantasyfootball.db.repositories;

///import java.util.List;

///import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ilmservice.fantasyfootball.db.entities.NFLTeam;

@Repository("nflTeamRepository")
public interface NFLTeamRepository extends CrudRepository<NFLTeam, String> {
  /// @Query("SELECT locationAbbreviation FROM NFLTeam")
  /// List<String> getLocationAbbreviations();
}
