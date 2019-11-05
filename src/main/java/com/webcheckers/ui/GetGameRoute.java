package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import spark.*;

import java.util.Collections;
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
    static final String ACTIVE_COLOR = "activeColor";
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    GetGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
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
        Player.Color activeColor = gameCenter.getBoardView().getActivecolor();
        BoardView board = gameCenter.getBoardView();

        whitePlayer.setWhite();
        redPlayer.setRed();
        //Set the players side
        if(currentUser == redPlayer) {
            vm.put(PostSignInRoute.CURR_USER_ATTR, redPlayer);
        }
        else{
            for(Row row : board.getRows()){
                Collections.reverse(row.getSpaces());
            }
            Collections.reverse(board.getRows());
            vm.put(PostSignInRoute.CURR_USER_ATTR, whitePlayer);
        }

        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(RED_PLAYER, redPlayer);


        if (activeColor == null){
            vm.put(ACTIVE_COLOR, Piece.COLOR.RED);
        } else {
            vm.put(ACTIVE_COLOR, activeColor);
        }


        //Set the view Mode as PLAY (for now)
        vm.put("viewMode", GameCenter.Mode.PLAY);

        //add the board
        vm.put("board", board);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
