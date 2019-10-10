package com.webcheckers.model;

public class Piece {

    enum type {
        Single, King
    }
    enum color {
        RED, WHITE
    }
    private type pieceType= null;
    private color pieceColor= null;


    public Piece(type type, color color) {
        this.pieceType = type;
        this.pieceColor = color;
    }

    public type getType() {
        return pieceType;
    }

    public color getColor() {
        return pieceColor;
    }
}
