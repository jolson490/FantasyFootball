-- (Some of the commentary in schema.sql also applies to this data.sql file.)

-- This script deletes all data that was created by the schema.sql & data.sql scripts.

USE nflDB;
DROP TABLE IF EXISTS weeklyTeams;
DROP TABLE IF EXISTS fantasyTeams;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS nflTeams;
