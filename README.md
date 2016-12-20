# FantasyFootball

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
* a web server (e.g. Tomcat).
* eclipse, including the following plugins:
    * version 1.7.0.2 or later of: "Maven Integration for Eclipse" (AKA org.eclipse.m2e.core - from http://download.eclipse.org/technology/m2e/releases).
    * version 0.17.2 or later of: "m2e connector for the mavenarchiver and pom properties" (AKA org.sonatype.m2e.mavenarchiver - from http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-mavenarchiver/0.17.2/N/LATEST/).

Setup in Eclipse:
* Window, Preferences, Java, Installed JREs
    * Remove any existing JRE.
    * Add jdk1.8.* - specify the value of JAVA\_HOME (e.g. C:\Program Files\Java\jdk1.8.0_101). Check the box next to this jdk, click Apply.
* Tomcat setup:
    * Window, Preferences, Server, Runtime Environment, "Apache\_Tomcat\_v*.0", for the "Tomcat installation directory" specify the value of CATALINA_HOME (e.g. "C:\apache-tomcat").
    * Java EE perspective, Servers, click "No servers are available. Click this link to create a new server...", Server name "Apache\_Tomcat\_v*.0\_at\_localhost". 
* Git stuff:
    * Window, Preferences, Team, Git, Configuration, create/specify the "user.email" and "user.name" Git settings in eclipse. (Reference info: https://wiki.eclipse.org/EGit/User_Guide)
    * Git Perspective, "Clone a Git repository", enter https://github.com/ILMServices/FantasyFootball.git as URI.
    * Git Repositories, right-click FantasyFootball, Import Projects, "Import existing Eclipse projects".
* One more Tomcat thing: Java EE perspective, Servers, double-click "Apache\_Tomcat\_v*.0\_at\_localhost", Modules, Add Web Module, click FantasyFootball, Ctrl+S (to save changes).
* In application.properties, set the value of ff.database.location.
* maven clean install. 
* right-click this FantasyFootball project, Maven, Update Project.

For all remaining steps, you need to have the derby database network server started - which is done via:
* $ cd $DERBY_HOME/bin
* $ ./startNetworkServer

NOTE: to start the application: in eclipse, in the Servers View, click on "Apache\_Tomcat\_v*.0\_at\_localhost", and click the green play button ("Start the server").

The first time you are going to run the application:
 * In application.properties change spring.datasource.initialize from false to true (so the database gets initialized/populated).
 * Then start the application.
 * (And then once the application initializes, terminate it and undo the change you made to application.properties.)

## To Run this application

Once you have completed the pre-requisites, then start the application.
Then you can access the application via your browser at: http://localhost:8080/ILMServices-FantasyFootball/
