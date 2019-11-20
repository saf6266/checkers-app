package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;


    PostBackupMoveRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){

        final Session session = request.session();
        final Player redPlayer = session.attribute(GetGameRoute.RED_PLAYER);
        final Player whitePlayer = session.attribute(GetGameRoute.WHITE_PLAYER);
        String gameCode = redPlayer.getName() + whitePlayer.getName();

        if (gameCenter.getStackOfBoardView(gameCode).size() > 1){
            gameCenter.getStackOfBoardView(gameCode).pop();
            gameCenter.setBoardView(gameCode, gameCenter.getStackOfBoardView(gameCode).peek());
            if(gameCenter.getStackOfBoardView(gameCode).size() == 1){
                gameCenter.getBoardView(gameCode).setJumped(false);

            }
            gameCenter.getBoardView(gameCode).setTurnEnd(false);

            return gson.toJson(Message.info("Returned to previous move"));
        } else {
            return gson.toJson(Message.error("Backup not possible"));
        }

    }
}
