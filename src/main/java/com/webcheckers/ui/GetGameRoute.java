package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetGameRoute implements Route {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";

    GetGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        Objects.requireNonNull(playerLobby, "playerLobby is required.");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

    }

    @Override
    public Object handle(Request request, Response response){

        Map<String, Object> vm = new HashMap<>();

        final Session session = request.session();
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        final Player opponent = session.attribute(WHITE_PLAYER);

        //Set the players side
        opponent.setWhite();
        currentUser.setRed();
        vm.put(PostSignInRoute.CURR_USER_ATTR, currentUser);
        vm.put(WHITE_PLAYER, opponent);
        vm.put(RED_PLAYER, currentUser);

        //Red player goes first
        vm.put(ACTIVE_COLOR, Player.Color.RED);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}