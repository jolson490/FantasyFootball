package com.ilmservice.fantasyfootball;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ilmservice.fantasyfootball.db.repositories.FantasyTeamRepository;

@WebServlet("/Fantasy")
public class FantasyFootballServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballServlet.class);

  private static final long serialVersionUID = 1L;

  private FantasyTeamRepository teamRepository;

  @Autowired
  public void setFantasyTeamRepository(FantasyTeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("begin doPost()");

    showData();

    String currentWeek = req.getParameter("currentWeek");
    logger.debug("currentWeek: {}", currentWeek);
    req.getSession().setAttribute("currentWeek", currentWeek);
    getServletContext().getRequestDispatcher("/jsp/HelloWorld.jsp").forward(req, resp);

    logger.debug("end doPost");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("in doGet");
    doPost(req, resp);
  }

  private void showData() {
    logger.debug("begin showData()");

    // TODO

    // (prints 8)
    // long numberTeams = teamRepository.count();
    // logger.debug("number of fantasy teams: {}", numberTeams);

        // (prints 8)
        // logger.debug("number of usernames (fantasy teams): {}",
        // usernames.size());

        // (prints the 8 usernames)
        // usernames.stream().forEach(username -> logger.debug("username: {}",
        // username.toString()));

        logger.debug("end showData");
  }

}
