package com.webcheckers.app;

import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import com.webcheckers.util.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    /* The map of boardViews and stacks of boardViews */
    private Map<String, ArrayList<Object>> boards;
    /* The board for the two players in a game */
    //private BoardView boardView;
    /* The list of players in a game */
    private ArrayList<Player> inGameList = new ArrayList<>();
    // The stack of BoardView states
    //private Stack<BoardView> stackOfBoardView;



    /* Constructor */
    public GameCenter(){
        boards = new HashMap<>();
        //boardView = new BoardView(currentUser, opponent);
        //stackOfBoardView = new Stack<>();
        //stackOfBoardView.push(boardView);
    }




    public Stack<BoardView> getStackOfBoardView(String gameCode) {
        ArrayList<Object> board = boards.get(gameCode);
        return (Stack<BoardView>) board.get(1);

    }

    public void setBoardView(String gameCode, BoardView boardView) {
        ArrayList<Object> board = boards.get(gameCode);
        board.set(0, boardView);
    }

    /**
     * Gets the board for the two players
     * @return this.board
     */
    public BoardView getBoardView(String gameCode) {
        ArrayList<Object> board = boards.get(gameCode);
        return (BoardView) board.get(0);
    }

    /**
     * Adds a player to the game list
     * @param currentUser The player that is being added
     */
    public void addPlayer(Player currentUser, Player opponent){
        BoardView boardView = new BoardView(currentUser, opponent);
        Stack<BoardView> stackOfBoardViews = new Stack<>();
        stackOfBoardViews.push(boardView);
        ArrayList<Object> boardArray = new ArrayList<>(2);
        String gameCode = currentUser.getName() + opponent.getName();
        boardArray.add(0, boardView);
        boardArray.add(1, stackOfBoardViews);
        boards.put(gameCode, boardArray);
        //boardView.setCurrentUser(player);
        inGameList.add(currentUser);
        if (!opponent.getName().equals("iridocyclitis")) {
            inGameList.add(opponent);
        }
    }

    public void removePlayer(String gameCode, Player player){
        ArrayList<Object> board = boards.get(gameCode);
        BoardView boardView = (BoardView) board.get(0);
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
            if(person.equals(player)){
                return true;
            }
        }
        return false;
    }
}
