package com.webcheckers.app;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.Stack;

/**
 * @author Shakeel Farooq
 */
@Tag ("Application-Tier")
public class GameCenterTest {


    private final static Player PLAYER = new Player("p1");
    private final static Player OPPONENT = new Player("p2");
    private String gameCode = PLAYER.getName() + OPPONENT.getName();

    @Test
    public void testPlayerName_not_in_game_list(){

        final GameCenter CuT = new GameCenter();
        CuT.addPlayer(PLAYER, OPPONENT);
        //Analyze player names not in game list
        assertFalse(CuT.inGame(PLAYER));
        assertFalse(CuT.inGame(OPPONENT));

    }

    @Test
    public void testPlayerName_in_game_list(){

        final GameCenter CuT = new GameCenter();

        //Add player names to game list
        CuT.addPlayer(PLAYER, OPPONENT);

        //Analyze player names in the games list
        assertTrue(CuT.inGame(PLAYER));
        assertTrue(CuT.inGame(OPPONENT));

    }

    @Test
    public void test_remove_player(){

        final GameCenter CuT = new GameCenter();

        //Add player names to game list
        CuT.addPlayer(PLAYER, OPPONENT);

        //Analyze player names in the games list
        assertTrue(CuT.inGame(PLAYER));
        assertTrue(CuT.inGame(OPPONENT));

        //Remove player names from game list
        CuT.removePlayer(gameCode, PLAYER);
        CuT.removePlayer(gameCode, OPPONENT);

        //Analyze player names not in list
        assertNull(CuT.getBoardView(gameCode));
        assertFalse(CuT.inGame(PLAYER));
        assertFalse(CuT.inGame(OPPONENT));
    }

    @Test
    public void testGetBoardView(){

        final GameCenter CuT = new GameCenter();
        assertNull(CuT.getBoardView(gameCode));
        CuT.addPlayer(PLAYER, OPPONENT);
        assertNotNull(CuT.getBoardView(gameCode));
        final BoardView boardView = CuT.getBoardView(gameCode);

        //Analyze the two boards are the same
        assertEquals(boardView, CuT.getBoardView(gameCode));
    }

    @Test
    public void testSetBoardView(){

        final GameCenter CuT = new GameCenter();
        CuT.addPlayer(PLAYER, OPPONENT);
        final BoardView boardView = CuT.getBoardView(gameCode);
        Space[][] m = boardView.getModel();

        BoardView newBoardView = new BoardView(boardView.getCurrentUser(), boardView.getOpponent(), boardView.getRows(),
                boardView.getModel(), boardView.isJumped(), boardView.isTurnEnd(), boardView.getMoveCheck(m),
                Player.Color.WHITE);

        CuT.setBoardView(gameCode, newBoardView);
        assertNotEquals(boardView, newBoardView);
        assertNotEquals(boardView, CuT.getBoardView(gameCode));
    }

    @Test
    public void testGetStackOfBoards(){

        final GameCenter CuT = new GameCenter();
        assertNull(CuT.getStackOfBoardView(gameCode));
        CuT.addPlayer(PLAYER, OPPONENT);
        assertNotNull(CuT.getStackOfBoardView(gameCode));

        Stack<BoardView> stack = CuT.getStackOfBoardView(gameCode);

        assertEquals(stack, CuT.getStackOfBoardView(gameCode));

    }
}
