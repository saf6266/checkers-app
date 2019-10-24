package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.app.PlayerLobby;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSignInRouteTest {

    private static final String INVALID_PLAYER_NAME = "#$dfhjad@";
    private static final String VALID_PLAYER_NAME = "bob";

    /**
     * The Component Under Test (CuT)
     */
    private PostSignInRoute CuT;

    //attributes holding mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        playerLobby = mock(PlayerLobby.class);

        //Create a unique CuT for each test
        CuT = new PostSignInRoute(playerLobby, engine);
    }


}
