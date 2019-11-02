package com.webcheckers.model;

import com.webcheckers.model.Piece;

/**
 * The model of a space in a row
 */
public class Space {

    ///
    ///Attributes
    ///
    private int cellIdx;        //The index of this space
    private Piece piece;        //The piece that may or may not be on this space
    private boolean isDark;     //Is it a dark space or a white space?

    ///
    ///Constructor
    ///
    public Space( Piece piece, int cellIdx, boolean isDark) {
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.isDark = isDark;
    }

    /**
     * Get the cell index of this space
     * @return this.cellIdx
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Get the piece on this space
     * @return this.piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Put a piece in a spot
     */
    public void putPiece(Piece p){
        this.piece = p;
    }

    /**
     * Removes a piece that was in a spot
     */
    public void removePiece(){
        this.piece = null;
    }

    /**
     * Check to see if this space is dark
     * @return this.isDark
     */
    public boolean isDark() {
        return isDark;
    }

    /**
     * Check to see if this space is a valid space to place a piece
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return (this.getPiece() == null) && this.isDark();
    }
}
