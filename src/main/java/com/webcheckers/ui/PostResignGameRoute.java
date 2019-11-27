package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    private GameCenter gameCenter;
    private Gson gson;

    public static final String MODE_OPTIONS_ATTR = "modeOptions";

    PostResignGameRoute(GameCenter gameCenter, Gson gson){
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;

        LOG.config("PostResignGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostResignGameRoute is invoked");

        final Session session = request.session();
        Player redPlayer = session.attribute(GetGameRoute.RED_PLAYER);
        Player whitePlayer = session.attribute(GetGameRoute.WHITE_PLAYER);

        String gameCode = redPlayer.getName() + whitePlayer.getName();
        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        gameCenter.removePlayer(gameCode, currentUser);
        Message message = Message.info("You have resigned from your game, you lost.");
        session.attribute("message", message);
        Message text = Message.info(currentUser.getName() + " has resigned! ");
        session.attribute("INFO", text);
        return gson.toJson(text);

    }
}
