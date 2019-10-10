package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  //values for view-model map
  final static String VIEW_NAME = "home.ftl";

  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  static final Message PLAYER_IN_GAME = Message.info("That player is already in a game. Select someone else.");

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;


  private ModelAndView error(final Map<String, Object> vm, final Message message){
        vm.put("message", message);
        return new ModelAndView(vm, VIEW_NAME);
  }


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    //retrieve the player's name
    final Session session = request.session();
    final Player player = session.attribute(PostSignInRoute.CURR_USER_ATTR);

    //check to see if the player exists
    if(player != null) {
        vm.put(PostSignInRoute.CURR_USER_ATTR, player);
        session.removeAttribute(PostSignInRoute.CURR_USER_ATTR);
    }


    //display a list of play that currently in the game.
    vm.put("playerList", playerLobby.getPlayers());

    final Session httpSession = request.session();
    ModelAndView mv;

    //If I am not the only one in the game
    if(playerLobby.getNumPlayers() != 1) {
        for (Player opponent : playerLobby.getPlayers()) {
            //Is the player in a game already?

            if (!(opponent.isInGame())) {                 //No
                playerLobby.addPlayerInGame(opponent);
                playerLobby.addPlayerInGame(player);
                httpSession.attribute("whitePlayer", opponent);
                httpSession.attribute("redPlayer", player);
                response.redirect(WebServer.GAME_URL);
                return null;
            } else {                                       //Yes
                mv = error(vm, PLAYER_IN_GAME);
                return templateEngine.render(mv);
            }
        }
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
