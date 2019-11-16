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

        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        gameCenter.removePlayer(currentUser);

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", currentUser.getName() + " has resigned.");
        gson.toJson(modeOptions);
        return gson.toJson(Message.info( (String) modeOptions.get("gameOverMessage")));
    }
}
