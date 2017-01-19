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
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

  // convert blank String (form field - e.g. nflRanking) to zero Integer.
  @InitBinder
  public void registerCustomerBinder(WebDataBinder binder) {
    logger.debug("in registerCustomerBinder()");
    binder.registerCustomEditor(
        Integer.class,
        new CustomNumberEditor(Integer.class, true) {
          @Override
          public void setValue(Object o) {
            super.setValue((o == null)? 0 : o);
          }
        }
        );
  }

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

  // ****

  private void addPositionsList(Model model) {
    final List<String> positions = Arrays.asList("QB", "RB", "WR", "TE", "K");
    model.addAttribute("positionsList", positions);
  }

  private void addNflTeamsList(Model model) {
    final List<NFLTeam> nflTeams = (List<NFLTeam>) nflTeamRepository.findAll();
    model.addAttribute("nflTeamsList", nflTeams);
  }

  // **

  private void addNewPlayerLists(Model model) {
    addPositionsList(model);
    addNflTeamsList(model);
  }

  // http://localhost:8080/ILMServices-FantasyFootball/newNFLPlayer
  // curl -X GET http://localhost:8080/ILMServices-FantasyFootball/newNFLPlayer -o NewNFLPlayer.html
  @GetMapping("/newNFLPlayer")
  public String newNFLPlayer(Model model) {
    logger.debug("in newNFLPlayer()");

    addNewPlayerLists(model);
    model.addAttribute("playerAttribute", new Player());

    return "NewNFLPlayer";
  }

  // (Directly copy/pasting URL into browser (or try to navigate to page via bookmark) is not allowed for POST.)
  // e.g.: curl -X POST -F firstName=Joshua -F lastName=Olson -F position=K -F nflRanking=40 -F nflTeam=MIN http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer -o createNFLPlayer.html
  @PostMapping("/createNFLPlayer")
  public String createNFLPlayer(@Valid @ModelAttribute("playerAttribute") Player theBoundPlayer, BindingResult result, Model model) {
    logger.debug("in createNFLPlayer(): result.hasErrors()={} theBoundPlayer={}", result.hasErrors(), theBoundPlayer);

    // Note that the following commands (invalid field values)...:
    //   #1) curl -X POST -F firstName=Jane -F lastName=Doe -F position=XX -F nflRanking=30 -F nflTeam=TB http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer -o createNFLPlayer2.html
    //   #2) curl -X POST -F firstName=Jane -F lastName=Doe -F position=QB -F nflRanking=30 -F nflTeam=XYZ http://localhost:8080/ILMServices-FantasyFootball/createNFLPlayer
    // ...do not cause a binding error - but they cause the following exceptions:
    //   #1) SqlExceptionHelper: The check constraint 'POSITION_CONSTRAINT' was violated while performing an INSERT or UPDATE on table '"APP"."PLAYERS"'.
    //   #2) SqlExceptionHelper: Column 'NFLTEAM'  cannot accept a NULL value.

    // Censor explicit/swear words from the player's name
    theBoundPlayer.setFirstName(BadWordFilter.getCensoredText(theBoundPlayer.getFirstName()));
    theBoundPlayer.setLastName(BadWordFilter.getCensoredText(theBoundPlayer.getLastName()));

    if (result.hasErrors()) {
      addNewPlayerLists(model);

      // return "redirect:/newNFLPlayer"; // Not ideal: doesn't display any field error messages

      // Not ideal (allows the URL in the browser to change from newNFLPlayer to createNFLPlayer)...
      // ...but at least field error messages get displayed.
      return "NewNFLPlayer";
    }

    playerRepository.addPlayer(theBoundPlayer);

    return "redirect:/nflPlayers";
  }

  // **

  // e.g.:
  //  * http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/6/
  //  * curl -X GET http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/6/ -o EditNFLPlayer.html
  @GetMapping("/editNFLPlayer/{playerPK}")
  public String editNFLPlayer(@PathVariable Integer playerPK, Model model) {
    logger.debug("in editNFLPlayer(): playerPK={}", playerPK);

    // If user manually puts something like the following into their browser (where no player with an id of 999 exists):
    //  * http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/999/
    if (playerPK == null || !playerRepository.exists(playerPK)) {
      model.addAttribute("errorMsg", ("edit player: Invalid player id (does not exist): " + playerPK));
      return "errorMessage";
    } else {
      model.addAttribute("playerToEdit", playerRepository.findOne(playerPK));
    }

    addNflTeamsList(model);

    return "EditNFLPlayer";
  }

  // e.g.: curl http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/6/saveEditedNFLPlayer --data "firstName=Teddy&lastName=Bridgewater&position=QB&nflRanking=46&nflTeam=DET"
  @PostMapping("/editNFLPlayer/{playerPK}/saveEditedNFLPlayer")
  public String saveEditedNFLPlayer(@Valid @ModelAttribute("playerToEdit") Player theBoundPlayer, BindingResult result, Model model) {
    logger.debug("in saveEditedNFLPlayer(): result.hasErrors()={} theBoundPlayer={}", result.hasErrors(), theBoundPlayer);

    if (result.hasErrors()) {
      addNflTeamsList(model); // e.g. for curl command that omits a readonly field

      return "EditNFLPlayer";
    }

    // If user manually does something like the following:
    //  * curl http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/999/saveEditedNFLPlayer --data "firstName=Jordy&lastName=Nelson&nflTeam=GB&position=WR&nflRanking=5"
    if (theBoundPlayer == null || theBoundPlayer.getPlayerPK() == null || !playerRepository.exists(theBoundPlayer.getPlayerPK())) {
      model.addAttribute("errorMsg", ("save player: Invalid player (does not exist): " + theBoundPlayer));
      return "errorMessage";
    }

    // Note that curl commands missing the following field values do not cause a binding error - but they cause the following exceptions:
    //  * (<name of missing field>: "<exception>")
    //  * position: "SqlExceptionHelper: Column 'POSITION'  cannot accept a NULL value."
    //  * nflTeam: "SqlExceptionHelper: Column 'NFLTEAM'  cannot accept a NULL value."
    //  * firstName (and ditto for lastName): "SqlExceptionHelper: Column 'FNAME'  cannot accept a NULL value."

    // TO-DO: ensure that only the non-readonly form fields (i.e. nflRanking & nflTeam) are modified - prevent other fields from
    // being modified by a curl command? Or better to not have the front end coupled in such a manner to the back end? e.g. the following command is accepted:
    //  * curl http://localhost:8080/ILMServices-FantasyFootball/editNFLPlayer/1/saveEditedNFLPlayer --data "firstName=Aaron1&lastName=Rodgers1&position=K&nflRanking=12&nflTeam=MIN"
    playerRepository.editPlayer(theBoundPlayer);

    return "redirect:/nflPlayers";
  }

  // ****

  // e.g.:
  // * http://localhost:8080/ILMServices-FantasyFootball/deleteNFLPlayer/25/
  // * curl -X GET http://localhost:8080/ILMServices-FantasyFootball/deleteNFLPlayer/25/
  @GetMapping("deleteNFLPlayer/{playerPK}")
  public String deleteNFLPlayer(@PathVariable Integer playerPK, Model model) {
    logger.debug("in deleteNFLPlayer(): playerPK={}", playerPK);

    // If user manually puts something like the following into their browser:
    //  * http://localhost:8080/ILMServices-FantasyFootball/deleteNFLPlayer/999/
    if (playerPK == null || !playerRepository.exists(playerPK)) {
      model.addAttribute("errorMsg", ("delete player: Invalid player id (does not exist): " + playerPK));
      return "errorMessage";
    } else {
      model.addAttribute("playerToEdit", playerRepository.findOne(playerPK));
    }

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
    nflPlayers.stream().forEach(nflPlayer -> logger.trace("NFL player: {}", nflPlayer.toString()));
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

  // TODO: migrate the stuff in testPlayers to src/test/ (create automated unit tests - and perhaps use various curl commands (from commentary in this file) as starting point).
  @SuppressWarnings("unused")
  private void testPlayers() {
    logger.debug("in testPlayers()");

    // Test deleting 0 players at once.
    deletePlayerIfExists("Charles", "J");
    deletePlayerIfExists("Char", "J");
    deletePlayerIfExists("Charles", "");

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
