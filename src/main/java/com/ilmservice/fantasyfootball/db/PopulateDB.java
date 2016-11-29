package com.ilmservice.fantasyfootball.db;

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
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;

// Note that Apache Derby is a relational database - more info can be found at: https://db.apache.org/derby/ 

// NOTE: run the "main" method in this class via eclipse: right-click PopulateDB.java, Run As, Spring Boot App.

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

  private Connection conn = null;
  private Statement stmt = null;

  @Value("${ff.derby.system.home}")
  private String derbySystemHome;

  // This method allows the value of the "derby.system.home" System Property to
  // be changed.
  // (The default value of this System Property is the current project directory
  // - i.e. the "user.dir" Property).
  public void setDerbyHome() {
    logger.debug("begin setDerbyHome()");

    Properties systemProps = System.getProperties();
    logger.trace("derby.system.home from System.Property - before setting from properties file: {}",
        System.getProperty("derby.system.home"));
    systemProps.setProperty("derby.system.home", derbySystemHome);
    logger.debug("derby.system.home from System.Property - after setting from properties file: {}",
        System.getProperty("derby.system.home"));

    logger.debug("end setDerbyHome");
  }

  public static void main(String[] args) {
    new SpringApplicationBuilder(PopulateDB.class).web(false).run(args);
  }

  // creates & populate the "nflDB" Java/Derby database
  @PostConstruct
  public void populateDatabase() {
    logger.debug("begin populateDatabase()");
    createConnection();
    runMultiLineSqlCommands();
    readAndExecuteFile();
    logger.info("end populateDatabase - SUCCESSFULLY COMPLETED PROCESSING (unless any errors noted above)");
  }

  private void createConnection() {
    logger.debug("begin createConnection()");

    setDerbyHome();

    try {
      // (Regarding "create=true", per the Derby Reference Manual, if the
      // specified db already exists, a SQLWarning is issued.)
      final String dbURL = "jdbc:derby:nflDB;create=true;";
      conn = DriverManager.getConnection(dbURL);

      handleWarnings();
    } catch (SQLException except) {
      logger.error("Error connecting to database", except);
      System.exit(1);
    }

    logger.debug("end createConnection");
  }

  private void handleWarnings() throws SQLException {
    logger.debug("begin handleWarnings()");
    // Consider any SQLWarning to be an unexpected error for which to terminate
    // this application.
    // Here's an e.g. of a warning that can occur: this PopulateDB class assumes
    // the database doesn't already exist - so here check for if the user tried
    // to run PopulateDB when the db already exists (don't let the user run
    // PopulateDB if the db already exists).
    if (conn != null) {
      boolean doTerminate = false;

      SQLWarning warningToLog = conn.getWarnings();
      while (warningToLog != null) {
        doTerminate = true;

        logger.error("SQLWarning: SQL state '" + warningToLog.getSQLState() + "', error code '"
            + warningToLog.getErrorCode() + "', message [" + warningToLog.getMessage() + "]");

        warningToLog = warningToLog.getNextWarning();
      }

      if (doTerminate) {
        logger.error("terminating due to SQLWarning(s) that occurred.");
        System.exit(1);
      }
    }
    logger.debug("end handleWarnings");
  }

  private void runMultiLineSqlCommands() {
    logger.debug("begin runMultiLineSqlCommands()");
    try {
      stmt = conn.createStatement();
      stmt.execute(
          "CREATE TABLE players (" 
              + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
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
              + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
              + "username VARCHAR(50) NOT NULL," 
              + "fantasyMascot VARCHAR(100) NOT NULL," 
              + "PRIMARY KEY (Id)" + ")");
      stmt.close();

      stmt = conn.createStatement();
      stmt.execute("CREATE TABLE weeklyTeams (" 
          + "playerID INT NOT NULL," 
          + "fantasyTeamId INT NOT NULL,"
          + "week INT NOT NULL  CHECK (week >= 1 AND week <= 5)," 
          + "PRIMARY KEY (playerID, fantasyTeamId, week),"
          + "FOREIGN KEY (playerID) REFERENCES players(ID)," 
          + "FOREIGN KEY (fantasyTeamId) REFERENCES fantasyTeams(Id)" + ")");
      stmt.close();
    } catch (SQLException sqlExcept) {
      logger.error("Error running SQL commands", sqlExcept);
      System.exit(1);
    }
    logger.debug("end runMultiLineSqlCommands");
  }

  public FileInputStream getFISInClassPath(String filename) {
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

  private void readAndExecuteFile() {
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
