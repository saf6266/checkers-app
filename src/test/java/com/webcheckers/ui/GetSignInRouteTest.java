package com.webcheckers.ui;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
Dom Hen
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    private GetSignInRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    //values for the view-model map
    private static final Message SIGNIN_MSG = Message.info("sign-in message");
    private static final String TITLE = "MyTitle";
    private static final String TITLE_HEAD_TAG = "<title>" + "WebCheckers | " + TITLE + "</title>";
    private static final String TITLE_H1_TAG = "<h1>" + "WebCheckers | " + TITLE + "</h1>";

    private static final String VIEW_NAME = "signin.ftl";



    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new GetSignInRoute(templateEngine);
    }

    @Test
    public void isRendering(){
        //Testing that class contains correct attributes, vm
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.CURR_USER_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.USERNAME_PARAM);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.ACTIVE_COLOR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.RED_PLAYER);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.WHITE_PLAYER);

    }

    @Test
    public void signInViewTest(){
        //Testing route so that html contains what is needed
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSignInRoute.VIEW_NAME);
        vm.put(GetSignInRoute.TITLE_ATTR, TITLE);
        vm.put(GetSignInRoute.MESSAGE_ATTR, SIGNIN_MSG);

        //invoke test
        final String viewHtml = templateEngine.render(modelAndView);

        //analyze results
        //assertTrue(viewHtml.contains(TITLE_H1_TAG), "Title H1 Tag exists");
        //assertTrue(viewHtml.contains(TITLE_HEAD_TAG), "Title Head Tag exists");
        //assertTrue(viewHtml.contains("sign-in message"), "Sign-In Message exists");

    }

    @Test
    public void missingTitle(){
        //Testing html if title is not included in vm
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSignInRoute.VIEW_NAME);
        vm.put(GetSignInRoute.MESSAGE_ATTR, SIGNIN_MSG);

        //invoke test
        final String viewHtml = templateEngine.render(modelAndView);

        //assertFalse(viewHtml.contains(TITLE_HEAD_TAG));
        //assertFalse(viewHtml.contains(TITLE_H1_TAG));
        //assertTrue(viewHtml.contains("sign-in message"), "Sign-In Message exists");



    }







}
