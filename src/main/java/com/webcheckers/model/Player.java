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
    private Color color;
    private Player opponent;
    private int coins;

    ///
    ///Constructors
    ///

    public Player(String name){
        setName(name);
        setNone();
    }

    /**
     * Lose a coin when it is taken
     */
    public void Loss(){
        this.coins--;
    }

    /**
     *
     * @return false if no more tokens, true if tokens still remaining
     */
    public boolean validGame(){
        return this.coins == 0;
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
        return this.color == Color.RED;
    }

    /**
     * Check to see if this player is a white player
     * @return true if white, false otherwise
     */
    public boolean isWhite(){
        return this.color == Color.WHITE;
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
        this.color = Color.RED;
    }

    /**
     * Set the player's color to to white
     */
    public void setWhite(){
        this.color = Color.WHITE;
    }

    /**
     * When the game is finished, call this to reset the player to know they can play a game
     */
    public void setNone(){
        this.color = Color.NONE;
        this.coins = 12;
    }

    public Color getColor(){
        return this.color;
    }

    /**
     * the method will check user input is valid or not
     * only alphanumeric characters will be accepted for the username
     * @return boolean false-not acceptable
     *                 true-acceptable
     */
    public boolean validName(){
        String TempName = this.getName();
        int len = TempName.length();
        if(len == 0){
            return false;
        }
        //check length, if the input is 0
        char letter;
        for(int i = 1; i < len; i++){
            letter = TempName.charAt(i);
            if(letter <= 31)
                return false;
            else if(letter >= 33)
                if(letter <= 47)
                    return false;
                else if(letter >= 58)
                    if(letter <= 64)
                        return false;
                    else if(letter >= 91)
                        if(letter <= 96)
                            return false;
                        else if(letter >= 123)
                            return false;
            //knk
        }
        return true;
    }

}
