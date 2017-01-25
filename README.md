# FantasyFootball

## To try out this application in AWS (without setting it up on your own computer)

**I have an instance of this application running at: http://aws.ilmservice.com/poc/fantasy/ (please feel free to play around with it and check it out!)**

## Summary

This is a (relatively simple) Java EE web application, built with the Spring platform (e.g. Spring Boot and Spring MVC). 

I created this application to experiment with and learn a bunch of new technologies.

## Tech Stack

Three-tier architecture:
 * User Interface: JSP (JSTL & Jasper), HTML, and CSS.
 * Middle Tier: Java 8. Spring Data JPA (and Hibernate).
 * Back End: SQL database.

Tools: Maven, Tomcat. AWS & Jenkins. (And Eclipse and Git.)

## CI/CD

Via AWS CodePipeline, the following is setup/integrated (see [this AWS CI/CD blog] for more info):
 * this GitHub repository is polled every minute (for each pushed commit, a build is automatically kicked off)...
 * ...Jenkins (a CI server - running in a stand-alone AWS EC2 instance) does a build (via Maven)
 * ...Elastic Beanstalk (also has an EC2 instance - for each successful build, the updated application gets deployed to this instance)

## Current State

This is not a fully-fledged fantasy football application that is ready to compete with [ESPN's Fantasy Football](http://www.espn.com/fantasy/football/) ![smiley face](http://emojipedia-us.s3.amazonaws.com/cache/a7/0e/a70ed4d5e4062aab6c4ac24892ee6763.png) 


<img src="http://emojipedia-us.s3.amazonaws.com/cache/a7/0e/a70ed4d5e4062aab6c4ac24892ee6763.png" alt="Smiley face" height="16" width="16">


Pages:
 * "Choose Week" - just a simple proof-of-concept for a Spring MVC drop-down menu. (The idea is to eventually add functionality to allow a given user to specify which players they want to play (vs have on their bench) on their fantasy team for a given week of the season.)
 * "Show Fantasy Teams" - simply shows the pre-populated teams from the database. (Currently there is no way to create/edit/delete fantasy teams/users - nor is there any login/authentication.)
 * "NFL Players" - by far the most interesting of the 3 pages. Allows you to create/edit/delete NFL players in the database.
    * The nflRanking of each player is based on their 2014 fantasy points - e.g. the player with a value of 1 for this field had the highest total points, 2 had the 2nd highest, etc. And whenever the user creates/edits/deletes a player, the application updates the value of this field across all players accordingly (to ensure it is sort-ordered - the best player has a value of 1, and subsequent values increase by 1 per player). Currently there is no display of ranking within a given position - the ranking is across all players.

Info about data:
* For simplicity, I used only the 5 teams that were part of the <a target="_blank" href="https://en.wikipedia.org/wiki/NFL_Central_Division">NFC Central Division</a>. (Note that in 2002 the division was re-named to the NFC North.)
* And for the NFL players' data, I used a copy of http://www.scoresheet.com/FB_2015_num.xls (from http://www.scoresheet.com/FB_map.php) and then I modified the data from that spreadsheet to create the SQL commands in data.sql - to summarize the player data:
    * the players who played in 2015, 
    * and their total fantasy points from 2014.

## Future State (To-do List)

Change the...:
 * ...user interface code from JSP to something else - perhaps AngularJS or Thymeleaf.
 * ...controller code to REST services (with JSON).

Add automated tests. (And make sure deployment doesn't happen when a test fails.)

Instead of a static copy of NFL player data (from scoresheet.com), consider utilizing a public API (e.g. http://api.fantasy.nfl.com/) so this application always has the latest player data.

## Reference Info

This FantasyFootball application is hosted in the following repository: https://github.com/ILMServices/FantasyFootball

(More info about ILM can be found at: http://ilmservice.com)

NOTE: The rest of this README is info for any developer interested in setting up & experimenting with this application - e.g. 
* with your own AWS account, 
* or on your own machine (e.g. to make code changes).

## Developer - cloud, without CI/CD (to run this application with your own AWS account): 

(Note that this section doesn't involve CI/CD - this section describes how to manually get a static/uploaded copy of the application running in Elastic Beanstalk, without CodePipeline.)

Do a git clone of this repository: 
* git clone https://github.com/ILMServices/FantasyFootball

Build the application:
* cd FantasyFootball
* mvn package # creates ./target/ILMServices-FantasyFootball-0.0.1-SNAPSHOT.jar

And then in AWS...

...in RDS: I created a MySQL database, and then I did the following to populate it with data:
* cd FantasyFootball/src/main/resources/
* mysql -h \<endpoint\>.amazonaws.com -P \<port\> -u \<username\> -p <schema.sql
* mysql -h \<endpoint\>.amazonaws.com -P \<port\> -u \<username\> -p <data.sql

...in Elastic Beanstalk: I created an application environment:
* I specified the Java 8 platform (on Linux)
* Upload the uber jar (that you built with mvn)
* I modified the configuration environment variables:
    * RDS\_* - point to my RDS database
    * SERVER\_PORT - set to 5000
    * SPRING\_DATASOURCE\_* - override a few properties from the application.properties in this repository

## Developer - localhost: Info about this FantasyFootball eclipse project

Note that this project was created:
 - on a machine running Windows
 - with the JAVA\_HOME environment variable set to: "C:\Program Files\Java\jdk1.8.0_65"

## Developer - localhost: Pre-requisites (to setup & run this application on your own machine)

Before you run this web application, you need to complete these pre-requisites.

Have the following installed on your machine: 
* JDK version 8.
* A SQL database server.
* eclipse, including the following plugins:
    * version 1.7.0.2 or later of: "Maven Integration for Eclipse" (AKA org.eclipse.m2e.core - from http://download.eclipse.org/technology/m2e/releases).
    * version 0.17.2 or later of: "m2e connector for the mavenarchiver and pom properties" (AKA org.sonatype.m2e.mavenarchiver - from [this Maven repo](http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-mavenarchiver/0.17.2/N/LATEST/)).

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

For all remaining steps, you need to have the SQL database server started.

NOTE: to start the application (i.e. an uber jar - that includes Tomcat embedded): in eclipse, right-click this project, Run As, Spring Boot App.

The first time you are going to run the application:
 * In application.properties change spring.datasource.initialize from false to true (so the database gets initialized/populated).
 * Then start the application.
 * (And then once the application initializes, terminate it and undo the change you made to application.properties.)

## Developer - localhost: To Run this application

Once you have completed the pre-requisites, then start the application.
Then you can access the application via your browser at: http://localhost:8080/ILMServices-FantasyFootball/

[//]:

[this AWS CI/CD blog]: <https://aws.amazon.com/blogs/devops/building-continuous-deployment-on-aws-with-aws-codepipeline-jenkins-and-aws-elastic-beanstalk/>
