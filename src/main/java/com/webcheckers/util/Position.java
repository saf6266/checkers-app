package com.webcheckers.util;

public class Position {

    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Position){
            Position other = (Position) object;
            return this.row == other.row && this.cell == other.cell;
        }
        return false;
    }
}
