-- ============================

-- This file was created using Derby's dblook utility.
-- Timestamp: 2016-11-29 14:48:11.019
-- Source database is: C:\Users\Admin\git\FantasyFootball\nflDB
-- Connection URL is: jdbc:derby:C:\Users\Admin\git\FantasyFootball\nflDB
-- Specified schema is: APP
-- appendLogs: false

-- NOTES:
--  * More info about the SQL commands/syntax supported by Derby can be found in the Derby Reference Manual, which can be found at: https://db.apache.org/derby/manuals/
--  * This file was created by doing:
--      Admin@DESKTOP-4P28AA3 ~/git/FantasyFootball
--      $ dblook -d 'jdbc:derby:C:\Users\Admin\git\FantasyFootball\nflDB' -z APP -o 'C:\Users\Admin\Desktop\schema.sql'
--  * Originally this FantasyFootball project used SQL commands in the PopulateDB class (in the com.ilmservice.fantasyfootball.db package) to create the tables (and add rows to the tables).
--    But then that nflDB folder/database was used to create the equivalent commands in this schema.sql file (and the data.sql file). (And at that time PopulateDB was deleted.)

-- ============================

-- Note that Apache Derby is a relational database - more info can be found at: https://db.apache.org/derby/ 

-- Note that 'ij' is an interactive SQL scripting tool that comes with Derby - more info can be found at:
--   http://db.apache.org/derby/papers/DerbyTut/ij_intro.html
-- (And note that Derby is included in the JDK.)
--
-- OPTIONAL PREREQUISITE (in order to run 'ij' from Cygwin command line):
-- * Make sure you include %JAVA_HOME%\db\bin in your PATH; so that 'ij' is found - e.g.:
--     Joshua@JoshuaOlson /cygdrive/c/Users/Joshua/git/FantasyFootball
--     $ which ij
--     /cygdrive/c/Program Files/Java/jdk1.8.0_65/db/bin/ij
--
-- e.g. of how to run 'ij' from a Cygwin terminal (the following 'connect'
-- command assumes the 'nflDB' folder/database exists within the current directory):
--     Joshua@JoshuaOlson /cygdrive/c/Users/Joshua/git/FantasyFootball
--     $ ij
--     ij version 10.11
--     ij> connect 'jdbc:derby:nflDB';
--
-- (Note that https://db.apache.org/derby/integrate/derby_plugin_info.html
-- says between Derby versions "10.3 and 10.8.2" that Derby distributions
-- included plugins for Eclipse.)
  
-- ============================

-- To run this sql script outside of Spring Boot: 
--  * uncomment the following "connect" line, and change "${ff.database.location}" to the value of the ff.database.location property from application.properties, 
--  * and then in a terminal in "src/main/resources/" do "ij schema.sql".
-----connect 'jdbc:derby:${ff.database.location};create=true';

-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

-- From PopulateDB:
--          "CREATE TABLE weeklyTeams (" 
--          + "playerID INT NOT NULL," 
--          + "fantasyTeamId INT NOT NULL,"
--          + "week INT NOT NULL  CHECK (week >= 1 AND week <= 5)," 
--          + "PRIMARY KEY (playerID, fantasyTeamId, week),"
--          + "FOREIGN KEY (playerID) REFERENCES players(ID)," 
--          + "FOREIGN KEY (fantasyTeamId) REFERENCES fantasyTeams(Id)" + ")"
CREATE TABLE "APP"."WEEKLYTEAMS" (
  "PLAYERID" INTEGER NOT NULL, 
  "FANTASYTEAMID" INTEGER NOT NULL, 
  "WEEK" INTEGER NOT NULL);

-- From PopulateDB:
--          "CREATE TABLE fantasyTeams (" 
--              + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
--              + "username VARCHAR(50) NOT NULL," 
--              + "fantasyMascot VARCHAR(100) NOT NULL," 
--              + "PRIMARY KEY (Id)" + ")"
CREATE TABLE "APP"."FANTASYTEAMS" (
  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
  "USERNAME" VARCHAR(50) NOT NULL, 
  "FANTASYMASCOT" VARCHAR(100) NOT NULL);

-- From PopulateDB:
--          "CREATE TABLE players (" 
--              + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
--              + "fname VARCHAR(50) NOT NULL," 
--              + "lname VARCHAR(50) NOT NULL," 
--              + "position VARCHAR(10) NOT NULL,"
--              + "positionRanking INT NOT NULL CHECK (positionRanking >= 0)," 
--              + "nflTeam VARCHAR(50) NOT NULL,"
--              + "PRIMARY KEY (ID)" + ")"
CREATE TABLE "APP"."PLAYERS" (
  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
  "FNAME" VARCHAR(50) NOT NULL, 
  "LNAME" VARCHAR(50) NOT NULL, 
  "POSITION" VARCHAR(10) NOT NULL, 
  "POSITIONRANKING" INTEGER NOT NULL, 
  "NFLTEAM" VARCHAR(50) NOT NULL);

-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- PRIMARY/UNIQUE
ALTER TABLE "APP"."WEEKLYTEAMS" ADD CONSTRAINT "SQL161128153209451" PRIMARY KEY ("PLAYERID", "FANTASYTEAMID", "WEEK");

ALTER TABLE "APP"."FANTASYTEAMS" ADD CONSTRAINT "SQL161128153209440" PRIMARY KEY ("ID");

ALTER TABLE "APP"."PLAYERS" ADD CONSTRAINT "SQL161128153209391" PRIMARY KEY ("ID");

-- FOREIGN
ALTER TABLE "APP"."WEEKLYTEAMS" ADD CONSTRAINT "SQL161128153209452" FOREIGN KEY ("PLAYERID") REFERENCES "APP"."PLAYERS" ("ID") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "APP"."WEEKLYTEAMS" ADD CONSTRAINT "SQL161128153209453" FOREIGN KEY ("FANTASYTEAMID") REFERENCES "APP"."FANTASYTEAMS" ("ID") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------------------------
-- DDL Statements for checks
-- ----------------------------------------------

ALTER TABLE "APP"."WEEKLYTEAMS" ADD CONSTRAINT "SQL161128153209450" CHECK (week >= 1 AND week <= 5);

ALTER TABLE "APP"."PLAYERS" ADD CONSTRAINT "SQL161128153209390" CHECK (positionRanking >= 0);

