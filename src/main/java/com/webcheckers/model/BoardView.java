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
        doNewGame();
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








    private int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.RED and CheckersData.BLACK.
    private int selectedRow, selectedCol;  // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.
    private CheckersMove[] legalMoves;  // An array containing the legal moves for the
    //   current player.
    private BoardChecker board;  // The data for the checkers board is kept here.
    //    This board is also responsible for generating
    //    lists of legal moves.


    private void doNewGame() {

        board.setUpGame();   // Set up the pieces.
        currentPlayer = BoardChecker.RED;   // RED moves first.
        legalMoves = board.getLegalMoves(BoardChecker.RED);  // Get RED's legal moves.
    }

    public void doClickSquare(int row, int col) {
        // This is called by mousePressed() when a player clicks on the
        // square in the specified row and col.  It has already been checked
        // that a game is, in fact, in progress.

      /* If the player clicked on one of the pieces that the player
         can move, mark this row and col as selected and return.  (This
         might change a previous selection.)  Reset the message, in
         case it was previously displaying an error message. */

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
                selectedRow = row;
                selectedCol = col;
                //if (currentPlayer == BoardChecker.RED)
                  //  setText("RED:  Make your move."); //Output
                //else
                  //  setText("BLACK:  Make your move."); //Output
            }

      /* If the user clicked on a squre where the selected piece can be
         legally moved, then make the move and return. */

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
                doMakeMove(legalMoves[i]);
            }

      /* If we get to this point, there is a piece selected, and the square where
         the user just clicked is not one where that piece can be legally moved.
         Show an error message. */

        //message.setText("Click the square you want to move to.");

    }  // end doClickSquare()

    public void doMakeMove(CheckersMove move) {
        // Thiis is called when the current player has chosen the specified
        // move.  Make the move, and then either end or continue the game
        // appropriately.

        board.makeMove(move);

      /* If the move was a jump, it's possible that the player has another
         jump.  Check for legal jumps starting from the square that the player
         just moved to.  If there are any, the player must jump.  The same
         player continues moving.
      */

        if (move.isJump()) {
            legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
            if (legalMoves != null) {
                //if (currentPlayer == BoardChecker.RED)
                    //RED:  You must continue jumping.
                //else
                    //message.setText("BLACK:  You must continue jumping.");
                selectedRow = move.toRow;  // Since only one piece can be moved, select it.
                selectedCol = move.toCol;
                }
        }

      /* The current player's turn is ended, so change to the other player.
         Get that player's legal moves.  If the player has no legal moves,
         then the game ends. */

        if (currentPlayer == BoardChecker.RED) {
            currentPlayer = BoardChecker.BLACK;
            legalMoves = board.getLegalMoves(currentPlayer);
            //if (legalMoves == null)
                //gameOver("BLACK has no moves.  RED wins.");
            //else if (legalMoves[0].isJump())
                //message.setText("BLACK:  Make your move.  You must jump.");
            //else
               // message.setText("BLACK:  Make your move.");
        }
        else {
            currentPlayer = BoardChecker.RED;
            legalMoves = board.getLegalMoves(currentPlayer);
            //if (legalMoves == null)
                //gameOver("RED has no moves.  BLACK wins.");
            //else if (legalMoves[0].isJump())
               // message.setText("RED:  Make your move.  You must jump.");
            //else
                //message.setText("RED:  Make your move.");
        }



    }  // end doMakeMove();
}

