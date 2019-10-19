package com.webcheckers.util;

public class Position {

    private int row;
    private int cell;

    Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }
}
