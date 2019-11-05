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


    public boolean equals(Object object){
        if(object instanceof Move){
            Move other = (Move) object;
            return start.equals(other.getStart()) && end.equals(other.getEnd());
        }
        return false;
    }
}
