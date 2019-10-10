package com.webcheckers.model;

public class Player {
    /**
     * The player class to determine the different attributes of the user
     */
    //Attributes
    private boolean turn;
    private boolean inGame;
    private String name;
    private Color side;
    private Player opponent;

    public enum Color{
        RED,
        WHITE,
        NONE
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
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

    public boolean isInGame(){
        return this.inGame;
    }

    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }

    public boolean validName(){
        String TempName = this.getName();
        int len = TempName.length();
        char letter;
        int ASCII;
        for(int i = 1; i < len; i++){
            letter = TempName.charAt(i);
            ASCII = (int)letter;
            if(ASCII <= 31)
                return false;
            else if(ASCII >= 33)
                if(ASCII <= 47)
                    return false;
            else if(ASCII >= 58)
                if(ASCII <= 64)
                    return false;
            else if(ASCII >= 91)
                if(ASCII <= 96)
                    return false;
            else if(ASCII >= 123)
                return false;
        }
        return true;
    }
    /**
     * Get the name of the user
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    public boolean isRed(){
        return this.side == Color.RED;
    }

    public boolean isWhite(){
        return this.side == Color.WHITE;
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


    //Constructors
    public Player(boolean turn, String name){
        setTurn(turn);
        setName(name);
        setNone();
    }

    public Player(String name){
        setName(name);
        setNone();
    }


}
