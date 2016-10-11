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

// Note that Apache Derby is a relational database - more info can be found at: https://db.apache.org/derby/ 

// NOTE THAT YOU DO NOT NEED TO MODIFY THIS CODE FILE FOR THE CODE AUDITION. 
// But you do need to run the "main" method in this class to create & populate the "nflDB" Java/Derby database.

public class PopulateDB {

	// Note that 'ij' is an interactive SQL scripting tool that comes with Derby
	// - more info can be found at:
	// http://db.apache.org/derby/papers/DerbyTut/ij_intro.html
	// (And note that Derby is included in the JDK.)

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

  // (Note that https://db.apache.org/derby/integrate/derby_plugin_info.html
  // says between Derby versions "10.3 and 10.8.2" that Derby distributions
  // included plugins for Eclipse.)

	private static Connection conn = null;
	private static Statement stmt = null;

  // The default value of the "derby.system.home" System Property is the
  // current project directory (i.e. the "user.dir" Property).
  public static void setDerbyHome() {
    FileInputStream fis = getFISInClassPath("javaProperties.xml");
    java.util.Properties props = new Properties();
    try {
      props.loadFromXML(fis);
    } catch (InvalidPropertiesFormatException e1) {
      e1.printStackTrace();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    System.out.println("derby.system.home (from properties file): " + props.getProperty("derby.system.home"));

    Properties p = System.getProperties();
    System.out.println("derby.system.home from System.Property - before: " + System.getProperty("derby.system.home"));
    p.setProperty("derby.system.home", props.getProperty("derby.system.home"));
    System.out.println("derby.system.home from System.Property - after: " + System.getProperty("derby.system.home"));
  }

  public static void main(String[] args) {
    System.out.println("begin PopulateDB main");

		createConnection();

		runMultiLineSqlCommands();

		try {
			readAndExecuteFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

    System.out.println("end PopulateDB main - SUCCESSFULLY COMPLETED PROCESSING (unless any errors noted above)");
	}

	private static void createConnection() {
    setDerbyHome();

		try {
			// Get a connection. (Regarding "create=true", per the Derby
			// Reference Manual, if the specified db already exists, a
			// SQLWarning is issued.)
      final String dbURL = "jdbc:derby:nflDB;create=true;";
			conn = DriverManager.getConnection(dbURL);

      // (Ideally we should check for more than 1 warning & print them all.)
      // e.g. of warning that can occur: PopulateDB assumes the database doesn't
      // already exist - so check if user tried to run PopulateDB when database
      // already exists (don't let the user run PopulateDB if the db already
      // exists).
      SQLWarning connectionWarning = conn.getWarnings();
			if (connectionWarning != null) {
				System.err.println("createConnection: terminating due to SQLWarning: " + connectionWarning);
				System.exit(1);
			}
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	private static void runMultiLineSqlCommands() {
		System.out.println("begin PopulateDB runMultiLineSqlCommands");
		try {
			stmt = conn.createStatement();
			stmt.execute("CREATE TABLE players (" + "ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ "fname VARCHAR(50) NOT NULL," + "lname VARCHAR(50) NOT NULL,"
					+ "position VARCHAR(10) NOT NULL," + "positionRanking INT NOT NULL  CHECK (positionRanking >= 0),"
					+ "nflTeam VARCHAR(50) NOT NULL," + "PRIMARY KEY (ID)" + ")");
			stmt.close();

			stmt = conn.createStatement();
			stmt.execute("CREATE TABLE fantasyTeams (" + "ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ "username VARCHAR(50) NOT NULL ,"
					+ "fantasyMascot VARCHAR(100) NOT NULL," + "PRIMARY KEY (ID)" + ")");
			stmt.close();

			stmt = conn.createStatement();
			stmt.execute("CREATE TABLE weeklyTeams (" + "playerID int NOT NULL,"
					+ "fantasyTeamID INT NOT NULL,"
					+ "week INT NOT NULL  CHECK (week >= 1 AND week <= 5),"
					+ "PRIMARY KEY (playerID, fantasyTeamID, week),"
					+ "FOREIGN KEY (playerID) REFERENCES players(ID),"
					+ "FOREIGN KEY (fantasyTeamID) REFERENCES fantasyTeams(ID)" + ")");
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		System.out.println("end PopulateDB runMultiLineSqlCommands");
	}

  public static FileInputStream getFISInClassPath(String filename) {
    // Note that the .classpath file (in this eclipse project) is set up
    // such that the "inputFiles" folder is on the classpath (this is needed
    // so that when you run this program, the following txt file will be
    // found).
    URL url = PopulateDB.class.getClassLoader().getResource(filename);
    System.out.println("getFISInClassPath: url=" + url.getPath());

    URI uri = null;
    try {
      uri = url.toURI();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    // System.out.println("getFISInClassPath: URI is: " + uri.toString());

    FileInputStream fis = null;
    try {
      fis = new FileInputStream(new File(uri));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return fis;
  }

	private static void readAndExecuteFile() throws IOException {
		System.out.println("begin PopulateDB readAndExecuteFile");

    FileInputStream fis = getFISInClassPath("nflSqlCommands.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println("readAndExecuteFile: line=" + line);
			if (line != null && !line.equals("")) {
				try {
					stmt = conn.createStatement();
					stmt.execute(line);
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		br.close();
		System.out.println("end PopulateDB readAndExecuteFile");
	}

}
