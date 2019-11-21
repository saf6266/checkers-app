package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class GetSpectatorStopWatchingRoute implements Route {

    private GameCenter gameCenter;

    GetSpectatorStopWatchingRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response){

        final Session session = request.session();

        Player spectator = session.attribute(PostSignInRoute.CURR_USER_ATTR);

        gameCenter.removeSpectator(spectator);
        Message message = GetHomeRoute.WELCOME_MSG;
        session.attribute("message", message);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
