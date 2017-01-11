-- (Some of the commentary in schema.sql also applies to this data.sql file.)

USE nflDB;

INSERT INTO nflTeams (locationAbbreviation, location, mascot) VALUES ('GB', 'Green Bay', 'Packers');
INSERT INTO nflTeams (locationAbbreviation, location, mascot) VALUES ('DET', 'Detroit', 'Lions'); 
INSERT INTO nflTeams (locationAbbreviation, location, mascot) VALUES ('CHI', 'Chicago', 'Bears'); 
INSERT INTO nflTeams (locationAbbreviation, location, mascot) VALUES ('MIN', 'Minnesota', 'Vikings'); 
INSERT INTO nflTeams (locationAbbreviation, location, mascot) VALUES ('TB', 'Tampa Bay', 'Buccaneers'); 

-- To create the "INSERT INTO players" commands, a spreadsheet (described by the README.md) was used as a starting point. 
--  * (As noted on http://www.scoresheet.com/FB_current.php, the columns in that spreadsheet are: SS Player Number, 2014 Total Points, Points Per Game, Age, Team and Bye, Name.)
--  * TO-DO: Instead of storing/maintaing a sort-ordered/unique nflRanking, perhaps:
--     ** the "2014 Total Points" column (from the spreadsheet) should be stored/used (in this 'players') table,
--     ** and derive the corresponding NFL Ranking if needed. 

SET @seq_nflRanking = 0;
INSERT INTO players (nflRanking, fname, lname, position, nflTeam) VALUES 
  (@seq_nflRanking := @seq_nflRanking + 1,'Aaron','Rodgers','QB','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Matthew','Stafford','QB','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jay','Cutler','QB','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jordy','Nelson','WR','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Matt','Forte','RB','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Teddy','Bridgewater','QB','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Randall','Cobb','WR','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Eddie','Lacy','RB','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Golden','Tate','WR','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Alshon','Jeffery','WR','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Mike','Evans','WR','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Calvin','Johnson','WR','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Martellus','Bennett','TE','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Mike','Wallace','WR','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Joique','Bell','RB','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Vincent','Jackson','WR','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Eddie','Royal','WR','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Matt','Asiata','RB','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Shaun','Hill','QB','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Mason','Crosby','K','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jarius','Wright','WR','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Mike','Glennon','QB','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Blair','Walsh','K','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Patrick','Murray','K','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Cordarrelle','Patterson','WR','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jerick','McKinnon','RB','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Davante','Adams','WR','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Bobby','Rainey','RB','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Matt','Prater','K','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Charles','Johnson','WR','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Louis','Murphy','WR','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Doug','Martin','RB','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Andrew','Quarless','TE','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'James','Starks','RB','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Theo','Riddick','RB','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jacquizz','Rodgers','RB','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jeremy','Ross','WR','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Kyle','Rudolph','TE','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Eric','Ebron','TE','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Richard','Rodgers','TE','GB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Austin','Seferian-Jenkins','TE','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Rhett','Ellison','TE','MIN'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Charles','Sims','RB','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Brandon','Myers','TE','TB'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Corey','Fuller','WR','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Marquess','Wilson','WR','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Robbie','Gould','K','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'KaDeem','Carey','RB','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Dante','Rosario','TE','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Brandon','Pettigrew','TE','DET'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Jimmy','Clausen','QB','CHI'),
  (@seq_nflRanking := @seq_nflRanking + 1,'Adrian','Peterson','RB','MIN');
--   (@seq_nflRanking := @seq_nflRanking + 1,'Detroit','Lions','Defense','DET'),
--   (@seq_nflRanking := @seq_nflRanking + 1,'Minnesota','Vikings','Defense','MIN'),
--   (@seq_nflRanking := @seq_nflRanking + 1,'Green Bay','Packers','Defense','GB'),
--   (@seq_nflRanking := @seq_nflRanking + 1,'Tampa Bay','Buccaneers','Defense','TB'),
--   (@seq_nflRanking := @seq_nflRanking + 1,'Chicago','Bears','Defense','CHI'),

INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('jOlson', '1.21 JJ Watts'); 
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('cJohnson', 'Kobayshi Maru');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('amyR', 'Hufflepuff');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('LAnn', 'Packers West');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('jason', 'TitleTown USA');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('jen', 'Undefeated');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('AmyM', '2 Minute Drill');
INSERT INTO fantasyTeams (USERNAME, FANTASYMASCOT) VALUES ('matt', 'Hawkeyes');

INSERT INTO weeklyTeams VALUES (1, 1, 1);
INSERT INTO weeklyTeams VALUES (1, 2, 2);
INSERT INTO weeklyTeams VALUES (10, 1, 3);
INSERT INTO weeklyTeams VALUES (11, 2, 4);
INSERT INTO weeklyTeams VALUES (12, 1, 5);
INSERT INTO weeklyTeams VALUES (13, 2, 5);
