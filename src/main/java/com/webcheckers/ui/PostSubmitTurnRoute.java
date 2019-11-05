package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    PostSubmitTurnRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostSubmitTurnRoute is invoked.");

        final Session session = request.session();
        Player.Color activeColor = session.attribute(GetGameRoute.ACTIVE_COLOR);
        //if they can submit turn
        if ( gameCenter.getBoardView().isTurnEnd() && gameCenter.getStackOfBoardView().size() > 1){
            //Every 2 players has a boardview, that holds the actual model, while board handles movements
            if(activeColor == Player.Color.RED){
                session.attribute(GetGameRoute.ACTIVE_COLOR, Player.Color.WHITE);
            } else {
                session.attribute(GetGameRoute.ACTIVE_COLOR, Player.Color.RED);
            }

            //reset gamecenter's stack
            gameCenter.getStackOfBoardView().clear();
            return gson.toJson(Message.info("Valid Submit"));
        } else {
            return gson.toJson(Message.error("Move still available"));
        }
    }
}
