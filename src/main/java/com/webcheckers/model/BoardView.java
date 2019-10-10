package com.webcheckers.model;

import com.webcheckers.model.Row;

import java.util.ArrayList;
import java.util.Iterator;


public class BoardView implements Iterable<Row>{

    private ArrayList<Row> rows = new ArrayList<>();
    private Player currentUser;
    private Player opponent;

    public BoardView(Player currentUser, Player opponent) {
        this.currentUser = currentUser;
        this.opponent = opponent;
        rows = generateBoard(rows);
    }

    private BoardView(BoardView board){
        this.currentUser = board.getCurrentUser();
        this.opponent = board.getOpponent();
        this.rows = board.getRows();
    }

    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public ArrayList<Row> getRows(){
        return this.rows;
    }

    private Row getRow(int index){
        return getRows().get(index);
    }

    private ArrayList<Row> generateBoard(ArrayList<Row> row){
        for(int i = 0; i < 8; i++){
            Row r = new Row(i);
            row.add(r);
        }
        return row;
    }

    public BoardView flipBoard(BoardView board){
        BoardView copyBoard = new BoardView(board);
        for(int i = 8; i > 0; i--){
            Row r = board.getRow(i-1);
            copyBoard.getRows().add(8-i, r);
        }
        return copyBoard;
    }

    public Player getCurrentUser() {
        return currentUser;
    }

    public Player getOpponent() {
        return opponent;
    }

    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> iterator = rows.listIterator();
        return iterator;
    }
}

