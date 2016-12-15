-- (Some of the commentary in schema.sql also applies to this data.sql file.)

-- This script deletes all data that was created by the schema.sql & data.sql scripts.

-----connect 'jdbc:derby:${ff.database.location}';
connect 'jdbc:derby://localhost:1527/C:/Users/Admin/git/FantasyFootball/nflDB';

-- Note that Derby doesn't support "IF EXISTS" - so just simply try to DROP everything, and it doesn't matter if any of the commands return a "does not exist" error.

DROP TABLE weeklyTeams;
DROP TABLE fantasyTeams;
DROP TABLE players;
DROP TABLE nflTeams;

DROP SEQUENCE playerPK_seq RESTRICT;
