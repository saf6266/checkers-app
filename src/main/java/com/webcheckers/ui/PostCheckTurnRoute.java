package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
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
        //Get the active color
        Player.Color activeColor = this.gameCenter.getBoardView().getActivecolor();
        //Get the current User
        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        //Create the text message
        Message text;

        //Determine if it is the current User's turn or not
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        }
        else{
            text = Message.info("true");
            session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }
}
