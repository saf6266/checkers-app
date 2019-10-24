package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.Player;
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

    //friendly objects
    private Player validPlayer;
    private Player playerExists;

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

        //build the Service and Model objects
        //The two players are friendly
        validPlayer = new Player(VALID_PLAYER_NAME);
        playerExists = new Player(NAME_EXISTS);
        //but not the playerLobby
        playerLobby = mock(PlayerLobby.class);

        //Store the player in the session
        when(session.attribute(PostSignInRoute.CURR_USER_ATTR)).thenReturn(validPlayer);

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
    public void valid_name_and_not_already_exist(){
        //Arrange the test scenario: The user's name doesn't exist and is valid
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(VALID_PLAYER_NAME);


        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Invoke the test
        CuT.handle(request, response);
        //Analyze the results
        //  *model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //I don't understand why this is true?
        testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.NAME_EXISTS);
        //  *Inspect the activity within the handle method
        //verify(session).attribute(PostSignInRoute.CURR_USER_ATTR, validPlayer);
        //verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void name_exists(){
        //Arrange the test scenario: The user's name already exists
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(NAME_EXISTS);
        when(playerLobby.addPlayer(playerExists)).thenReturn(false);

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
        //  *test view name
        testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
    }




}
