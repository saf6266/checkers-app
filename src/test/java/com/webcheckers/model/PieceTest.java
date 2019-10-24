package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class PieceTest {

    @Test
    public void ctor_withArg(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);
            new Piece(Piece.TYPE.KING, Piece.COLOR.WHITE);
            new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
            new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        }, "Constructor not accepting legal arguments");

    }

    @Test
    public void get_type(){
        Piece CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
        assertSame(CuT.getType(), Piece.TYPE.SINGLE);
        CuT = new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        assertSame(CuT.getType(), Piece.TYPE.KING);
    }

    @Test
    public void get_color(){
        Piece CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
        assertSame(CuT.getColor(), Piece.COLOR.RED);
        CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);
        assertSame(CuT.getColor(), Piece.COLOR.WHITE);
    }


}
