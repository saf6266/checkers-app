package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the sign in page.
 *
 */
public class GetSignInRoute implements Route{

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    private final TemplateEngine templateEngine;

    //values for the view-model map
    private static final Message SIGNIN_MSG = Message.info("Enter your name to sign in as a player.");
    private static final String TITLE = "Sign In!";
    private static final String VIEW_NAME = "signin.ftl";

    ///
    ///Constructor
    ///
    GetSignInRoute(final TemplateEngine templateEngine){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine required.");

        LOG.config("GetSignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetSignIn is invoked");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", TITLE);
        //
        vm.put("message", SIGNIN_MSG);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
