package com.webcheckers.model;

public class Piece {

    enum TYPE {
        SINGLE, KING
    }
    enum COLOR {
        RED, WHITE
    }
    private TYPE pieceType= null;
    private COLOR pieceColor= null;


    public Piece(TYPE type, COLOR color) {
        this.pieceType = type;
        this.pieceColor = color;
    }

    public TYPE getType() {
        return pieceType;
    }

    public COLOR getColor() {
        return pieceColor;
    }
}
