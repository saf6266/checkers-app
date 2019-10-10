package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {

    private ArrayList<Space> spaces = new ArrayList<>();
    private int index;
    public Row(int index) {
        this.index = index;
    }

    private ArrayList<Space> generateSpaces(ArrayList<Space> spaceList){
        for(int i = 0; i < 8; i++){
            Space space = new Space()
        }
    }
    public int getIndex() {
        return index;
    }



    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
