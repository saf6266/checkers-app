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

    private boolean jumped;

    private boolean turnEnd;

    private Board moveCheck;

    private Player.Color activecolor ;

    private Player redPlayer;
    private Player whitePlayer;







    //Constructor
    public BoardView(Player currentUser, Player opponent) {
        this.currentUser = currentUser;
        this.opponent = opponent;
        this.redPlayer = currentUser;
        this.whitePlayer = opponent;
        this.rows = generateBoard(rows);
        this.model = generateBoardArray(rows);
        this.jumped = false;
        this.moveCheck = new Board(model, this);
        this.activecolor = Player.Color.RED;
        this.turnEnd = false;


    }

    public BoardView(Player currentUser, Player opponent, ArrayList<Row> rows, Space[][] model, boolean jumped, boolean turnEnd, Board moveCheck, Player.Color activecolor  ){
        this.currentUser = currentUser;
        this.opponent = opponent;
        this.redPlayer = currentUser;
        this.whitePlayer = opponent;
        this.rows = rows;
        this.model = model;
        this.jumped = jumped;
        this.turnEnd = turnEnd;
        this.moveCheck = moveCheck;
        this.activecolor = activecolor;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player.Color getActivecolor() {
        return activecolor;
    }

    public void setActivecolor(Player.Color activecolor) {
        this.activecolor = activecolor;
    }

    public boolean isTurnEnd() {
        if(!isJumped()) {
            return turnEnd;
        }
        return false;
    }

    public Board getMoveCheck(Space[][] m) {
        moveCheck = new Board(m, this);
        return moveCheck;
    }

    public Board getMoveCheck() {
        return moveCheck;
    }

    public boolean isJumped() {
        return jumped;
    }
    public boolean isLoss(Player.Color color){
        return moveCheck.isLoss(color);
    }

    public void setJumped(boolean b){
        this.jumped = b;
    }

    public void setTurnEnd(boolean turnEnd) {
        this.turnEnd = turnEnd;
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
     * Update the model with all the correct moves and convert to kingpiece
     * @param move: The move that was made
     * @param old: The boardview associated the current game
     */
    public void updateModel(Move move, BoardView old){
        Piece p = this.model[move.getStart().getRow()][move.getStart().getCell()].getPiece();
        Piece wp = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE);     //White Normal Piece
        Piece rp = new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED);       //Red Normal Piece
        if (Math.abs(move.getStart().getRow()-move.getEnd().getRow())/ 2 == 1){
            int r = (move.getStart().getRow() + move.getEnd().getRow())/2;
            int c = (move.getStart().getCell() + move.getEnd().getCell())/2;
            this.model[r][c].removePiece();

        }
        this.model[move.getStart().getRow()][move.getStart().getCell()].removePiece();
        this.model[move.getEnd().getRow()][move.getEnd().getCell()].putPiece(p);
        Piece test = this.model[move.getEnd().getRow()][move.getEnd().getCell()].getPiece();
        if(test.equals(rp)||test.equals(wp)) {//If the piece is a normal piece and reaches the other side, make it a king
            if (test.getColor() == Piece.COLOR.WHITE) {
                if (move.getEnd().getRow() == 7) {
                    this.model[move.getEnd().getRow()][move.getEnd().getCell()].getPiece().coronate();
                }
            }
            if (test.getColor() == Piece.COLOR.RED) {
                if (move.getEnd().getRow() == 0) {
                    this.model[move.getEnd().getRow()][move.getEnd().getCell()].getPiece().coronate();
                }
            }
        }
        convertToArrayList(this.model);
    }

    /**
     * Generate a deep copy of the board
     */
    public Space[][] generateCopyBoard(Space[][] model){
        Space[][] newModel = new Space[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = model[i][j].getPiece();
                if (piece != null && model[i][j].isDark()) {
                    Piece.COLOR color = piece.getColor();
                    Piece.TYPE type = piece.getType();
                    Piece newPiece = new Piece(type, color);
                    newModel[i][j] = new Space(newPiece, j, true);
                }
                else if(piece == null && model[i][j].isDark()){
                    newModel[i][j] = new Space(null, j, true);
                }
                else {
                    newModel[i][j] = model[i][j];
                }
            }
        }
        return newModel;
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

    public void setOpponent(Player opponent){
        this.opponent = opponent;
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

