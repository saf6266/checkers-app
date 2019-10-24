package com.webcheckers.ui;

import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSignOutRouteTest {

    private PostSignOutRoute CuT;

    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Response response;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        playerLobby = mock(PlayerLobby.class);

        gameCenter = mock(GameCenter.class);

        CuT = new PostSignOutRoute(engine, playerLobby, gameCenter);
    }

    @Test
    public void test_remove_player(){
        when(session.attribute(eq(PostSignInRoute.CURR_USER_ATTR))).thenReturn("");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);
        testHelper.assertViewModelAttribute(PostSignInRoute.CURR_USER_ATTR, "");
    }
}
