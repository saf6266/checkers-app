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

    private ArrayList<Row> generateBoard(ArrayList<Row> row){
        for(int i = 0; i < 8; i++){
            Row r = new Row(i);
            row.add(r);
        }
        return row;
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

