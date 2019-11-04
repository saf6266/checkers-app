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

    @Override
    public boolean equals(Object move){
        if(move instanceof Move){
            Move other = (Move) move;
            return this.start == other.start && this.end == other.end;
        }
        return false;
    }
}
