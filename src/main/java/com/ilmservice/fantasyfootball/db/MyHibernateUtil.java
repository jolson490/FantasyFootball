package com.ilmservice.fantasyfootball.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilmservice.fantasyfootball.db.entities.FantasyTeam;

// TODO delete this class - or re-factor & move to test

public class MyHibernateUtil {
  private static final Logger logger = LoggerFactory.getLogger(MyHibernateUtil.class);

  private static javax.persistence.EntityManagerFactory sessionFactory;

  public static void createFactory() {
    logger.debug("begin createFactory()");
    if (sessionFactory == null) {
      initializeSession();
    }

    showData();

    logger.debug("end createFactory - sessionFactory: {}", sessionFactory);
  }

  private static void showData() {
    logger.debug("begin showData()");
    EntityManager entityManager = sessionFactory.createEntityManager();
    entityManager.getTransaction().begin();

    List<FantasyTeam> teams = entityManager.createQuery("from FantasyTeam", FantasyTeam.class).getResultList();
    if (teams != null) {
      // (prints 8)
      logger.debug("number of fantasy teams: {}", teams.size());
    }

    List<String> usernames = entityManager.createQuery("SELECT username FROM FantasyTeam", String.class)
        .getResultList();
    if (usernames != null) {
      // (prints 8)
      logger.debug("number of usernames (fantasy teams): {}", usernames.size());

      // (prints the 8 usernames)
      usernames.stream().forEach(username -> logger.debug("username: {}", username.toString()));
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.debug("end showData");
  }

  private static void initializeSession() {
    logger.debug("begin initializeSession()");

    // Make sure the same derby home is used for MyHibernateUtil as was for
    // PopulateDB - otherwise MyHibernateUtil might not find the database
    // directory that was created by PopulateDB.
    // PopulateDB.setDerbyHome();

    // T-skip (note that persistence.xml no longer exists)
    sessionFactory = Persistence.createEntityManagerFactory("FantasyFootballUnit");

    logger.debug("end initializeSession");
  }

  public static void main(String[] args) {
    MyHibernateUtil.createFactory();
  }

}
