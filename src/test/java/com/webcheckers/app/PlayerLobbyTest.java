package com.webcheckers.app;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerLobbyTest {



    private final static Player PLAYER = new Player("test");
    private final static Player OPPONENT = new Player("opponent");

    @Test
    public void testPlayerName_valid(){

        final Player P1 = new Player("Billy");
        final Player P2 = new Player("John");
        final Player P3 = new Player("Smith");

        final Player CuT = new Player("Billy");

        PlayerLobby lobby = new PlayerLobby();
        lobby.addPlayer(P1);
        lobby.addPlayer(P2);
        lobby.addPlayer(P3);

        assertFalse(lobby.checkPlayer(CuT));
        lobby.addPlayer(CuT);

        assertTrue(lobby.getNumPlayers() == 3);
    }

    @Test
    public void test_Removing(){
        final Player CuT = new Player("test");

        final Player P1 = new Player("Billy");
        final Player P2 = new Player("John");
        final Player P3 = new Player("Smith");

        PlayerLobby lobby = new PlayerLobby();
        lobby.addPlayer(P1);
        lobby.addPlayer(P2);
        lobby.addPlayer(P3);

        assertFalse(lobby.getNumPlayers() == 2);

        assertTrue(lobby.getNumPlayers() == 3);

        lobby.removePlayer(P1);
        assertTrue(lobby.getNumPlayers() == 2);

        lobby.addPlayer(P1);
        assertTrue(lobby.getNumPlayers() == 3);

        lobby.removePlayer(P2);
        lobby.removePlayer(P3);
        assertTrue(lobby.getNumPlayers() == 1);

        lobby.addPlayer(P1);
        assertTrue(lobby.getNumPlayers() == 1);
    }


}


