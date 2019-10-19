package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMove implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMove.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;


    PostValidateMove(TemplateEngine templateEngine, GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");

        LOG.config("PostValidateMove is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostValidateMove is invoked.");

        final Session session = request.session();
        BoardView board = gameCenter.getBoard();
        return null;
    }
}
