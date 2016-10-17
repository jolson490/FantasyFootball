package com.ilmservice.fantasyfootball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Note that Apache Derby is a relational database - more info can be found at: https://db.apache.org/derby/ 

// NOTE: run the "main" method in this class to create & populate the "nflDB" Java/Derby database.

public class PopulateDB {
  private static final Logger logger = LoggerFactory.getLogger(PopulateDB.class);

  // Note that 'ij' is an interactive SQL scripting tool that comes with Derby
  // - more info can be found at:
  // http://db.apache.org/derby/papers/DerbyTut/ij_intro.html
  // (And note that Derby is included in the JDK.)
  //
  // OPTIONAL PREREQUISITE (in order to run 'ij' from Cygwin command line):
  // * Make sure you include %JAVA_HOME%\db\bin in your PATH; so that 'ij' is
  // found:
  // Joshua@JoshuaOlson /cygdrive/c/Users/Joshua/git/FantasyFootball
  // $ which ij
  // /cygdrive/c/Program Files/Java/jdk1.8.0_65/db/bin/ij
  //
  // e.g. of how to run 'ij' from a Cygwin terminal (the following 'connect'
  // command assumes the 'nflDB' folder/database exists within the current
  // directory):
  // Joshua@JoshuaOlson /cygdrive/c/Users/Joshua/git/FantasyFootball
  // $ ij
  // ij version 10.11
  // ij> connect 'jdbc:derby:nflDB';
  //
  // (Note that https://db.apache.org/derby/integrate/derby_plugin_info.html
  // says between Derby versions "10.3 and 10.8.2" that Derby distributions
  // included plugins for Eclipse.)

  private static Connection conn = null;
  private static Statement stmt = null;

  // This method allows the value of the "derby.system.home" System Property to
  // be changed.
  // (The default value of this System Property is the current project directory
  // - i.e. the "user.dir" Property).
  public static void setDerbyHome() {
    logger.debug("begin setDerbyHome()");

    FileInputStream fis = getFISInClassPath("javaProperties.xml");
    java.util.Properties fileProps = new Properties();
    try {
      fileProps.loadFromXML(fis);
    } catch (InvalidPropertiesFormatException e1) {
      logger.error("Error reading properties file", e1);
      System.exit(1);
    } catch (IOException e1) {
      logger.error("Error reading properties file", e1);
      System.exit(1);
    }
    logger.trace("derby.system.home (from properties file): {}", fileProps.getProperty("derby.system.home"));

    Properties systemProps = System.getProperties();
    logger.trace("derby.system.home from System.Property - before setting from properties file: {}",
        System.getProperty("derby.system.home"));
    systemProps.setProperty("derby.system.home", fileProps.getProperty("derby.system.home"));
    logger.debug("derby.system.home from System.Property - after setting from properties file: {}",
        System.getProperty("derby.system.home"));

    logger.debug("end setDerbyHome");
  }

  public static void main(String[] args) {
    logger.info("begin main()");
    createConnection();
    runMultiLineSqlCommands();
    readAndExecuteFile();
    logger.info("end main - SUCCESSFULLY COMPLETED PROCESSING (unless any errors noted above)");
    System.exit(0);
  }

  private static void createConnection() {
    logger.debug("begin createConnection()");

    setDerbyHome();

    SQLWarning connectionWarning = null;
    try {
      // Get a connection. (Regarding "create=true", per the Derby
      // Reference Manual, if the specified db already exists, a
      // SQLWarning is issued.)
      final String dbURL = "jdbc:derby:nflDB;create=true;";
      conn = DriverManager.getConnection(dbURL);

      connectionWarning = conn.getWarnings();

    } catch (SQLException except) {
      logger.error("Error connecting to database", except);
      System.exit(1);
    }

    // (Note that ideally here a check for more than 1 warning should be made, &
    // print them all.)
    // Here's an e.g. of a warning that can occur: this PopulateDB class assumes
    // the database doesn't already exist - so here check for if the user tried
    // to run PopulateDB when the db already exists (don't let the user run
    // PopulateDB if the db already exists).
    if (connectionWarning != null) {
      System.err.println("terminating due to SQLWarning: " + connectionWarning);
      System.exit(1);
    }

    logger.debug("end createConnection");
  }

  private static void runMultiLineSqlCommands() {
    logger.debug("begin runMultiLineSqlCommands()");
    try {
      stmt = conn.createStatement();
      stmt.execute(
          "CREATE TABLE players (" 
              + "ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
              + "fname VARCHAR(50) NOT NULL," 
              + "lname VARCHAR(50) NOT NULL," 
              + "position VARCHAR(10) NOT NULL,"
              + "positionRanking INT NOT NULL CHECK (positionRanking >= 0)," 
              + "nflTeam VARCHAR(50) NOT NULL,"
              + "PRIMARY KEY (ID)" + ")");
      stmt.close();

      stmt = conn.createStatement();
      stmt.execute(
          "CREATE TABLE fantasyTeams (" 
              + "ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
              + "username VARCHAR(50) NOT NULL," 
              + "fantasyMascot VARCHAR(100) NOT NULL," 
              + "PRIMARY KEY (ID)" + ")");
      stmt.close();

      stmt = conn.createStatement();
      stmt.execute("CREATE TABLE weeklyTeams (" 
          + "playerID int NOT NULL," 
          + "fantasyTeamID INT NOT NULL,"
          + "week INT NOT NULL  CHECK (week >= 1 AND week <= 5)," 
          + "PRIMARY KEY (playerID, fantasyTeamID, week),"
          + "FOREIGN KEY (playerID) REFERENCES players(ID)," 
          + "FOREIGN KEY (fantasyTeamID) REFERENCES fantasyTeams(ID)" + ")");
      stmt.close();
    } catch (SQLException sqlExcept) {
      logger.error("Error running SQL commands", sqlExcept);
      System.exit(1);
    }
    logger.debug("end runMultiLineSqlCommands");
  }

  public static FileInputStream getFISInClassPath(String filename) {
    logger.debug("begin getFISInClassPath(filename={})", filename);

    // Note that Maven by default includes files in src/main/resources into the
    // classpath.
    URL url = PopulateDB.class.getClassLoader().getResource(filename);
    logger.trace("url: {}", url.getPath());

    FileInputStream fis = null;
    try {
      URI uri = url.toURI();
      logger.debug("URI is: {}", uri.toString());

      fis = new FileInputStream(new File(uri));
    } catch (URISyntaxException | FileNotFoundException e) {
      logger.error("Error with FileInputStream", e);
      System.exit(1);
    }

    logger.debug("end getFISInClassPath - FileInputStream: {}", fis);
    return fis;
  }

  private static void readAndExecuteFile() {
    logger.debug("begin readAndExecuteFile()");

    FileInputStream fis = getFISInClassPath("nflSqlCommands.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
    String line = null;
    try {
      while ((line = br.readLine()) != null) {
        logger.trace("line: {}", line);
        if (line != null && !line.isEmpty()) {
          try {
            stmt = conn.createStatement();
            stmt.execute(line);
            stmt.close();
          } catch (SQLException e) {
            logger.error("Error running SQL commands", e);
            System.exit(1);
          }
        }
      }
      br.close();
    } catch (IOException e) {
      logger.error("Error reading file", e);
      System.exit(1);
    }

    logger.debug("end readAndExecuteFile");
  }

} // end class PopulateDB
