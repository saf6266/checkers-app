package com.webcheckers.model;

import com.webcheckers.util.Move;
import com.webcheckers.util.Position;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import sun.font.TrueTypeFont;
//import static org.Mockito.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardViewTest {
    //attributes
    private final static ArrayList<Row> rows = new ArrayList<>();
    private final static Player currentUser = new Player("BOB");
    private final static Player opponent = new Player("JOHN");
    private final static Space[][] model = new Space[8][8];
    private final static boolean jumped = true;
    private final static boolean turnEnd = true;
    private final static Board moveCheck = new Board(model, new BoardView(currentUser, opponent));
    private final static Player.Color activecolor = Player.Color.RED;
    private final static Player redPlayer = new Player("Nick");
    private final static Player whitePlayer = new Player("Tom");
    private final static boolean b = true;

    @Test
    void getRedPlayer() {
       final BoardView CuT = new BoardView(redPlayer, whitePlayer);

        assertEquals(CuT.getRedPlayer(), redPlayer);
    }

    @Test
    void setRedPlayer() {
        final BoardView CuT = new BoardView(redPlayer, whitePlayer);

        CuT.setRedPlayer(redPlayer);
        assertNotNull(CuT.getRedPlayer());
    }

    @Test
    void getWhitePlayer() {
        final BoardView CuT = new BoardView(redPlayer, whitePlayer);

        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    @Test
    void setWhitePlayer() {
        final BoardView CuT = new BoardView(redPlayer, whitePlayer);

        CuT.setWhitePlayer(whitePlayer);
        assertNotNull(CuT.getWhitePlayer());
    }

    @Test
    void getActivecolor() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getActivecolor(), Player.Color.RED);
    }

    @Test
    void setActivecolor() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setActivecolor(activecolor);
        assertNotNull(CuT.getActivecolor());
    }

    @Test
    void isTurnEnd() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertFalse(CuT.isTurnEnd());
    }

    @Test
    void getMoveCheck() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);
        Board BTest = CuT.getMoveCheck(model);

        assertNotEquals(CuT.getMoveCheck(model), BTest);
    }

    @Test
    void testGetMoveCheck() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getMoveCheck(), moveCheck);
    }

    @Test
    void isJumped() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertTrue(CuT.isJumped());
    }


    @Test
    void setJumped() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setJumped(true);
        assertTrue(CuT.isJumped());

    }

    @Test
    void setTurnEnd() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setJumped(false);
        CuT.setTurnEnd(true);
        assertTrue(CuT.isTurnEnd());
    }

    @Test
    void updateModel() {
        final BoardView CuT = new BoardView(currentUser, opponent);
        Move mockMove = mock(Move.class);
        Position mockSPosition = mock(Position.class);
        Position mockEPosition = mock(Position.class);

        when(mockMove.getStart()).thenReturn(mockSPosition);
        when(mockMove.getEnd()).thenReturn(mockEPosition);
        //one move diagonal
        when(mockSPosition.getCell()).thenReturn(1);
        when(mockSPosition.getRow()).thenReturn(2);
        when(mockEPosition.getCell()).thenReturn(2);
        when(mockEPosition.getRow()).thenReturn(3);

        Space[][] model = CuT.getModel();
        assertNotNull(model[2][1].getPiece());
        assertNull(model[3][2].getPiece());
        //CuT.updateModel(mockMove);
        assertNotNull(model[3][2].getPiece());
        assertNull(model[2][1].getPiece());

        //one move diagonal
        when(mockSPosition.getCell()).thenReturn(2);
        when(mockSPosition.getRow()).thenReturn(3);
        when(mockEPosition.getCell()).thenReturn(3);
        when(mockEPosition.getRow()).thenReturn(4);

        Space[][] model2 = CuT.getModel();
        assertNotNull(model2[3][2].getPiece());
        assertNull(model2[4][3].getPiece());
        //CuT.updateModel(mockMove);
        assertNotNull(model2[4][3].getPiece());
        assertNull(model2[3][2].getPiece());

        //one jump diagonal
        when(mockSPosition.getCell()).thenReturn(4);
        when(mockSPosition.getRow()).thenReturn(5);
        when(mockEPosition.getCell()).thenReturn(4);
        when(mockEPosition.getRow()).thenReturn(3);

        Space[][] model3 = CuT.getModel();
        assertNotNull(model3[5][4].getPiece());
        assertNull(model3[3][4].getPiece());
        //CuT.updateModel(mockMove);
        assertNotNull(model3[3][4].getPiece());
        assertNull(model3[5][4].getPiece());

        //test king promotion
        final BoardView CuT1 = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);
        final Piece testPiece = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);
        //testPiece.setPiecePosition(6, 1);

        when(mockSPosition.getCell()).thenReturn(6);
        when(mockSPosition.getRow()).thenReturn(1);
        when(mockEPosition.getCell()).thenReturn(7);
        when(mockEPosition.getRow()).thenReturn(0);

        Space[][] model4 = CuT1.getModel();
        assertNotNull(model4[6][1].getPiece());
        assertNotNull(model4[7][0].getPiece());
        //CuT1.updateModel(mockMove);
        assertNotNull(model4[7][0].getPiece());
        assertNotNull(model4[6][1].getPiece());
        assertEquals(Piece.TYPE.SINGLE,testPiece.getType());
    }

    @Test
    void generateCopyBoard() {
        final BoardView CuT = new BoardView(currentUser, opponent);

        CuT.setModel(model);
        assertSame(CuT.generateCopyBoard(CuT.getModel()), model);
    }

    @Test
    void setRows() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setRows(rows);
        assertNotNull(CuT.getRows());
    }

    @Test
    void getRows() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getRows(), rows);
    }

    @Test
    void getModel() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getModel(), model);
    }

    @Test
    void setModel() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setModel(model);
        assertNotNull(CuT.getModel());
    }

    @Test
    void getCurrentUser() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getCurrentUser(), currentUser);
    }

    @Test
    void getOpponent() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        assertEquals(CuT.getOpponent(), opponent);
    }

    @Test
    void setCurrentUser() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setCurrentUser(currentUser);
        assertNotNull(CuT.getCurrentUser());
    }

    @Test
    void setOpponent() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);

        CuT.setOpponent(opponent);
        assertNotNull(CuT.getOpponent());
    }

    @Test
    void iterator() {
        final BoardView CuT = new BoardView(currentUser, opponent, rows, model, jumped, turnEnd, moveCheck, activecolor);
        final Iterator<Row> iterator = CuT.iterator();

        assertNotEquals(CuT.iterator(), iterator);
    }
}