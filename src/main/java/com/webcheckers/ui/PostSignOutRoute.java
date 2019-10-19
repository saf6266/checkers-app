package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Sign Out Route
 */
public class PostSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

    ///
    ///Attributes
    ///
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    ///
    ///Constructor
    ///
    PostSignOutRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        //
        LOG.config("PostSignOutRoute is initialized.");
    }

    /**
     * Remove the player from the player lobby and from the in game list
     * if they are in a game. Then go back to the home page.
     * @param request
     *      the HTTP request
     * @param response
     *      the HTTP response
     * @return
     *      null, redirect to the home page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostSignOutRoute is invoked.");

        //Get the player's name
        final Session session = request.session();
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        //If the player is in a game, then remove them from the game
        if(gameCenter.inGame(currentUser)){
            gameCenter.removePlayer(currentUser);
        }
        //Remove the player from the player lobby
        playerLobby.removePlayer(currentUser);

        //Remove the currentUser from the session
        session.removeAttribute(PostSignInRoute.CURR_USER_ATTR);

        //Redirect to the home page
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
