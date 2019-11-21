package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSpectatorCheckTurnRoute {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    PostSpectatorCheckTurnRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;
        LOG.config("PostSpectatorCheckTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        final Player redPlayer = session.attribute(GetGameRoute.RED_PLAYER);
        final Player whitePlayer = session.attribute(GetGameRoute.WHITE_PLAYER);
        String gameCode = redPlayer.getName() + whitePlayer.getName();
        //Get the active color
        Player.Color activeColor = this.gameCenter.getBoardView(gameCode).getActivecolor();
        //Get the current User
        Player currentUser = this.gameCenter.getBoardView(gameCode).getCurrentUser();
        //Create the text message
        Message text = Message.info("false");
        BoardView boardView = this.gameCenter.getBoardView(gameCode);

        //player still deciding
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        } else{ //state needs tp update
            text = Message.info("true");
            session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }

}
