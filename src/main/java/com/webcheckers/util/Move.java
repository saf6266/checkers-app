package com.webcheckers.util;

public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return this.start;
    }

    public Position getEnd(){
        return this.end;
    }


    public boolean equals(Move move){

        return this.start.getRow() == move.start.getRow() && this.end.getRow() == move.end.getRow()
                && this.end.getCell() == move.end.getCell() && this.start.getCell() == move.start.getCell();
    }
}
