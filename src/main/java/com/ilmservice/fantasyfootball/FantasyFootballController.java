package com.ilmservice.fantasyfootball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ilmservice.fantasyfootball.db.entities.NFLTeam;
import com.ilmservice.fantasyfootball.db.entities.Player;
import com.ilmservice.fantasyfootball.db.repositories.FantasyTeamRepository;
import com.ilmservice.fantasyfootball.db.repositories.NFLTeamRepository;
import com.ilmservice.fantasyfootball.db.repositories.PlayerRepository;
import com.ilmservice.fantasyfootball.model.WeekForm;;

@Controller
@EnableAutoConfiguration
public class FantasyFootballController {
  private static final Logger logger = LoggerFactory.getLogger(FantasyFootballController.class);

  @Autowired
  private FantasyTeamRepository fantasyTeamRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private NFLTeamRepository nflTeamRepository;

  // ************************ BEGIN MAPPING METHODS... ************************

  // http://localhost:8080/ILMServices-FantasyFootball/
  @RequestMapping("/")
  public String home() {
    logger.debug("in home()");

    showData();
    // testPlayers();

    return "index";
  }

  // ***********

  // http://localhost:8080/ILMServices-FantasyFootball/showTeams
  // curl -X GET http://localhost:8080/ILMServices-FantasyFootball/showTeams -o ShowTeams.html
  @RequestMapping(value = "/showTeams", method = RequestMethod.GET)
  public String listTeams(Model model) {
    logger.debug("in listTeams()");
    model.addAttribute("teamsAttribute", fantasyTeamRepository.findAll());
    return "ShowTeams";
  }

  // ***********

  // http://localhost:8080/ILMServices-FantasyFootball/nflPlayers
  // curl -X GET http://localhost:8080/ILMServices-FantasyFootball/nflPlayers -o NFLPlayers.html
  @RequestMapping(value = "/nflPlayers", method = RequestMethod.GET)
  public String listPlayers(Model model) {
    logger.debug("in listPlayers()");
    model.addAttribute("playersAttribute", playerRepository.findAll());
    return "NFLPlayers";
  }

  // TODO-foolproof for rest of mapping methods below:
  //  * Add help info, and confirmation what happened when user changes something.
  //  * Add curl command, and
  //  * for both various curls commands and URLS: test both happy path and error conditions.
  //   ** (e.g. add code to methods to validate input - e.g. check that playerPK exists)

  // ****

  // http://localhost:8080/ILMServices-FantasyFootball/newNFLPlayer
  // curl -X GET http://localhost:8080/ILMServices-FantasyFootball/newNFLPlayer -o NewNFLPlayer.html
  @GetMapping("/newNFLPlayer")
  public String newNFLPlayer(Model model) {
    logger.debug("in newNFLPlayer()");

    final List<String> positions = Arrays.asList("QB", "RB", "WR", "TE", "K");
    model.addAttribute("positionsList", positions);

    List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    model.addAttribute("nflTeamsList", nflTeams);

    model.addAttribute("playerAttribute", new Player());

    return "NewNFLPlayer";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer
  // e.g.: curl -X POST -F firstName=Joshua -F lastName=Olson -F position=K -F nflRanking=40 -F nflTeam=MIN http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer -o createNFLPlayer.html
  @PostMapping("/createNFLPlayer")
  public String createNFLPlayer(@Valid @ModelAttribute("playerAttribute") Player theBoundPlayer, BindingResult result, Model model) {
    logger.debug("in createNFLPlayer(): result.hasErrors()={} theBoundPlayer={}", result.hasErrors(), theBoundPlayer);

    if (result.hasErrors()) {
      return "NewNFLPlayer";
    }

    playerRepository.addPlayer(theBoundPlayer);

    return "redirect:/nflPlayers";
  }

  // ****

  // TO-DO: move Aaron Rodgers to 52, then move back to 1 and actually ends up 2 (behind Matthew Stafford still at nflRanking 1).

  @GetMapping("/editNFLPlayer/{playerPK}")
  public String editNFLPlayer(@PathVariable Integer playerPK, Model model) {
    logger.debug("in editNFLPlayer(): playerPK={}", playerPK);

    List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    model.addAttribute("nflTeamsList", nflTeams);

    // TODO-foolproof
    model.addAttribute("playerToEdit", playerRepository.findOne(playerPK));

    return "EditNFLPlayer";
  }

  // TO-DO why not?: "/saveEditedNFLPlayer"
  // TODO-foolproof-test
  @PostMapping("/editNFLPlayer/{playerPK}/saveEditedNFLPlayer")
  public String saveEditedNFLPlayer(@Valid @ModelAttribute("playerToEdit") Player theBoundPlayer, BindingResult result, Model model) {
    logger.debug("in saveEditedNFLPlayer(): result.hasErrors()={} theBoundPlayer={}", result.hasErrors(), theBoundPlayer);

    if (result.hasErrors()) {
      return "EditNFLPlayer";
    }

    playerRepository.editPlayer(theBoundPlayer);

    return "redirect:/nflPlayers";
  }

  // ****

  // TO-DO? change to Post (add JavaScript to corresponding "a href"?).
  // TODO-foolproof-test
  @GetMapping("deleteNFLPlayer/{playerPK}")
  public String deleteNFLPlayer(@PathVariable Integer playerPK) {
    logger.debug("in deleteNFLPlayer(): playerPK={}", playerPK);
    playerRepository.deletePlayer(playerPK);
    return "redirect:/nflPlayers";
  }

  // ***********

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

    model.addAttribute("weekAttribute", new WeekForm());

    return "ChooseWeek";
  }

  // http://localhost:8080/ILMServices-FantasyFootball/showWeek
  // e.g.: curl -X POST -F 'week=2' http://localhost:8080/ILMServices-FantasyFootball/showWeek
  @PostMapping("/showWeek")
  // If "weekAttribute" weren't specified, then the default name (for the model attribute) would be
  // "weekForm". ("The default model attribute name is inferred from the declared attribute type".)
  public String showWeek(@Valid @ModelAttribute("weekAttribute") WeekForm theBoundWeek, BindingResult result, Model model) {
    logger.debug("in showWeek(): theBoundWeek.getWeek()={} result.hasErrors()={}", theBoundWeek.getWeek(), result.hasErrors());

    // This validation is kinda pointless, because requests from a browser will
    // limit the choices in the drop-down menu to the options specified by
    // 'weeksMap'. But include this validation in case a user does something
    // like the following (note the invalid value for week):
    // curl -X POST -F 'week=6' http://localhost:8080/ILMServices-FantasyFootball/showWeek
    if (result.hasErrors()) {
      // Since the code inside this "if" check is only for curl commands, do not
      // bother creating & adding 'weeksMap'.

      // NOTE: In order to avoid encountering the following exception, both "ChooseWeek" and "ShowWeek" use the same name ("weekAttribute") for the model attribute.
      // * "java.lang.IllegalStateException: Neither BindingResult nor plain target object for bean name 'blankWeek' available as request attribute"

      return "ChooseWeek";
    }

    // TO-DO-data-weeklyTeams get data from db for theBoundWeek, and have the view display/print it to browser.
    return "ShowWeek";
  }
  // ************************ ...END MAPPING METHODS ************************

  // ************************ BEGIN DEBUG/TESTING CODE... ************************
  private void showData() {
    logger.debug("in showData()");
    // showFantasyTeams();
    // showNflTeams();
    showNflPlayers();
  }

  @SuppressWarnings("unused")
  private void showFantasyTeams() {
    showTable(fantasyTeamRepository, "Fantasy team");
  }

  @SuppressWarnings("unused")
  private void showNflTeams() {
    showTable(nflTeamRepository, "NFL team");
  }

  private void showNflPlayers() {
    long numberPlayers = playerRepository.count();
    logger.debug("number of NFL players: {}", numberPlayers);

    List<Player> nflPlayers = playerRepository.findAll();
    nflPlayers.stream().forEach(nflPlayer -> logger.debug("NFL player: {}", nflPlayer.toString()));
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void showTable(CrudRepository repository, String entityName) {
    logger.debug("in showTable()");
    long numberEntities = repository.count();
    logger.debug("number of {}s: {}", entityName, numberEntities);

    // (prints all of the fields/columns for each of the entities)
    ArrayList entities = (ArrayList) repository.findAll();
    entities.stream().forEach(entity -> logger.debug("{}: {}", entityName, entity.toString()));
  }

  // TODO: migrate the stuff in testPlayers to src/test/
  @SuppressWarnings("unused")
  private void testPlayers() {
    logger.debug("in testPlayers()");

    // Test deleting 0 players at once.
    deletePlayerIfExists("Charles", "J");
    deletePlayerIfExists("Char", "J");
    deletePlayerIfExists("Charles", "");

    // TO-DO Test deleting 2 players at once.

    // Test deleting 1 player at once.
    deletePlayerIfExists("Josh", "Olson");
    deletePlayerIfExists("Matt", "McDonald");
    deletePlayerIfExists("Jason", "Erdahl"); // test deleting the player with the worst nflRanking (reorderPlayersRankings is no-op)
    showNflPlayers();

    createAddPlayer("Josh", "Olson", "QB", 2, "GB"); // test inserting a player somewhere in the middle (with regards to nflRanking)
    createAddPlayer("Matt", "McDonald", "K", 444, "MIN"); // test nflRanking value higher than existing number of players is adjusted to: existing # of players + 1
    createAddPlayer("Jason", "Erdahl", "QB", 0, "GB"); // test user not specifying value for nflRanking (no call made to reorderPlayersRankings)
    showNflPlayers();

    // At this point the nflRanking of these players should be:
    // 95         |Josh                                              |Olson                                             |QB|2          |GB
    // 96         |Matt                                              |McDonald                                          |K |54         |MIN
    // 97         |Jason                                             |Erdahl                                            |QB|55         |GB
  }

  // Note that the names are treated as case insensitive.
  private void deletePlayerIfExists(final String firstName, final String lastName) {
    // Find & delete the player(s) - given the input parameters there's probably at most 1 player to be deleted, but this method does support deleting 2+ players.
    List<Player> players = playerRepository.findByFirstName_AndLastName_AllIgnoreCase(firstName, lastName);
    logger.debug("deletePlayerIfExists(): firstName={} lastName={} players.size()={}", firstName, lastName, players.size());
    if (players.size() > 0) {
      players.stream().forEach(player -> playerRepository.deletePlayer(player.getPlayerPK()));
    }
  }

  // This method creates & adds a player.
  // TO-DO make this application thread-safe - e.g. what if another user between
  // restartNflRanking and playerRepository.save? (Currently this implementation
  // isn't foolproof with ensuring/maintaining the sort order of the
  // 'nflRanking' field.)
  private void createAddPlayer(final String firstName, final String lastName, final String position, final int requestedNflRanking, final String nflTeamAbbreviation) {
    logger.debug("in createAddPlayer(): firstName={} lastName={} position={} requestedNflRanking={} nflTeamAbbreviation={}",
        firstName, lastName, position, requestedNflRanking, nflTeamAbbreviation);

    List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    Optional<NFLTeam> optionalNflTeam = nflTeams.stream()
        .filter(nflTeam -> nflTeam.getLocationAbbreviation().equals(nflTeamAbbreviation)).findFirst();
    if (!optionalNflTeam.isPresent()) {
      logger.error("NFLTeam not found for abbreviation: {}", nflTeamAbbreviation);
    }
    else {
      NFLTeam nflTeam = optionalNflTeam.get();
      Player newPlayer = new Player(firstName, lastName, position, requestedNflRanking, nflTeam);
      playerRepository.addPlayer(newPlayer);
    }
  }
  // ************************ ...END DEBUG/TESTING CODE ************************

}
