# FantasyFootball

## Current State

This is not a fully fledged fantasy football application that is ready to compete with http://www.espn.com/fantasy/football/ :).
But this is a good example of a (relatively simple) Java EE web application, using: Spring (Boot & MVC & JPA), a SQL database, JSP, and a little CSS.

Pages:
 * "Choose Week" - just a simple proof-of-concept for a Spring MVC drop-down menu. (The idea is to eventually add functionality to allow a given user to specify which players they want to play (vs have on their bench) on their fantasy team for a given week of the season.)
 * "Show Fantasy Teams" - simply shows the pre-populated teams from the database. (Currently there is no way to create/edit/delete fantasy teams/users - nor is their any login/authentication.)
 * "NFL Players" - by far the most interesting of the 3 pages. Allows you to create/edit/delete NFL players in the database.
    * The nflRanking of each player is based on their 2014 fantasy points - e.g. the player with a value of 1 for this field had the highest total points, 2 had the 2nd highest, etc. And whenever the user creates/edits/deletes a player, the application updates the value of this field across all players accordingly (to ensure it is sort-ordered - the best player has a value of 1, and subsequent values increase by 1 per player). Currently there is no display of ranking within a given position - the ranking is across all players.

Info about data:
* For simplicity, I used only the 5 teams that were part of the NFC Central Division (https://en.wikipedia.org/wiki/NFL_Central_Division). (Note that in 2002 the division was re-named to the NFC North.)
* And for the NFL players data, I used a copy of http://www.scoresheet.com/FB_2015_num.xls (from http://www.scoresheet.com/FB_map.php) and then I modified the data from that spreadsheet to create the SQL commands in data.sql - to summarize the player data:
    * the players who played in 2015, 
    * and their total fantasy points from 2014.

## Reference Info

This FantasyFootball application is hosted in the following repository: https://github.com/ILMServices/FantasyFootball
More info about ILM can be found at: http://ilmservice.com

## Info about this FantasyFootball eclipse project

Note that this project was created:
 - on a machine running Windows
 - with the JAVA\_HOME environment variable set to: "C:\Program Files\Java\jdk1.8.0_65"
 - with the following in the PATH environment variable: "%JAVA\_HOME%\bin;%JAVA\_HOME%\db\bin;"

## Pre-requisites

Before you run this web application, you need to complete these pre-requisites.

Have the following installed on your machine: 
* JDK version 8.
* eclipse, including the following plugins:
    * version 1.7.0.2 or later of: "Maven Integration for Eclipse" (AKA org.eclipse.m2e.core - from http://download.eclipse.org/technology/m2e/releases).
    * version 0.17.2 or later of: "m2e connector for the mavenarchiver and pom properties" (AKA org.sonatype.m2e.mavenarchiver - from http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-mavenarchiver/0.17.2/N/LATEST/).

Setup in Eclipse:
* Window, Preferences, Java, Installed JREs
    * Remove any existing JRE.
    * Add jdk1.8.* - specify the value of JAVA\_HOME (e.g. C:\Program Files\Java\jdk1.8.0_101). Check the box next to this jdk, click Apply.
* Git stuff:
    * Window, Preferences, Team, Git, Configuration, create/specify the "user.email" and "user.name" Git settings in eclipse. (Reference info: https://wiki.eclipse.org/EGit/User_Guide)
    * Git Perspective, "Clone a Git repository", enter https://github.com/ILMServices/FantasyFootball.git as URI.
    * Git Repositories, right-click FantasyFootball, Import Projects, "Import existing Eclipse projects".
* In application.properties, set the value of ff.database.location.
* maven clean install. 
* right-click this FantasyFootball project, Maven, Update Project.

For all remaining steps, you need to have the derby database network server started - which is done via:
* $ cd $DERBY_HOME/bin
* $ ./startNetworkServer

NOTE: to start the application (i.e. an uber jar - that includes Tomcat embedded): in eclipse, right-click this project, Run As, Spring Boot App.

The first time you are going to run the application:
 * In application.properties change spring.datasource.initialize from false to true (so the database gets initialized/populated).
 * Then start the application.
 * (And then once the application initializes, terminate it and undo the change you made to application.properties.)

## To Run this application

Once you have completed the pre-requisites, then start the application.
Then you can access the application via your browser at: http://localhost:8080/ILMServices-FantasyFootball/
