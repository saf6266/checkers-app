package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.util.Move;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;


    PostValidateMoveRoute(TemplateEngine templateEngine, GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostValidateMoveRoute is invoked.");

        final Session session = request.session();
        String query = request.queryParams("actionData");
        Move move = gson.fromJson(query, Move.class);
        BoardView board = gameCenter.getBoard();
        return null;
    }
}
