package com.webcheckers.util;

public class Move {

    private int startRow;
    private int endRow;
    private int startCell;
    private int endCell;

    Move(int startRow, int endRow, int startCell, int endCell){
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCell = startCell;
        this.endCell = endCell;
    }

    public Position getStart(){
        return new Position(startRow, startCell);
    }

    public Position getEnd(){
        return new Position(endRow, endCell);
    }
}
