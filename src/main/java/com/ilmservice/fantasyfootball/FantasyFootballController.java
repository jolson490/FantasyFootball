package com.ilmservice.fantasyfootball;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ilmservice.fantasyfootball.db.repositories.FantasyTeamRepository;
import com.ilmservice.fantasyfootball.model.Week;

@Controller
@EnableAutoConfiguration
public class FantasyFootballController {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballController.class);

  @Autowired
  private FantasyTeamRepository fantasyTeamRepository;

  // @Autowired
  // private PlayerRepository playerRepository;

  // http://localhost:8080/ILMServices-FantasyFootball/
  @RequestMapping("/")
  public String home() {
    logger.debug("in home()");
    // showData();
    return "index";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/showTeams
  // curl -X GET http://localhost:8080/ILMServices-FantasyFootball/showTeams -o ShowTeams.html
  @RequestMapping(value = "/showTeams", method = RequestMethod.GET)
  public String listTeams(Model model) {
    logger.debug("in listTeams()");
    model.addAttribute("teamsAttribute", fantasyTeamRepository.findAll());
    return "ShowTeams";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/jsptest
  @RequestMapping("/jsptest")
  public String jsptest(ModelAndView modelAndView) {
    logger.debug("in jsptest()");
    return "jsp-spring-boot";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/chooseWeek
  @RequestMapping("/chooseWeek")
  public ModelAndView weekForm() {
    logger.debug("in weekForm()");
    ModelAndView mav = new ModelAndView("ChooseWeek");

    Map<Integer, String> weeks = new HashMap<Integer, String>();
    // TO-DO-data-weeks: populate based on weeks stored in db
    weeks.put(1, "Week 1");
    weeks.put(2, "Week 2");
    weeks.put(3, "Week 3");
    weeks.put(4, "Week 4");
    weeks.put(5, "Week 5");
    mav.addObject("weeksMap", weeks);

    mav.addObject("blankWeekModel", new Week());

    return mav;
  }

  // http://localhost:8080/ILMServices-FantasyFootball/showWeek
  // TO-DO-data-weeks-validate: If user puts above URL directly into browser, they get "Selected week: 0" - add validation to prevent/handle invalid values.
  // e.g.: curl -X POST -F 'week=2' http://localhost:8080/ILMServices-FantasyFootball/showWeek
  @RequestMapping("/showWeek")
  public ModelAndView weekSubmit(@ModelAttribute Week chosenWeekModel) {
    logger.debug("in weekSubmit(): chosenWeekModel.getWeek()={}", chosenWeekModel.getWeek());
    // TO-DO-data-weeklyTeams get data from db for chosenWeekModel, and have the view display/print it to browser.
    ModelAndView mav = new ModelAndView("ShowWeek");
    mav.addObject("chosenWeekModel", chosenWeekModel);
    return mav;
  }

  //@formatter:off
  /*
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
   */
  //@formatter:on

}
