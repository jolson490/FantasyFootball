package com.ilmservice.fantasyfootball;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ilmservice.fantasyfootball.db.entities.FantasyTeam;
import com.ilmservice.fantasyfootball.db.repositories.FantasyTeamRepository;
import com.ilmservice.fantasyfootball.model.Week;

@Controller
@EnableAutoConfiguration
public class FantasyFootballController {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballController.class);

  @Autowired
  private FantasyTeamRepository fantasyTeamRepository;

  // http://localhost:8080/ILMServices-FantasyFootball/ -> index.jsp
  @RequestMapping("/")
  public String home() {
    logger.debug("in home()");
    showData();
    return "index";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/jsptest -> jsp-spring-boot.jsp
  @RequestMapping("/jsptest")
  public String jsptest(ModelAndView modelAndView) {
    logger.debug("in jsptest()");
    return "jsp-spring-boot";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/chooseWeek -> ChooseWeek.jsp
  @RequestMapping("/chooseWeek")
  public ModelAndView weekForm() {
    logger.debug("in weekForm()");
    ModelAndView mav = new ModelAndView("ChooseWeek");

    Map<Integer, String> weeks = new HashMap<Integer, String>();
    // TODO-data-weeks: populate based on weeks stored in db
    weeks.put(1, "Week 1");
    weeks.put(2, "Week 2");
    weeks.put(3, "Week 3");
    weeks.put(4, "Week 4");
    weeks.put(5, "Week 5");
    mav.addObject("weeksMap", weeks);

    mav.addObject("weekModel", new Week());

    return mav;
  }

  // http://localhost:8080/ILMServices-FantasyFootball/showWeek -> ShowWeek.jsp
  // TODO-data-weeks-validate: If user puts above URL directly into browser, they get "Selected week: 0" - add validation to prevent/handle invalid values.
  @RequestMapping("/showWeek")
  public ModelAndView weekSubmit(@ModelAttribute Week weekModel) {
    logger.debug("in weekSubmit(): weekModel.getWeek()={}", weekModel.getWeek());
    // TODO-data-weeklyTeams get data from db for weekModel, and have the view display/print it to browser.
    ModelAndView mav = new ModelAndView("ShowWeek");
    mav.addObject("weekModel", weekModel);
    return mav;
  }

  private void showData() {
    logger.debug("begin showData(): fantasyTeamRepository={}", fantasyTeamRepository);

    // (prints 8)
    long numberTeams = fantasyTeamRepository.count();
    logger.debug("number of fantasy teams: {}", numberTeams);

    // (prints all of the fields/columns for each of the 8 fantasy teams)
    List<FantasyTeam> fantasyTeams = (List<FantasyTeam>)fantasyTeamRepository.findAll();
    fantasyTeams.stream().forEach(fantasyTeam -> logger.debug("fantasyTeam: {}", fantasyTeam.toString()));

    logger.debug("end showData");
  }

}
