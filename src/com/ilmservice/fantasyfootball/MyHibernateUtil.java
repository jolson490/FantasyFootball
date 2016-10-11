package com.ilmservice.fantasyfootball;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class MyHibernateUtil {
  private static SessionFactory sessionFactory;

  public static SessionFactory getFactory() {
    if (sessionFactory == null) {
      initializeSession();
    }

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    List<FantasyTeam> teams = session.createCriteria(FantasyTeam.class).list();
    if (teams != null) {
      // (prints 0)
      System.out.println("Number of fantasy teams: " + teams.size());

      // System.out.println(teams.get(2).getMascot());
    }

    Query myQuery = session.createNativeQuery("SELECT USERNAME FROM fantasyTeams");
    List<Object> usernames = myQuery.getResultList();
    if (usernames != null) {
      // (prints 8)
      System.out.println("Number of usernames (fantasy teams): " + usernames.size());
      for (Object username : usernames) {
        // (prints the 8 usernames)
        System.out.println("username: " + username.toString());
      }
    }

    session.close();

    return sessionFactory;
  }

  private static void initializeSession() {
    System.out.println("begin initializeSession...");

    // Make sure the same derby home is used for MyHibernateUtil as was for
    // PopulateDB - otherwise MyHibernateUtil might not find the database
    // directory that was created by PopulateDB.
    PopulateDB.setDerbyHome();

    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    System.out.println("Configuration: " + configuration.toString());
    
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
        .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    System.out.println("...end initializeSession");
  }

  public static void main(String[] args) {
    MyHibernateUtil.getFactory();
  }

}
