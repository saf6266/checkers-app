package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Sign In route.
 */
public class PostSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    //values for the view-model map
    final static String TITLE_ATTR = "title";
    final static String TITLE = "Sign In!";
    final static String VIEW_NAME = "signin.ftl";
    final static String MESSAGE_ATTR = "message";

    final static Message NAME_EXISTS = Message.error("The name you entered already exists, enter a different name.");
    final static Message INVALID_NAME = Message.error("The name you entered has at least one non-alphanumeric letter, enter" +
                                                    " a new name.");
    static final String CURR_USER_ATTR = "currentUser";
    static final String USERNAME_PARAM = "username";

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //Constructor
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        Objects.requireNonNull(templateEngine, "templateEngine is required.");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * Add an error message to the view-model map.
     *
     * @param vm The view-model map
     * @param message The appropriate error message needed to print
     * @return the Model-View for the SignIn page with the message
     */
    private ModelAndView error(final Map<String, Object> vm, final Message message){
        vm.put(MESSAGE_ATTR, message);
        return new ModelAndView(vm, VIEW_NAME);
    }

    /**
     * Get the name that the user entered and determine if it is a valid name
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   null if the player name already exists, otherwise the rendered HTML for the Sign In page
     */
    @Override
    public String handle(Request request, Response response){
        //retrieve the HTTP session
        final Session httpSession = request.session();

        //Start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        //retrieve request parameter
        final String name = request.queryParams(USERNAME_PARAM);

        final Player player = new Player(name);
        ModelAndView mv;
        //Check to see that there are not any non-alphanumeric letters
        if(player.validName()){
            //do if player name already in lobby
            if(!(playerLobby.addPlayer(player))){
                mv = error(vm, NAME_EXISTS);
                return templateEngine.render(mv);
            }
            else {
                //add player object to View_model map
                vm.put(CURR_USER_ATTR, player);
                //add to session (why?) --> so the home route can retrieve the player name;
                httpSession.attribute(CURR_USER_ATTR, player);
                httpSession.attribute("player", player);

                // Go back to the home page
                response.redirect(WebServer.HOME_URL);
                return null;
            }
        }
        else{
            mv = error(vm, INVALID_NAME);
            return templateEngine.render(mv);
        }
    }
}
