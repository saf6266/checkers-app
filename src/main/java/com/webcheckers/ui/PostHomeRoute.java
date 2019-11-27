package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Intermediate route for when the my home navigation is clicked
 */
public class PostHomeRoute implements Route {

    private GameCenter gameCenter;

    PostHomeRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    /**
     * When the player clicks my home, then they should be redirected to
     * a certain route. If they are playing a game, then redirect them to the resign route
     * If they are spectating a game, then redirect them to the stop watching game
     * If they are already on the home page, then redirect them back to the home route
     * @param request
     *      the request
     * @param response
     *      the response
     * @return
     *      null
     */
    @Override
    public Object handle(Request request, Response response){

        Session session = request.session();
        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        //Check to see if the player is in a game
        if(gameCenter.inGame(currentUser)){
            //Current User is a spectator
            if(currentUser.getOpponent() == null){
                response.redirect(WebServer.SPECTATE_STOP_WATCHING);
                return null;
            }
            //Current User is in a game
            else{
                response.redirect(WebServer.RESIGN_GAME);
                return null;
            }
        }
        //Current User is already on the home page
        else{
            response.redirect(WebServer.HOME_URL);
            return null;
        }
    }

}
