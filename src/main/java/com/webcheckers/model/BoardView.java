package com.webcheckers.model;

import com.webcheckers.model.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * The board model for two certain players
 */
public class BoardView implements Iterable<Row>{

    //The rows of the board
    private ArrayList<Row> rows = new ArrayList<>();
    //The currentUser that is shown the board
    private Player currentUser;
    //The opponent to the currentUser
    private Player opponent;

    //Constructor
    public BoardView(Player currentUser, Player opponent) {
        this.currentUser = currentUser;
        this.opponent = opponent;
        rows = generateBoard(rows);
    }

    /**
     * Generate the initial state of the board
     * @param row The uninitialized row
     * @return The initialized row
     */
    private ArrayList<Row> generateBoard(ArrayList<Row> row){
        for(int i = 0; i < 8; i++){
            Row r = new Row(i);
            row.add(r);
        }
        return row;
    }

    /**
     * Get the rows of the board
     * @return this.rows
     */
    private ArrayList<Row> getRows(){
        return this.rows;
    }

    /**
     * Flip the board in the case that the currentUser is
     * the white player.
     */
    public void flipBoard(){
        for(Row row : getRows()){
            Collections.reverse(row.getSpaces());
        }
        Collections.reverse(getRows());

    }

    /**
     * Get the current User
     * @return this.currentUser
     */
    public Player getCurrentUser() {
        return currentUser;
    }

    /**
     * Get the opponent
     * @return this.opponent
     */
    public Player getOpponent() {
        return opponent;
    }

    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Create an iterator of the rows of the board
     * @return iterator
     */
    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> iterator = rows.listIterator();
        return iterator;
    }
}

