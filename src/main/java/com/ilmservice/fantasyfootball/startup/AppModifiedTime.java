package com.ilmservice.fantasyfootball.startup;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppModifiedTime implements ApplicationListener<ApplicationReadyEvent> {
  private static final Logger logger = LoggerFactory.getLogger(AppModifiedTime.class);

  // The datetime of when this uber jar file (that this application is running inside of) was last modified.
  private static String dateTimeUpdated = null;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    logger.debug("in onApplicationEvent() - default time zone...: *{}*", java.util.TimeZone.getDefault());
    // The following line of code is equivalent to if the following text had
    // been passed (on the command line) as a System Property to the java
    // command that runs the far file: -Duser.timezone="America/Chicago"
    TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago")); // CST (ILM HQ is in Edina, MN)
    logger.debug("...time zone changed to: *{}*", java.util.TimeZone.getDefault());

    setTime();
  }

  public static String getTime() {
    return dateTimeUpdated;
  }

  private void setTime() {
    logger.debug("in setTime()");

    final String className = getClass().getSimpleName() + ".class";
    logger.debug("className: {}", className);

    final String classPath = (getClass().getResource(className).toString());
    logger.debug("classPath=*{}*", classPath);
    // e.g. values for 'classPath' (spaces added in middle for readability):
    //  * localhost: jar:file:/C:/Users/Admin/git/FantasyFootball/target/ILMServices-FantasyFootball-0.0.1-SNAPSHOT.jar!  /BOOT-INF/classes!/com/ilmservice/fantasyfootball/startup/AppModifiedTime.class
    //  * AWS:       jar:file:/var/app/current/application.jar!                                                           /BOOT-INF/classes!/com/ilmservice/fantasyfootball/startup/AppModifiedTime.class
    //  * Docker:    jar:file:/app.jar!                                                                                   /BOOT-INF/classes!/com/ilmservice/fantasyfootball/startup/AppModifiedTime.class
    // NOTE: This code makes an assumption that the application will be launched via "java -jar <filename>.jar" - e.g. when 
    // running this (on your local machine) via Eclipse (right-click project, Run As, Spring Boot App), then the following (log output) occurs: 
    //   DEBUG [main] AppModifiedTime: classPath=*file:/C:/Users/Admin/git/FantasyFootball/target/classes/com/ilmservice/fantasyfootball/startup/AppModifiedTime.class*
    //   DEBUG [main] AppModifiedTime: jarPath=*f*
    //   ERROR [main] AppModifiedTime: Error getting jar modified time
    //   java.net.MalformedURLException: no protocol: f
    
    String jarPath = null;
    try {
      // "indexOf will return -1 ... if the class is not loaded from the jar file"
      jarPath = classPath.substring(0, classPath.indexOf("!")+2); // need to leave the "!/" at the end
    } catch (IndexOutOfBoundsException except) {
      logger.error("Error getting substring (of fully qualified path to class name)", except);
    }
    logger.debug("jarPath=*{}*", jarPath);

    try {
      final URL url = new URL(jarPath);
      logger.debug("url=*{}*", url);
      final JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
      logger.debug("jarConnection=*{}*", jarConnection);
      final FileTime fileTime = jarConnection.getJarFile().getEntry("META-INF/MANIFEST.MF").getLastModifiedTime();
      logger.debug("fileTime={} fileTime.toMillis()={}", fileTime, fileTime.toMillis());

      final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a z");
      logger.debug("formatter.getTimeZone()=*{}*", formatter.getTimeZone());
      dateTimeUpdated = formatter.format(fileTime.toMillis());
      logger.debug("dateTimeUpdated=*{}*", dateTimeUpdated);
    } catch (MalformedURLException except1) {
      logger.error("Error getting jar modified time", except1);
    } catch (IOException except2) {
      logger.error("Error getting jar modified time", except2);
    }
  } // end setTime
}
