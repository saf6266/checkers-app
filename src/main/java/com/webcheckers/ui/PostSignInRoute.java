package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostSignInRoute implements Route {

    private final static String VIEW_NAME = "signin.ftl";
    private final static Message NAME_EXISTS = Message.info("The name you entered already exists, enter a different name.");
    private final static Message INVALID_NAME = Message.info("The name you entered has at least one non-alphanumeric letter, enter" +
                                                    " a new name.");
    private final static Message ADDED_NAME = Message.info("Successfully added you name to the list!");

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //Constructor
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        Objects.requireNonNull(templateEngine, "templateEngine is required.");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    private ModelAndView error(final Map<String, Object> vm, final Message message){
        vm.put("message", message);
        return new ModelAndView(vm, VIEW_NAME);
    }

    @Override
    public String handle(Request request, Response response){
        //retrieve the HTTP session
        final Session httpSession = request.session();

        //Start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign In!");

        //retrieve request parameter
        final String name = request.queryParams("username");

        final Player player = new Player(name);

        ModelAndView mv;
        //Check to see that there are not any non-alphanumeric letters
        if(player.validName()){
            //Check to see if the name already exists or not
            if(!(playerLobby.addPlayer(player))){
                mv = error(vm, NAME_EXISTS);
                return templateEngine.render(mv);
            }

        }
        else{
            mv = error(vm, INVALID_NAME);
            return templateEngine.render(mv);
        }
        httpSession.attribute("player", player);

        vm.put("message", ADDED_NAME);
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
