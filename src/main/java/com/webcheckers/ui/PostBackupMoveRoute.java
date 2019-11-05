package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

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

        if (gameCenter.getStackOfBoardView().size() > 1){
            gameCenter.setBoardView(gameCenter.getStackOfBoardView().pop());
            return gson.toJson(Message.info("Returned to previous move"));
        } else {
            return gson.toJson(Message.error("Backup not possible"));
        }

    }
}
