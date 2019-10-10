package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;


public class PostGameRoute implements Route {

    static final Message PLAYER_IN_GAME = Message.error("That player is already in a game. Select someone else.");

    private final PlayerLobby playerLobby;

    PostGameRoute(PlayerLobby playerLobby){
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.playerLobby = playerLobby;
    }
    @Override
    public String handle(Request request, Response response){
        //Retrieve the HTTP session
        final Session session = request.session();

        //Get the opponent's name
        final String opponentName = request.queryParams("opponent");

        Player opponent = null;
        for(Player player : playerLobby.getPlayers()){
            if(player.getName().equals(opponentName)){
                opponent = player;
                break;
            }
        }

        final Player currentUser = session.attribute("player");

        //Is the opponent already in a game
        if(opponent.isInGame()){
            session.attribute("message", PLAYER_IN_GAME);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        else{
            session.attribute(GetGameRoute.RED_PLAYER, currentUser);
            session.attribute(GetGameRoute.WHITE_PLAYER, opponent);
            response.redirect(WebServer.GAME_URL);
            return null;
        }


    }
}
