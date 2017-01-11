-- To run this sql script outside of Spring Boot:
--  * follow the steps from README.md to make sure your database server is started.
--  * do the following command: 
--      mysql -u root -p -h 127.0.0.1 -P 3307

CREATE DATABASE  IF NOT EXISTS  nflDB;
USE nflDB;

CREATE TABLE nflTeams (
  locationAbbreviation VARCHAR(3)  PRIMARY KEY,
  location VARCHAR(20)  NOT NULL,
  mascot VARCHAR(20)  NOT NULL);

CREATE TABLE players (
  playerPK INT  PRIMARY KEY  AUTO_INCREMENT,
  fname VARCHAR(50)  NOT NULL, 
  lname VARCHAR(50)  NOT NULL, 
  position VARCHAR(2)  NOT NULL,
  nflRanking INT  NOT NULL, 
  nflTeam VARCHAR(3)  NOT NULL,
  CONSTRAINT fk_nflTeam FOREIGN KEY (nflTeam) REFERENCES nflTeams(locationAbbreviation),
  CONSTRAINT chk_Position CHECK (position IN ('QB', 'RB', 'WR', 'TE', 'K')),
  CONSTRAINT chk_nflRanking CHECK (nflRanking >= 1) );
  
CREATE TABLE fantasyTeams (
  Id INT  PRIMARY KEY  AUTO_INCREMENT,
  username VARCHAR(50)  NOT NULL,
  fantasyMascot VARCHAR(100)  NOT NULL );

CREATE TABLE weeklyTeams (
  playerID INT  NOT NULL,
  fantasyTeamId INT  NOT NULL,
  week INT  NOT NULL,
  CONSTRAINT pk_weeklyTeams PRIMARY KEY (playerID, fantasyTeamId, week),
  CONSTRAINT fk_playerID  FOREIGN KEY (playerID)  REFERENCES players(playerPK)  ON DELETE CASCADE,
  CONSTRAINT fk_fantasyTeamId FOREIGN KEY (fantasyTeamId) REFERENCES fantasyTeams(Id),
  CONSTRAINT chk_week CHECK (week >= 1 AND week <= 5) );
