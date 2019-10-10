package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

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

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;





  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
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

    //retrieve the player's name
    final Session session = request.session();
    final Player player = session.attribute(PostSignInRoute.CURR_USER_ATTR);
    final Player opponent;
    //Get the playerInGame message if it exists
    final Message playerInGame = session.attribute("message");

    if(player != null) {
      if (gameCenter.inGame(player)) {
        opponent = player.getOpponent();
        session.attribute(GetGameRoute.WHITE_PLAYER, player);
        session.attribute(GetGameRoute.RED_PLAYER, opponent);
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;

      }

      if (playerInGame == null) {
        // display a user message in the Home page
        vm.put("message", WELCOME_MSG);
      } else {
        vm.put("message", playerInGame);
      }

      vm.put(PostSignInRoute.CURR_USER_ATTR, player);
    }
    else{
      vm.put("message", WELCOME_MSG);
    }



    //display a list of play that currently in the game.
    vm.put("playerList", playerLobby.getPlayers());


    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
