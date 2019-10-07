package com.webcheckers.model;

public class Player {
    /**
     * The player class to determine the different attributes of the user
     */
    //Attributes
    private boolean turn;
    private String name;
    private Color side;

    enum Color{
        RED,
        WHITE,
        NONE
    }

    /**
     * Checks if it is my turn
     * @return true if my turn, false if not my turn
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * Set if my turn, clear if it is not
     * @param turn if it is my turn or not
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Get the name of the user
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Give the user/player a name
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the player's color to red
     */
    public void setRed(){
        this.side = Color.RED;
    }

    /**
     * Set the player's color to to white
     */
    public void setWhite(){
        this.side = Color.WHITE;
    }

    /**
     * When the game is finished, call this to reset the player to know they can play a game
     */
    public void setNone(){side = Color.NONE;}


    //Constructor
    public Player(boolean turn, String name){
        setTurn(turn);
        setName(name);
        setNone();
    }


}
