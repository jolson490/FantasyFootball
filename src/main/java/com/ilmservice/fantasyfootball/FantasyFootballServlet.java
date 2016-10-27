package com.ilmservice.fantasyfootball;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilmservice.fantasyfootball.db.MyHibernateUtil;

@WebServlet("/Fantasy")
public class FantasyFootballServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballServlet.class);

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("begin doPost()");
    String currentWeek = req.getParameter("currentWeek");
    logger.debug("currentWeek: {}", currentWeek);
    MyHibernateUtil.createFactory();
    req.getSession().setAttribute("currentWeek", currentWeek);
    getServletContext().getRequestDispatcher("/jsp/HelloWorld.jsp").forward(req, resp);
    logger.debug("end doPost");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("in doGet");
    doPost(req, resp);
  }

}
