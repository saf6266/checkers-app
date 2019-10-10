package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;


public class PostGameRoute implements Route {

    static final Message PLAYER_IN_GAME = Message.error("That player is already in a game. Select someone else.");
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;
    private GameCenter gameCenter;

    PostGameRoute(PlayerLobby playerLobby, GameCenter gameCenter){
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
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

        LOG.finer(opponent.getName());
        final Player currentUser = session.attribute("player");

        //Is the opponent already in a game
        if(gameCenter.inGame(opponent)){
            session.attribute(PostSignInRoute.CURR_USER_ATTR, currentUser);
            session.attribute("message", PLAYER_IN_GAME);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        else{
            gameCenter.addPlayer(currentUser);
            currentUser.setOpponent(opponent);
            gameCenter.addPlayer(opponent);
            opponent.setOpponent(currentUser);
            session.attribute(GetGameRoute.RED_PLAYER, currentUser);
            session.attribute(GetGameRoute.WHITE_PLAYER, opponent);
            response.redirect(WebServer.GAME_URL);
            return null;
        }


    }
}
