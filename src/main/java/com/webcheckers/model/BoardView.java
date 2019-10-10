package com.webcheckers.model;

import com.webcheckers.model.Row;

import java.util.ArrayList;
import java.util.Iterator;


public class BoardView implements Iterable<Row>{

    private ArrayList<Row> rows = new ArrayList<>();

    public BoardView() {
    }

    private ArrayList<Row> generateBoard(ArrayList<Row> row){
        for(int i = 0; i < 8; i++){
            Row r = new Row(i);
            row.add(r);
        }
        return row;
    }

    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> iterator = generateBoard(rows).iterator();
        return iterator;
    }
}

