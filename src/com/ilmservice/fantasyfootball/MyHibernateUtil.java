package com.ilmservice.fantasyfootball;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHibernateUtil {
  private static final Logger logger = LoggerFactory.getLogger(MyHibernateUtil.class);

  private static SessionFactory sessionFactory;

  public static SessionFactory getFactory() {
    logger.debug("begin getFactory()");
    if (sessionFactory == null) {
      initializeSession();
    }

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    // List<Object> tables = session.createQuery("from
    // java.lang.Object").list();
    // (prints 0)
    // System.out.println("Number of tables in db: " + tables.size());
    // for (Object table : tables) {
    // System.out.println("table: " + table.toString());
    // }

    // List<FantasyTeam> teams =
    // session.createCriteria(FantasyTeam.class).list();
    // if (teams != null) {
    // (prints 0)
    // System.out.println("Number of fantasy teams: " + teams.size());
    //
    // for (FantasyTeam team : teams) {
    // System.out.println("team's mascot: " + team.getMascot());
    // }
    // }

    // ("org.hibernate.hql.internal.ast.QuerySyntaxException: fantasyTeams is
    // not mapped [from fantasyTeams]")
    // Query query = session.createQuery("from fantasyTeams");
    // List<?> list = query.list();
    // if (list != null) {
    // System.out.println("Query - Number of fantasy teams: " + list.size());
    // }

    Query myQuery = session.createNativeQuery("SELECT USERNAME FROM fantasyTeams");
    List<Object> usernames = myQuery.getResultList();
    if (usernames != null) {
      // (prints 8)
      logger.debug("number of usernames (fantasy teams): {}", usernames.size());
      for (Object username : usernames) {
        // (prints the 8 usernames)
        logger.debug("username: {}", username.toString());
      }
    }

    // (ERROR 42X01: Syntax error: Encountered "SHOW")
    // System.out.println(session.createSQLQuery("SHOW TABLES").list());

    // (prints addresses of objects)
    // System.out.println("SYS.SYSTABLES: " + session.createSQLQuery("SELECT *
    // FROM SYS.SYSTABLES").list());

    session.close();

    logger.debug("end getFactory - sessionFactory: {}", sessionFactory);
    return sessionFactory;
  }

  private static void initializeSession() {
    logger.debug("begin initializeSession()");

    // Make sure the same derby home is used for MyHibernateUtil as was for
    // PopulateDB - otherwise MyHibernateUtil might not find the database
    // directory that was created by PopulateDB.
    PopulateDB.setDerbyHome();

    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    logger.debug("configuration: {}", configuration);

    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
        .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    logger.debug("end initializeSession");
  }

  public static void main(String[] args) {
    MyHibernateUtil.getFactory();
  }

}
