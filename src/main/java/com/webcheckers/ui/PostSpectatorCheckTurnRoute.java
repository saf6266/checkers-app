package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSpectatorCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private GameCenter gameCenter;
    private Gson gson;

    PostSpectatorCheckTurnRoute(GameCenter gameCenter, Gson gson){
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
        BoardView boardView = this.gameCenter.getBoardView(gameCode);
        //Get the active color
        Player.Color activeColor = boardView.getActivecolor();
        //Get the current User
        Player currentUser = boardView.getCurrentUser();
        Player opponent = boardView.getOpponent();
        //Create the text message
        Message text;

        //Check to see if the game has ended
        if(currentUser == null || opponent == null){
            text = Message.info("true");
            return gson.toJson(text);
        }
        //player still deciding
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        } else{ //state needs to update
            text = Message.info("true");
            session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }

}
