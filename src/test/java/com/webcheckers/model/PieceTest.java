package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class PieceTest {

    @Test
    public void ctor_withArg(){
        //testing constructors
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);
            new Piece(Piece.TYPE.KING, Piece.COLOR.WHITE);
            new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
            new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        }, "Constructor not accepting legal arguments");

    }

    @Test
    public void get_type(){

        //testing type single
        Piece CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
        assertSame(CuT.getType(), Piece.TYPE.SINGLE);

        //testing type KING
        CuT = new Piece(Piece.TYPE.KING, Piece.COLOR.RED);
        assertSame(CuT.getType(), Piece.TYPE.KING);
    }

    @Test
    public void get_color(){

        //testing color RED
        Piece CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);
        assertSame(CuT.getColor(), Piece.COLOR.RED);

        //testing color WHITE
        CuT = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);
        assertSame(CuT.getColor(), Piece.COLOR.WHITE);
    }


}
