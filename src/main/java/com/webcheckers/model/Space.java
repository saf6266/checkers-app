package com.webcheckers.model;

import com.webcheckers.model.Piece;

public class Space {

    private int cellIdx;
    private Piece piece;
    private boolean isDark;

    public Space( Piece piece, int cellIdx, boolean isDark) {
        this.piece = piece;
        this.cellIdx = cellIdx;
        this.isDark = isDark;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isDark() {
        return isDark;
    }

    public boolean isValid() {
        return (this.getPiece() == null) && this.isDark();
    }
}
