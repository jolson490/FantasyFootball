package com.ilmservice.fantasyfootball;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/Fantasy")
public class FantasyFootballServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballServlet.class);

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("begin doPost()");
    String currentWeek = req.getParameter("currentWeek");
    logger.debug("currentWeek: {}", currentWeek);
    MyHibernateUtil.getFactory();
    // SessionFactory sessionFactory = null;
    // configuration.configure("hibernate.cfg.xml");
    // ServiceRegistry serviceRegistry = new
    // StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    // sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    // System.out.println(configuration);
    req.getSession().setAttribute("currentWeek", currentWeek);
    getServletContext().getRequestDispatcher("/HelloWorld.jsp").forward(req, resp);
    logger.debug("end doPost");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("in doGet");

    // SessionFactory factory = MyHibernateUtil.getFactory();
    // System.out.println("Made it factory");
    // Session session = factory.getCurrentSession();
    // session.beginTransaction();
    // List<FantasyTeam> players;
    // players = session.createCriteria(FantasyTeam.class).list();
    // if(players != null){
    // System.out.println("Here are my players: " + players.size());
    // System.out.println(players.get(2).getMascot());
    // req.getSession().setAttribute("players", players);
    // }
    // session.close();
    doPost(req, resp);
  }

}
