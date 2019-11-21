package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetSpectatorGameRoute implements Route {

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /spectator/game} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     * @param gameCenter
     *   the gameCenter with all of the games
     * @param gson
     *  the gson
     */
    GetSpectatorGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Spectator Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Spectator Game page
     */
    @Override
    public Object handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        Session session = request.session();

        Player redPlayer = session.attribute(GetGameRoute.RED_PLAYER);
        Player whitePlayer = session.attribute(GetGameRoute.WHITE_PLAYER);
        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        String gameCode = redPlayer.getName() + whitePlayer.getName();
        BoardView boardView = gameCenter.getBoardView(gameCode);
        Player boardCurrentUser = boardView.getCurrentUser();
        Player boardOpponent = boardView.getOpponent();
        //Create the message
        Message message = Message.info(boardOpponent.getName() + " made the last move.\n" +
                                            "It is " + boardCurrentUser.getName() + "'s turn");
        Player.Color activeColor = boardView.getActivecolor();

        //Set the current user to the spectator
        vm.put(PostSignInRoute.CURR_USER_ATTR, currentUser);

        vm.put(GetGameRoute.RED_PLAYER, redPlayer);
        vm.put(GetGameRoute.WHITE_PLAYER, whitePlayer);
        vm.put(GetGameRoute.ACTIVE_COLOR, activeColor);
        vm.put("viewMode", GameCenter.Mode.SPECTATOR);
        vm.put("board", boardView);
        vm.put("message", message);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
