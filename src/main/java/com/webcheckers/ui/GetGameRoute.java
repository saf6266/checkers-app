package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import spark.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    static final String ACTIVE_COLOR = "activeColor";
    static final String BOARD = "board";

    GetGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

    }


    @Override
    public Object handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "GAME");

        final Session session = request.session();

        final Player redPlayer = session.attribute(RED_PLAYER);
        final Player whitePlayer = session.attribute(WHITE_PLAYER);
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        BoardView board = gameCenter.getBoard();
        BoardView flippedBoard = board.flipBoard(board);

        //Set the players side
        whitePlayer.setWhite();
        redPlayer.setRed();

        //Determine if redPlayer is the currentUser
        if(redPlayer == currentUser) {
            session.removeAttribute(PostSignInRoute.CURR_USER_ATTR);
            vm.put(PostSignInRoute.CURR_USER_ATTR, redPlayer);
            vm.put(BOARD, board);
        }
        else{
            session.removeAttribute(PostSignInRoute.CURR_USER_ATTR);
            vm.put(PostSignInRoute.CURR_USER_ATTR, whitePlayer);
            vm.put(BOARD, flippedBoard);
        }
        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(RED_PLAYER, redPlayer);



        //Red player goes first
        vm.put(ACTIVE_COLOR, Player.Color.RED);

        //Set the view Mode as PLAY (for now)
        vm.put("viewMode", GameCenter.Mode.PLAY);


        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
