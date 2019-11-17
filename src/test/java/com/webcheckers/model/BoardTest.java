package com.webcheckers.model;


import com.webcheckers.util.Move;
import com.webcheckers.util.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("model-Tier")
public class BoardTest {

    private final static Space[][] model = new Space[8][8];
    //private final static BoardView boardview = new BoardView();

    @Test
    public void test_isValidMove(){
        final Position p1 = new Position(2,4);
        final Position p2 = new Position(3,7);
        final Move move = new Move(p1, p2);
        final Player.Color c1 = Player.Color.RED;
        //final Board B1 = new Board(model, boardview);
        //assertTrue(B1.isValidMove(move, c1));
    }
}
