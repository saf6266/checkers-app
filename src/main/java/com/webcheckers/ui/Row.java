package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {

    private ArrayList<Space> spaces = new ArrayList<>();
    private int index;
    public Row(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }



    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
