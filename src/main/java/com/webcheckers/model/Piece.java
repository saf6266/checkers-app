package com.webcheckers.model;

/**
 * The piece model that may or may not be placed on a space
 */
public class Piece {

    /**
     * The type of piece
     */
    public enum TYPE {
        SINGLE, KING
    }

    /**
     * The color of the piece
     */
    public enum COLOR {
        RED, WHITE
    }

    ///
    ///Attributes
    ///

    private TYPE pieceType;
    private COLOR pieceColor;


    //Constructor
    public Piece(TYPE type, COLOR color) {
        this.pieceType = type;
        this.pieceColor = color;
    }

    /**
     * Get the type of piece
     * @return this.pieceType
     */
    public TYPE getType() {
        return pieceType;
    }

    /**
     * Get the color of the piece
     * @return this.pieceColor
     */
    public COLOR getColor() {
        return pieceColor;
    }

    public void coronate(){
        this.pieceType = TYPE.KING;
    }
}
