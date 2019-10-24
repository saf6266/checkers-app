package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import spark.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Shakeel Farooq
 */
@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String INVALID_PLAYER_NAME = "#$dfhjad@";
    private static final String VALID_PLAYER_NAME = "bob";
    private static final String NAME_EXISTS = "bob";

    /**
     * The Component Under Test (CuT)
     */
    private PostSignInRoute CuT;

    //attributes holding mock objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        playerLobby = mock(PlayerLobby.class);

        //Create a unique CuT for each model
        CuT = new PostSignInRoute(playerLobby, engine);
    }

    @Test
    public void invalid_name(){
        //Arrange the test scenario: The user's name is not a valid name
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(INVALID_PLAYER_NAME);


        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Invoke the test
        CuT.handle(request, response);
        //Analyze the results:
        //  *model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //  *model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(PostSignInRoute.TITLE_ATTR, PostSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.INVALID_NAME);
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.CURR_USER_ATTR);
        //  *test view name
        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    @Test
    public void name_exists(){
        //Arrange the test scenario: The user's name already exists
        final Player player = new Player(VALID_PLAYER_NAME);
        playerLobby.addPlayer(player);
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(NAME_EXISTS);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Invoke the test
        CuT.handle(request, response);
        //Analyze the results
        //  *model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //  *model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(PostSignInRoute.TITLE_ATTR, PostSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.NAME_EXISTS);
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.CURR_USER_ATTR);
        //  *test view name
        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }

    @Test
    public void valid_name_and_not_exist(){
        //Arrange the test scenario: The user's name doesn't exist and is valid
        final Player player = new Player(VALID_PLAYER_NAME);
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(VALID_PLAYER_NAME);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Invoke the test
        CuT.handle(request, response);
        //Analyze the results
        //  *model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //  *model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(PostSignInRoute.TITLE_ATTR, PostSignInRoute.TITLE);
        testHelper.assertViewModelAttribute(PostSignInRoute.CURR_USER_ATTR, player);
        testHelper.assertViewModelAttributeIsAbsent(PostSignInRoute.MESSAGE_ATTR);
    }


}
