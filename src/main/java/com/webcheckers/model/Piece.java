package com.webcheckers.model;

public class Piece {

    enum TYPE {
        SINGLE, KING
    }
    enum COLOR {
        RED, WHITE
    }
    private TYPE pieceTYPE = null;
    private COLOR pieceCOLOR = null;


    public Piece(TYPE type, COLOR color) {
        this.pieceTYPE = type;
        this.pieceCOLOR = color;
    }

    public TYPE getType() {
        return pieceTYPE;
    }

    public COLOR getColor() {
        return pieceCOLOR;
    }
}
