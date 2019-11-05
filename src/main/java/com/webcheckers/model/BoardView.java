package com.webcheckers.model;

import com.webcheckers.model.Row;
import com.webcheckers.util.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 * The board model for two certain players
 */
public class BoardView implements Iterable<Row>{

    //The rows of the board aka the board
    private ArrayList<Row> rows = new ArrayList<>();
    //The currentUser that is shown the board
    private Player currentUser;
    //The opponent to the currentUser
    private Player opponent;
    //2d array copy of the rows board
    private Space[][] model;
    //A stack of models(Space[][])
    private Stack<Space[][]> stackOfBoards;


    //Constructor
    public BoardView(Player currentUser, Player opponent) {
        this.currentUser = currentUser;
        this.opponent = opponent;
        this.stackOfBoards =  new Stack<>();
        rows = generateBoard(rows);
        this.model = generateBoardArray(rows);
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

    public void updateModel(Move move){
        Piece p = this.model[move.getStart().getRow()][move.getStart().getCell()].getPiece();
        Piece wp = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);     //White Normal Piece
        Piece rp = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);       //Red Normal Piece
        if (Math.abs(move.getStart().getRow()-move.getEnd().getRow())/ 2 == 0){
            int r = (move.getStart().getRow() + move.getEnd().getRow())/2;
            int c = (move.getStart().getCell() + move.getEnd().getCell())/2;
            this.model[r][c].removePiece();
        }
        this.model[move.getStart().getRow()][move.getStart().getCell()].removePiece();
        this.model[move.getEnd().getRow()][move.getEnd().getCell()].putPiece(p);
        Piece test = this.model[move.getEnd().getRow()][move.getEnd().getCell()].getPiece();
        if(test.equals(rp)||test.equals(wp))//If the piece is a normal piece and reaches the other side, make it a king
            if(test.getColor() == Piece.COLOR.WHITE)
                if(move.getEnd().getRow() == 7)
                    test.coronate();
            if(test.getColor() == Piece.COLOR.RED)
                if(move.getEnd().getRow() == 0)
                    test.coronate();
        pushModelStack(getModel());

    }

    //add to stack
    public void pushModelStack(Space[][] model){
        getStackOfBoards().push(model);
        convertToArrayList(model);
    }

    public Stack<Space[][]> getStackOfBoards() {
        return stackOfBoards;
    }

    /**
     * Generate a copy of arraylist rows to 2d array for easier manipulation
     * @param rows the ArrayList of rows
     * @return a 2D Piece array representation of rows
     */
    private Space[][] generateBoardArray(ArrayList<Row> rows){
        Space[][] model = new Space[8][8];
        int i = 0;
        //loop through row
        for (Row row: rows){
            //loop through spaces in that row
            for( Space spaces: row.getSpaces()){
                model[i][spaces.getCellIdx()] = spaces;
            }
            i++;
        }
        return model;
    }

    /**
     * Converts the 2d Piece array back into an array list for the iterator to work
     * rows is a arraylist of Rows and Rows is an arraylist of Spaces.
     * update rows!
     * @param model the 2d Piece Array
     * @return a Arraylist representation of {model}
     */
    private void convertToArrayList(Space[][] model){
        //ArrayList of rows
        ArrayList<Row> rows = new ArrayList<>();
        //looping through model by rows
        for (int i = 0; i < 8; i++){
            //ArrayList of spaces
            ArrayList<Space> spaces = new ArrayList<>();

            ArrayList<Row> rowsForSpaces = new ArrayList<>();
            //looping through cols of 2d array and add to spaces array
            for ( int j = 0; j < 8; j++){
                spaces.add(model[i][j]);
            }
            Row r = new Row(i, spaces);
            rows.add(r);

        }
        setRows(rows);
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    /**
     * Get the rows of the board
     * @return this.rows
     */
    public ArrayList<Row> getRows(){
        return this.rows;
    }

    /**
     * Get the 2d array of spaces
     * @return this.model
     */
    public Space[][] getModel() {
        return model;
    }

    public void setModel(Space[][] m){
        this.model = m;
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

