package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The model of a row on a board.
 */
public class Row implements Iterable<Space> {

    ///
    ///Attributes
    ///
    private ArrayList<Space> spaces = new ArrayList<>();
    private int index;

    ///
    ///Constructor
    ///
    public Row(int index) {
        this.index = index;
        this.spaces = generateSpaces(spaces, getIndex());
    }

    /**
     * Contructor for the conversion used in boardview
     * @param index
     * @param spaces
     */
    public Row(int index, ArrayList<Space> spaces){
        this.index = index;
        this.spaces = spaces;
    }

    /**
     * Get the spaces in this row
     * @return this.spaces
     */
    public ArrayList<Space> getSpaces(){
        return this.spaces;
    }

    /**
     * Get the row index
     * @return this.index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Initialize the spaces for a specific row index
     * @param spaceList The array list of spaces
     * @param i The specific row index
     * @return The updated space list
     */
    public ArrayList<Space> generateSpaces(ArrayList<Space> spaceList, int i){
            for ( int j = 0; j < 8; j++){
                if( i < 3) { // Generate the WHITE side of the board
                    if (i % 2 == 0) {
                        if (j % 2 == 1) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }}
                    else {
                        if (j % 2 == 0) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }}}
                else if ( i > 4){ // Generate the RED side of the board
                    if (i % 2 == 1) {
                        if (j % 2 == 0) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }}
                    else {
                        if (j % 2 == 1) {
                            spaceList.add(new Space(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED), j, true));
                        } else {
                            spaceList.add(new Space(null, j, false));
                        }}}
                else { //Generate the pieces in the middle without a piece on them
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
                    }}}}
        return spaceList;
    }


    /**
     * Create an iterator of the spaces in this row
     * @return iterator
     */
    @Override
    public Iterator<Space> iterator() {
        Iterator<Space> iterator = spaces.listIterator();
        return iterator;
    }
}
