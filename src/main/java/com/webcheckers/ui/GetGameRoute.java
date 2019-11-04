package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import spark.*;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The {@code GET /game} route handler.
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    ///
    ///Attributes
    ///
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    //Values used in the view-model map for rendering the game view
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    static final String TITLE = "GAME";

    ///
    ///Constructor
    ///
    GetGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        //
        LOG.config("GetGameRoute is initialized.");

    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();

        vm.put("title", TITLE);

        final Session session = request.session();

        final Player redPlayer = session.attribute(RED_PLAYER);
        final Player whitePlayer = session.attribute(WHITE_PLAYER);
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        Player.Color activeColor = session.attribute(ACTIVE_COLOR);
        BoardView board = gameCenter.getBoardView();

        whitePlayer.setWhite();
        redPlayer.setRed();
        //Set the players side
        if(currentUser == redPlayer) {
            vm.put(PostSignInRoute.CURR_USER_ATTR, redPlayer);
        }
        else{
            for(Row row : board.getRows()){ //Flip the board
                Collections.reverse(row.getSpaces());
            }
            Collections.reverse(board.getRows());
            vm.put(PostSignInRoute.CURR_USER_ATTR, whitePlayer);
        }
        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(RED_PLAYER, redPlayer);


        //Red player goes first
        vm.put(ACTIVE_COLOR, activeColor);

        //check if game is ended then display this object for game.ftl
      //  final Map<String, Object> modeOptions = new HashMap<>(2);
      //  modeOptions.put("isGameOver", true);
       // modeOptions.put("gameOverMessage", /* get end of game message */);
       // vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));


        //Set the view Mode as PLAY (for now)
        vm.put("viewMode", GameCenter.Mode.PLAY);

        //add the board
        vm.put("board", board);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
