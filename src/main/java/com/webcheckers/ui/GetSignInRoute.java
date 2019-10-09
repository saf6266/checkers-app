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

    private static final Message SIGNIN_MSG = Message.info("Enter your name to sign in as a player.");

    GetSignInRoute(final TemplateEngine templateEngine){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine required.");

        LOG.config("GetSignInRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetSignIn is invoked");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Sign In!");
        //
        vm.put("message", SIGNIN_MSG);

        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
