package com.webcheckers.app;

import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import com.webcheckers.util.Move;

import java.util.ArrayList;
import java.util.Stack;

/**
 * GameCenter has a game list that determines if a player is in a game
 * and adds players that enter a game.
 * GameCenter also creates only one board for the currentUser and the opponent.
 */
public class GameCenter {

    /**
     * The type of mode that is selected
     */
    public enum Mode{
        PLAY,
        SPECTATOR,
        REPLAY
    }

    /* The board for the two players in a game */
    private BoardView boardView;
    /* The list of players in a game */
    private ArrayList<Player> inGameList = new ArrayList<>();



    /* Constructor */
    public GameCenter(Player currentUser, Player opponent){
        boardView = new BoardView(currentUser, opponent);

    }





    /**
     * Gets the board for the two players
     * @return this.board
     */
    public BoardView getBoardView() {
        return boardView;
    }

    /**
     * Adds a player to the game list
     * @param player The player that is being added
     */
    public void addPlayer(Player player){
        boardView.setCurrentUser(player);
        inGameList.add(player);
    }

    public void removePlayer(Player player){
        boardView.setCurrentUser(null);
        inGameList.remove(player);
    }

    /**
     * Checks to see if a player is already in a game.
     * @param player The player that is being checked
     * @return True if the player is in a game, false otherwise
     */
    public boolean inGame(Player player){
        for(Player person : inGameList){
            if(person.getName().equals(player.getName())){
                return true;
            }
        }
        return false;
    }
}
