package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.util.Message;
import com.webcheckers.util.Move;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    PostValidateMoveRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostValidateMoveRoute is invoked.");

        final Session session = request.session();
        String query = request.queryParams("actionData");
        Move move = gson.fromJson(query, Move.class);
        BoardView boardView = gameCenter.getBoardView();
        Board b = new Board(gameCenter);
        if(b.isValidMove(move)){
             boardView.setModel(gameCenter.getBoardView().getModel());
             boardView.updateModel(move);
             return gson.toJson(Message.info("the move is valid"));
        }else{
             return gson.toJson(Message.error("the move is invalid"));
        }
    }
}
