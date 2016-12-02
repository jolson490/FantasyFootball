-- (Some of the commentary in schema.sql also applies to this data.sql file.)

-----connect 'jdbc:derby:${ff.database.location}';

SET SCHEMA APP;

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Aaron','Rodgers','QB',1,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Matthew','Stafford','QB',2,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jay','Cutler','QB',3,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Teddy','Bridgewater','QB',4,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Shaun','Hill','QB',5,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Mike','Glennon','QB',6,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jimmy','Clausen','QB',7,'CHI');

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Matt','Forte','RB',1,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Eddie','Lacy','RB',2,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Joique','Bell','RB',3,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Matt','Asiata','RB',4,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jerick','McKinnon','RB',5,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Bobby','Rainey','RB',6,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Doug','Martin','RB',7,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('James','Starks','RB',8,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Theo','Riddick','RB',9,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jacquizz','Rodgers','RB',10,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Charles','Sims','RB',11,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('KaDeem','Carey','RB',12,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Adrian','Peterson','RB',13,'MIN');

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jordy','Nelson','WR',1,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Randall','Cobb','WR',2,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Golden','Tate','WR',3,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Alshon','Jeffery','WR',4,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Mike','Evans','WR',5,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Calvin','Johnson','WR',6,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Mike','Wallace','WR',7,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Vincent','Jackson','WR',8,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Eddie','Royal','WR',9,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jarius','Wright','WR',10,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Cordarrelle','Patterson','WR',11,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Davante','Adams','WR',12,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Charles','Johnson','WR',13,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Louis','Murphy','WR',14,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Jeremy','Ross','WR',15,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Corey','Fuller','WR',16,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Marquess','Wilson','WR',17,'CHI');

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Martellus','Bennett','TE',1,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Andrew','Quarless','TE',2,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Kyle','Rudolph','TE',3,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Eric','Ebron','TE',4,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Richard','Rodgers','TE',5,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Austin','Seferian-Jenkins','TE',6,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Rhett','Ellison','TE',7,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Brandon','Myers','TE',8,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Dante','Rosario','TE',9,'CHI');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Brandon','Pettigrew','TE',10,'DET');

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Mason','Crosby','K',1,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Blair','Walsh','K',2,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Patrick','Murray','K',3,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Matt','Prater','K',4,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Robbie','Gould','K',5,'CHI');

insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Detroit','Lions','Defense',1,'DET');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Minnesota','Vikings','Defense',2,'MIN');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Green Bay','Packers','Defense',3,'GB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Tampa Bay','Buccaneers','Defense',4,'TB');
insert into players (FNAME, LNAME, POSITION, POSITIONRANKING, NFLTEAM) values ('Chicago','Bears','Defense',5,'CHI');

insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('jOlson', '1.21 JJ Watts'); 
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('cJohnson', 'Kobayshi Maru');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('amyR', 'Hufflepuff');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('LAnn', 'Packers West');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('jason', 'TitleTown USA');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('jen', 'Undefeated');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('AmyM', '2 Minute Drill');
insert into fantasyTeams (USERNAME, FANTASYMASCOT) values ('matt', 'Hawkeyes');

insert into weeklyTeams values (1, 1, 1);
insert into weeklyTeams values (1, 2, 2);
insert into weeklyTeams values (10, 1, 3);
insert into weeklyTeams values (11,2, 4);
insert into weeklyTeams values (12, 1, 5);
insert into weeklyTeams values (13, 2, 5);
