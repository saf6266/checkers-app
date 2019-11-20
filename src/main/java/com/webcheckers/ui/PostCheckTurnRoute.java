package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private final static Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    final static String TEXT = "text";

    private TemplateEngine templateEngine;
    private Gson gson;
    private GameCenter gameCenter;

    PostCheckTurnRoute(TemplateEngine templateEngine, Gson gson, GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gson = gson;
        this.gameCenter = gameCenter;
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
        BoardView board = this.gameCenter.getBoardView(gameCode);
        //Create the text message
        Message text;

        if(board.getOpponent() == null || board.getCurrentUser() == null){
            text = Message.info("true");
            session.attribute("INFO", text);
            return gson.toJson(text);
        }
        //Determine if it is the current User's turn or not
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        }
        else{ //If if condition fails, tell them it's their turn
            text = Message.info("true");
            session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }
}
