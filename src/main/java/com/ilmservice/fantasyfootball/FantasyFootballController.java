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
import com.ilmservice.fantasyfootball.db.repositories.PlayerDao;
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

  @Autowired
  private PlayerDao playerDao;

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

  // TODO for rest of mapping methods below:
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

    addPlayer(theBoundPlayer); // takes care of calling reorderPlayersRankings

    return "redirect:/nflPlayers";
  }

  // ****

  // TO-DO: move Aaron Rodgers to 52, then move back to 1 and actually ends up 2 (behind Matthew Stafford still at nflRanking 1).

  @GetMapping("/editNFLPlayer/{playerPK}")
  public String editNFLPlayer(@PathVariable Integer playerPK, Model model) {
    logger.debug("in editNFLPlayer(): playerPK={}", playerPK);

    List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    model.addAttribute("nflTeamsList", nflTeams);

    model.addAttribute("playerToEdit", playerRepository.findOne(playerPK));

    return "EditNFLPlayer";
  }

  // TO-DO why not?: "/saveEditedNFLPlayer"
  @PostMapping("/editNFLPlayer/{playerPK}/saveEditedNFLPlayer")
  public String saveEditedNFLPlayer(@Valid @ModelAttribute("playerToEdit") Player theBoundPlayer, BindingResult result, Model model) {
    logger.debug("in saveEditedNFLPlayer(): result.hasErrors()={} theBoundPlayer={}", result.hasErrors(), theBoundPlayer);

    if (result.hasErrors()) {
      return "EditNFLPlayer";
    }

    // TODO?: create helper function(s) to minimize the number of calls to reorderPlayersRankings.
    playerRepository.save(theBoundPlayer);
    reorderPlayersRankings();

    return "redirect:/nflPlayers";
  }

  // ****

  // TO-DO? change to Post (add JavaScript to corresponding "a href"?).
  @GetMapping("deleteNFLPlayer/{playerPK}")
  public String deleteNFLPlayer(@PathVariable Integer playerPK) {
    logger.debug("in deleteNFLPlayer(): playerPK={}", playerPK);

    playerRepository.delete(playerPK);
    reorderPlayersRankings();

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

    addPlayer("Josh", "Olson", "QB", 2, "GB"); // test inserting a player somewhere in the middle (with regards to nflRanking)
    addPlayer("Matt", "McDonald", "K", 444, "MIN"); // test nflRanking value higher than existing number of players is adjusted to: existing # of players + 1
    addPlayer("Jason", "Erdahl", "QB", 0, "GB"); // test user not specifying value for nflRanking (no call made to reorderPlayersRankings)
    showNflPlayers();

    // At this point the nflRanking of these players should be:
    // 95         |Josh                                              |Olson                                             |QB|2          |GB
    // 96         |Matt                                              |McDonald                                          |K |54         |MIN
    // 97         |Jason                                             |Erdahl                                            |QB|55         |GB
  }
  // ************************ ...END DEBUG/TESTING CODE ************************

  // Note that the names are treated as case insensitive.
  private void deletePlayerIfExists(final String firstName, final String lastName) {
    // find & delete the player(s) - given the input parameters there's probably at most 1 player to be deleted, but this method does support deleting 2+ players.
    logger.debug("in deletePlayerIfExists(): firstName={} lastName={}", firstName, lastName);
    List<Player> players = playerRepository.findByFirstName_AndLastName_AllIgnoreCase(firstName, lastName);
    logger.debug("number of players to delete: {}", players.size());
    players.stream().forEach(player -> logger.debug("about to delete NFL player: {}", player.toString()));
    players.stream().forEach(player -> playerRepository.delete(player));

    // If 1+ player was deleted, then call reorderPlayersRankings.
    if (players.size() > 0) {
      // (For simplicity, don't bother to skip calling reorderPlayersRankings when the only deleted player
      // was the last one - i.e. the one with the worst nflRanking.)
      reorderPlayersRankings();
    }
  }

  private void reorderPlayersRankings() {
    logger.debug("in reorderPlayersRankings(): playerRepository.count()={}", playerRepository.count());
    // Go through all the players and update their ranking - might not be the
    // most efficient, but requires the least amount of code. :)
    // This method is not a no-op when a player has been inserted/removed somewhere from the middle (with regards to nflRanking).
    List<Player> nflPlayers = playerRepository.findAll();
    for (int playerCounter = 0; playerCounter < nflPlayers.size(); playerCounter++) {
      final int newRanking = (playerCounter + 1);
      logger.debug("setting ranking to {} for player: {}", newRanking, nflPlayers.get(playerCounter));
      final int id = nflPlayers.get(playerCounter).getPlayerPK();
      Player loopPlayer = playerRepository.findOne(id);
      if (null == loopPlayer) {
        logger.error("Couldn't find Player: newRanking={} id={}", newRanking, id);
      } else {
        loopPlayer.setNflRanking(newRanking);
        playerRepository.save(loopPlayer);
      }
    }
  }

  private void addPlayer(final Player player) {
    addPlayer(player.getFirstName(), player.getLastName(), player.getPosition(), player.getNflRanking(), player.getNflTeam().getLocationAbbreviation());
  }

  // TO-DO make this application thread-safe - e.g. what if another user between
  // restartNflRanking and playerRepository.save? (Currently this implementation
  // isn't foolproof with ensuring/maintaining the sort order of the
  // 'nflRanking' field.)
  private void addPlayer(final String firstName, final String lastName, final String position, final int nflRanking, final String nflTeamAbbreviation) {
    logger.debug("in addPlayer(): firstName={} lastName={} position={} nflRanking={} nflTeamAbbreviation={}",
        firstName, lastName, position, nflRanking, nflTeamAbbreviation);

    List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    Optional<NFLTeam> optionalNflTeam = nflTeams.stream()
        .filter(nflTeam -> nflTeam.getLocationAbbreviation().equals(nflTeamAbbreviation)).findFirst();
    if (!optionalNflTeam.isPresent()) {
      logger.error("NFLTeam not found for abbreviation: {}", nflTeamAbbreviation);
    }
    else {
      NFLTeam nflTeam = optionalNflTeam.get();

      // Make sure the next nflRanking value is correct. (The db (call below to PlayerRepository.save) ignores the value of nflRanking in
      // newPlayer - i.e. the db always uses next generated value; this matters when inserting a new player into the middle of the table.)
      int nextGeneratedValue = nflRanking;
      if ((nflRanking == 0) || (nflRanking > playerRepository.count() + 1)) {
        // Override the nflRanking value that was requested by the client of this 'addPlayer' method.
        nextGeneratedValue = (int) (playerRepository.count() + 1);
      }
      playerDao.restartNflRanking(nextGeneratedValue);

      Player newPlayer = new Player(firstName, lastName, position, nflRanking, nflTeam);
      logger.debug("nextGeneratedValue={}, about to create/save new player: {}", nextGeneratedValue, newPlayer);
      playerRepository.save(newPlayer);

      // If the user did not specify a value for nflRanking, then the db (in the call above to PlayerRepository.save) will have generated the next
      // highest value for 'nflRanking' (i.e. added the player to the end of the table) - in which case there's no need to tell the db to reorder the players.
      // (Note that we also don't need to go into the following "if" check if the user happened to specify the next value that the db was going to
      // generate, but for simplicity don't bother checking for that.)
      if (nflRanking > 0) {
        reorderPlayersRankings();
      }
    }
  } // end addPlayer
}
