package com.webcheckers.model;

public class Player {

    //Attributes
    private boolean turn;
    private String name;
    private Color side = Color.NONE;

    enum Color{
        RED,
        WHITE,
        NONE
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRed(){
        this.side = Color.RED;
    }

    public void setWhite(){
        side = Color.WHITE;
    }

    //Constructor
    public Player(boolean turn, String name){
        setTurn(turn);
        setName(name);
    }


}
