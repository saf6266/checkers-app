package com.webcheckers.app;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag ("Application-Tier")
public class GameCenterTest {

    private final static Player PLAYER = new Player("model");
    private final static Player OPPONENT = new Player("opponent");

    @Test
    public void testPlayerName_not_in_game_list(){

        final GameCenter CuT = new GameCenter(PLAYER, OPPONENT);

        //Analyze player names not in game list
        assertFalse(CuT.inGame(PLAYER));
        assertFalse(CuT.inGame(OPPONENT));

    }

    @Test
    public void testPlayerName_in_game_list(){

        final GameCenter CuT = new GameCenter(PLAYER, OPPONENT);

        //Add player names to game list
        CuT.addPlayer(PLAYER);
        CuT.addPlayer(OPPONENT);

        //Analyze player names in the games list
        assertTrue(CuT.inGame(PLAYER));
        assertTrue(CuT.inGame(OPPONENT));

    }

    @Test
    public void testGetBoardView(){

        final GameCenter CuT = new GameCenter(PLAYER, OPPONENT);
        final BoardView board = new BoardView(PLAYER, OPPONENT);

        //Analyze the two boards are the same
        assertEquals(board, CuT.getBoard());
    }
}
