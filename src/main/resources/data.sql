-- (Some of the commentary in schema.sql also applies to this data.sql file.)

-----connect 'jdbc:derby:${ff.database.location}';

SET SCHEMA APP;

insert into nflTeams (locationAbbreviation, location, mascot) values ('GB', 'Green Bay', 'Packers');
insert into nflTeams (locationAbbreviation, location, mascot) values ('DET', 'Detroit', 'Lions'); 
insert into nflTeams (locationAbbreviation, location, mascot) values ('CHI', 'Chicago', 'Bears'); 
insert into nflTeams (locationAbbreviation, location, mascot) values ('MIN', 'Minnesota', 'Vikings'); 
insert into nflTeams (locationAbbreviation, location, mascot) values ('TB', 'Tampa Bay', 'Buccaneers'); 

--TODO1: change logical value from positionRanking to nflRanking - keep DEFAULT, re-order all players?
insert into players values (NEXT VALUE FOR playerPK_seq,'Aaron','Rodgers','QB',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Matthew','Stafford','QB',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jay','Cutler','QB',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Teddy','Bridgewater','QB',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Shaun','Hill','QB',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Mike','Glennon','QB',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jimmy','Clausen','QB',DEFAULT,'CHI');

insert into players values (NEXT VALUE FOR playerPK_seq,'Matt','Forte','RB',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Eddie','Lacy','RB',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Joique','Bell','RB',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Matt','Asiata','RB',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jerick','McKinnon','RB',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Bobby','Rainey','RB',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Doug','Martin','RB',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'James','Starks','RB',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Theo','Riddick','RB',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jacquizz','Rodgers','RB',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Charles','Sims','RB',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'KaDeem','Carey','RB',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Adrian','Peterson','RB',DEFAULT,'MIN');

insert into players values (NEXT VALUE FOR playerPK_seq,'Jordy','Nelson','WR',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Randall','Cobb','WR',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Golden','Tate','WR',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Alshon','Jeffery','WR',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Mike','Evans','WR',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Calvin','Johnson','WR',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Mike','Wallace','WR',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Vincent','Jackson','WR',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Eddie','Royal','WR',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jarius','Wright','WR',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Cordarrelle','Patterson','WR',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Davante','Adams','WR',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Charles','Johnson','WR',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Louis','Murphy','WR',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Jeremy','Ross','WR',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Corey','Fuller','WR',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Marquess','Wilson','WR',DEFAULT,'CHI');

insert into players values (NEXT VALUE FOR playerPK_seq,'Martellus','Bennett','TE',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Andrew','Quarless','TE',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Kyle','Rudolph','TE',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Eric','Ebron','TE',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Richard','Rodgers','TE',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Austin','Seferian-Jenkins','TE',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Rhett','Ellison','TE',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Brandon','Myers','TE',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Dante','Rosario','TE',DEFAULT,'CHI');
insert into players values (NEXT VALUE FOR playerPK_seq,'Brandon','Pettigrew','TE',DEFAULT,'DET');

insert into players values (NEXT VALUE FOR playerPK_seq,'Mason','Crosby','K',DEFAULT,'GB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Blair','Walsh','K',DEFAULT,'MIN');
insert into players values (NEXT VALUE FOR playerPK_seq,'Patrick','Murray','K',DEFAULT,'TB');
insert into players values (NEXT VALUE FOR playerPK_seq,'Matt','Prater','K',DEFAULT,'DET');
insert into players values (NEXT VALUE FOR playerPK_seq,'Robbie','Gould','K',DEFAULT,'CHI');

-- insert into players values (NEXT VALUE FOR playerPK_seq,'Detroit','Lions','Defense',DEFAULT,'DET');
-- insert into players values (NEXT VALUE FOR playerPK_seq,'Minnesota','Vikings','Defense',DEFAULT,'MIN');
-- insert into players values (NEXT VALUE FOR playerPK_seq,'Green Bay','Packers','Defense',DEFAULT,'GB');
-- insert into players values (NEXT VALUE FOR playerPK_seq,'Tampa Bay','Buccaneers','Defense',DEFAULT,'TB');
-- insert into players values (NEXT VALUE FOR playerPK_seq,'Chicago','Bears','Defense',DEFAULT,'CHI');

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
insert into weeklyTeams values (11, 2, 4);
insert into weeklyTeams values (12, 1, 5);
insert into weeklyTeams values (13, 2, 5);
