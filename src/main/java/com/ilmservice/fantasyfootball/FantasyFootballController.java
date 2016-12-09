package com.ilmservice.fantasyfootball;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ilmservice.fantasyfootball.db.repositories.FantasyTeamRepository;
import com.ilmservice.fantasyfootball.model.WeekForm;

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
  @GetMapping("/chooseWeek")
  // This method gathers & sets up the data/model needed to present the user with the ChooseWeek form/page.
  public String chooseWeek(Model model) {
    logger.debug("in chooseWeek()");

    Map<Integer, String> weeks = new HashMap<Integer, String>();
    // TO-DO-data-weeks: populate based on weeks stored in db
    weeks.put(1, "Week 1");
    weeks.put(2, "Week 2");
    weeks.put(3, "Week 3");
    weeks.put(4, "Week 4");
    weeks.put(5, "Week 5");

    model.addAttribute("weeksMap", weeks);

    model.addAttribute("blankWeek", new WeekForm());

    return "ChooseWeek";
  }

  private final String binding_boundWeek = "org.springframework.validation.BindingResult.boundWeek";
  private final String binding_blankWeek = "org.springframework.validation.BindingResult.blankWeek";

  // http://localhost:8080/ILMServices-FantasyFootball/showWeek
  // e.g.: curl -X POST -F 'week=2' http://localhost:8080/ILMServices-FantasyFootball/showWeek
  @PostMapping("/showWeek")
  // If "boundWeek" weren't specified, then the default name (for the model attribute) would be
  // "weekForm". ("The default model attribute name is inferred from the declared attribute type".)
  public String showWeek(@Valid @ModelAttribute("boundWeek") WeekForm theBoundWeek, BindingResult result, Model model) {
    // Note that 'model' has an attribute in it named 'boundWeek'.
    logger.debug("in showWeek(): theBoundWeek.getWeek()={} result.hasErrors()={}", theBoundWeek.getWeek(), result.hasErrors());

    // This validation is kinda pointless, because requests from a browser will
    // limit the choices in the drop-down menu to the options specified by
    // 'weeksMap'. But include this validation in case a user does something
    // like the following (note the invalid value for week):
    // curl -X POST -F 'week=6' http://localhost:8080/ILMServices-FantasyFootball/showWeek
    if (result.hasErrors()) {
      // Since the code inside this "if" check is only for curl commands, do not
      // bother creating & adding 'weeksMap'.
      // But do add both blankWeek and 'binding_blankWeek' to the model (I couldn't figure out how to re-name the existing model attributes).

      logger.debug("in showWeek(): before: model has: boundWeek={} blankWeek={}: binding: boundWeek={} blankWeek={}",
          model.containsAttribute("boundWeek"), model.containsAttribute("blankWeek"),
          model.containsAttribute(binding_boundWeek), model.containsAttribute(binding_blankWeek));

      // Add the following line of code to avoid getting: "java.lang.IllegalStateException: Neither BindingResult nor plain target object for bean name 'blankWeek' available as request attribute"
      // (ACTUALLY, after adding code below to add 'binding_blankWeek', the following line of code isn't needed, so I commented it out.)
      ///// model.addAttribute("blankWeek", theBoundWeek);

      // for binding, copy bound to blank.
      if (model.containsAttribute(binding_boundWeek)) {
        logger.debug("modelMap - if: found {}", binding_boundWeek);
        Map<String, Object> myModelMap = model.asMap();
        BeanPropertyBindingResult br = (BeanPropertyBindingResult) myModelMap.get(binding_boundWeek);
        logger.debug("modelMap - if: br=***{}***", br);

        // Add this so that ChooseWeek.jsp is able to get the "form:errors" for
        // blankWeek.
        //
        // Note this will put an attribute in the model with the KEY that will match up with the following from ChooseWeek.jsp:
        //  * modelAttribute="blankWeek"
        //  * form:errors path="week"
        // But the VALUE will still have "boundWeek" in its text. But that doesn't make a difference in the html text that is
        // returned to the curl command, because only the message ("must be less than or equal to 5") is returned.
        model.addAttribute(binding_blankWeek, br);
      }

      printMap(model);

      logger.debug("in showWeek(): after: model has: boundWeek={} blankWeek={}: binding: boundWeek={} blankWeek={}",
          model.containsAttribute("boundWeek"), model.containsAttribute("blankWeek"),
          model.containsAttribute(binding_boundWeek), model.containsAttribute(binding_blankWeek));

      return "ChooseWeek";
    }

    // TO-DO-data-weeklyTeams get data from db for theBoundWeek, and have the view display/print it to browser.
    return "ShowWeek";
  }

  private void printMap(Model model) {
    try {
      Map<String, Object> modelMap = model.asMap();
      modelMap.forEach((key, value) -> {
        logger.debug("");
        logger.debug("modelMap: key={} value={}", key, value);
        if (value instanceof WeekForm) {
          logger.debug("modelMap: found WeekForm");
          logger.debug("modelMap: week={}", ((WeekForm) value).getWeek());
        } else if (key.equals(binding_boundWeek)) {
          logger.debug("modelMap: found {}", binding_boundWeek);
          BeanPropertyBindingResult br = (BeanPropertyBindingResult) value;
          logger.debug("modelMap: br=***{}***", br);
        } else if (key.equals(binding_blankWeek)) {
          logger.debug("modelMap: found {}", binding_blankWeek);
          BeanPropertyBindingResult br = (BeanPropertyBindingResult) value;
          logger.debug("modelMap: br=***{}***", br);
        }
      });
    } catch (Exception e) {
      logger.error("showWeek: exception caught", e);
    }
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
