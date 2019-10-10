package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class Row implements Iterable<Space> {

    private static final Logger LOG = Logger.getLogger(Row.class.getName());


    private ArrayList<Space> spaces = new ArrayList<>();
    private int index;
    public Row(int index) {
        this.index = index;
        spaces = generateSpaces(spaces, index);
    }

    public ArrayList<Space> generateSpaces(ArrayList<Space> spaceList, int i){

            for ( int j = 0; j < 8; j++){
                if( i < 3) {
                    if (i % 2 == 0) {
                        if (j % 2 == 1) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    } else {
                        if (j % 2 == 0) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    }
                } else if ( i > 4){
                    if (i % 2 == 1) {
                        if (j % 2 == 0) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    } else {
                        if (j % 2 == 1) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    }
                } else {
                    if (i % 2 == 1) {
                        if (j % 2 == 0) {
                            spaceList.add(new Space(null, j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    } else {
                        if (j % 2 == 1) {
                            spaceList.add(new Space(null, j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }
                    }

                }

            }

        return spaceList;
    }
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        Iterator<Space> iterator = spaces.listIterator();
        return iterator;
    }
}
