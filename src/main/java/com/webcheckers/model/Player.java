package com.webcheckers.model;

/**
 * The model of the player.
 * Deals with player specific attributes like the name of the player,
 * what color they are (red, white or none), etc.
 */
public class Player {

    /**
     * The color of the player:
     * Red player
     * White player
     * None
     */
    public enum Color{
        RED,
        WHITE,
        NONE
    }

    ///
    ///Attributes
    ///
    private boolean turn;
    private String name;
    private Color side;
    private Player opponent;

    ///
    ///Constructors
    ///
    public Player(boolean turn, String name){
        setTurn(turn);
        setName(name);
        setNone();
    }

    public Player(String name){
        setName(name);
        setNone();
    }

    /**
     * Get the opponent of this player
     * @return this.opponent
     */
    public Player getOpponent() {
        return opponent;
    }

    /**
     * Set the player's opponent to an opponent
     * @param opponent The opponent of the player
     */
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


    /**
     * Get the name of the user
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Check to see if this player is a red player
     * @return true if red, false otherwise
     */
    public boolean isRed(){
        return this.side == Color.RED;
    }

    /**
     * Check to see if this player is a white player
     * @return true if white, false otherwise
     */
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

    /**
     * TODO: only allow alphanumeric ranges
     * logic incorrect!!!!!!!!!!!
     * @return
     */
    public boolean validName(){
        String TempName = this.getName();
        int len = TempName.length();
        //check length, if the input is 0
        char letter;
        int ASCII;          //maybe redundant
        for(int i = 1; i < len; i++){     //maybe start from 0
            letter = TempName.charAt(i);
            ASCII = (int)letter;        //maybe redundant
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
            //knk
        }
        return true;
    }




}
