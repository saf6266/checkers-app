package com.webcheckers.model;

import com.webcheckers.app.GameCenter;
import com.webcheckers.util.Move;
import com.webcheckers.util.Position;

import java.util.ArrayList;

public class Board {

    private boolean jumped = false;
    private ArrayList<Move> possibleMoves;
    private int row;
    private int col;
    private boolean turn;
    private Space[][] model;
    private BoardView boardview;

    public Board(Space[][] bbc, BoardView b){
        this.model = bbc;
        this.possibleMoves = new ArrayList<>();
        boardview = b;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public void setPossibleMoves(ArrayList<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    /*
    Creates an arraylist of valid moves and then compares the given move to a list of moves
     */
    public ArrayList<Object> isValidMove(Move playerMove, Player.Color color){
        ArrayList<Object> isValid = new ArrayList<>(2);
        reset();
        row = playerMove.getStart().getRow();
        col = playerMove.getStart().getCell();
        Position start = playerMove.getStart();
        Position end = playerMove.getEnd();
        findMoves(color);
        for(Move move : possibleMoves){
            if(playerMove.equals(move)){
                possibleMoves.clear();
                if(boardview.isJumped()) {
                    boardview.setTurnEnd(false);
                    row = playerMove.getEnd().getRow();
                    col = playerMove.getEnd().getCell();
                    int startRow = playerMove.getStart().getRow();
                    int startCol = playerMove.getStart().getCell();
                    Piece piece = model[startRow][startCol].getPiece();
                    jumpable(row, col, piece.getColor(), piece.getType());
                    if(possibleMoves.size() == 0){
                        boardview.setJumped(false);
                        boardview.setTurnEnd(true);
                    }
                    possibleMoves.clear();
                }
                else {
                    boardview.setTurnEnd(true);
                    boardview.setJumped(false);
                }
                //Check to see if the player move is a jump move and add the appropriate message to the array list
                if(isJump(start, end)){
                    isValid.add(0, true);
                    isValid.add(1, "Valid Jump Move");
                    return isValid;
                }
                isValid.add(0, true);
                isValid.add(1, "Valid Single Move");
                return isValid;

            }
        }
        //Check to see if the player move is a jump move and add the appropriate message to the array list
        if(isJump(start, end)){
            isValid.add(0, false);
            isValid.add(1, "Invalid Jump Move");
        }
        else{
            isValid.add(0, false);
            isValid.add(1, "You must make the available jump move");
        }
        return isValid;
    }

    public ArrayList<Move> getPossibleMoves() {
        return possibleMoves;
    }

    /*
        Checks if the player has any remaining pieces as well as valid moves
         */
    public boolean isLoss(Player.Color user){
        findMoves(user);
        if(possibleMoves.size()==0) {

            return true;
        }

        return false;
    }

    private boolean outOfBounds(int row, int col){
        return row < 0 || row >= 8 || col >= 8 || col < 0;
    }

    private boolean isSingleMove(Position start, Position end){
        return Math.abs(end.getRow() - start.getRow()) == 1;
    }

    private boolean isValidSingleMove(Space[][] model, Position end){
        int endRow = end.getRow();
        int endCell = end.getCell();
        Space endSpace = model[endRow][endCell];
        return endSpace.isValid();
    }

    /**
     * Check to see if a move is a jump
     * @param start The start position of the piece
     * @param end The end position of the piece
     * @return true if the move is a jump, false otherwise
     */
    public boolean isJump(Position start, Position end){
        return Math.abs(end.getRow() - start.getRow()) == 2;
    }


    /*
    Checks for any possible jumps that can be made
     */
    public void jumpable(int row, int col, Piece.COLOR color, Piece.TYPE type){
        boardview.setJumped(true);
        if(type == Piece.TYPE.SINGLE){
            if(color == Piece.COLOR.WHITE){
                //bottom left
                if(!outOfBounds(row + 2, col - 2)){
                    if(model[row + 1][col - 1].getPiece() != null) {
                        if (model[row + 1][col - 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row + 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPiece() != null) {
                        if (model[row + 1][col + 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row + 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
            }
            //player color is red
            else{
                //top left
                if(!outOfBounds(row - 2, col - 2)){
                    if(model[row - 1][col - 1].getPiece() != null) {
                        if (model[row - 1][col - 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row - 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //top right
                if(!outOfBounds(row - 2, col + 2)){
                    if(model[row - 1][col + 1].getPiece() != null) {
                        if (model[row - 1][col + 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row - 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
            }
        }
        //King piece
        else{
            if(color == Piece.COLOR.WHITE){
                //bottom left

                if(!outOfBounds(row + 2, col - 2)){
                    if(model[row + 1][col - 1].getPiece() != null) {
                        if (model[row + 1][col - 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row + 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPiece() != null) {
                        if (model[row + 1][col + 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row + 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //top left
                if(!outOfBounds(row - 2, col - 2)){
                    if(model[row - 1][col - 1].getPiece() != null) {
                        if (model[row - 1][col - 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row - 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //top right
                if(!outOfBounds(row - 2, col + 2)){
                    if(model[row - 1][col + 1].getPiece() != null) {
                        if (model[row - 1][col + 1].getPieceColor() == Piece.COLOR.RED) {
                            if (model[row - 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }

                        }
                    }
                }
            }
            //King Piece is red
            else{
                //bottom left
                if(!outOfBounds(row + 2, col - 2)){
                    if(model[row + 1][col - 1].getPiece() != null) {
                        if (model[row + 1][col - 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row + 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPiece() != null) {
                        if (model[row + 1][col + 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row + 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row + 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //top left
                if(!outOfBounds(row - 2, col - 2)){
                    if(model[row - 1][col - 1].getPiece() != null) {
                        if (model[row - 1][col - 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row - 2][col - 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col - 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
                //top right
                if(!outOfBounds(row - 2, col + 2)){
                    if(model[row - 1][col + 1].getPiece() != null) {
                        if (model[row - 1][col + 1].getPieceColor() == Piece.COLOR.WHITE) {
                            if (model[row - 2][col + 2].getPiece() == null) {
                                Position start = new Position(row, col);
                                Position end = new Position(row - 2, col + 2);
                                Move move = new Move(start, end);
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
            }

        }

    }

    /*
    Checks for singular move - ability
     */
    private void movePiece(int row, int col, Piece.COLOR color, Piece.TYPE type){
        boardview.setJumped(false);
        if(type == Piece.TYPE.SINGLE){
            if(color == Piece.COLOR.WHITE){
                //bottom left

                if(!outOfBounds(row + 1, col - 1)){
                        if(model[row + 1][col - 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 1, col - 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                }
                //bottom right
                if(!outOfBounds(row + 1, col + 1)){
                        if(model[row + 1][col + 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 1, col + 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                }
            }
            //player color is red
            else{
                //top left
                if(!outOfBounds(row - 1, col - 1)){
                    if(model[row - 1][col - 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row - 1, col - 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                    }
                }
                //top right
                if(!outOfBounds(row - 1, col + 1)){
                    if(model[row - 1][col + 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row - 1, col + 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                    }
                }
            }
        }
        //King piece
        else{
            if(color == Piece.COLOR.WHITE){
                //bottom left

                if(!outOfBounds(row + 1, col - 1)){
                    if(model[row + 1][col - 1].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row + 1, col - 1);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
                //bottom right
                if(!outOfBounds(row + 1, col + 1)){
                    if(model[row + 1][col + 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 1, col + 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                    }
                }
                //top left
                if(!outOfBounds(row - 1, col - 1)){
                    if(model[row - 1][col - 1].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 1, col - 1);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
                //top right
                if(!outOfBounds(row - 1, col + 1)){
                    if(model[row - 1][col + 1].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 1, col + 1);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
            }

            else{
                //bottom left
                if(!outOfBounds(row + 1, col - 1)){
                    if(model[row + 1][col - 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 1, col - 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                    }
                }
                //bottom right
                if(!outOfBounds(row + 1, col + 1)){
                    if(model[row + 1][col + 1].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 1, col + 1);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                    }
                }
            }
            //top left
            if(!outOfBounds(row - 1, col - 1)){
                if(model[row - 1][col - 1].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 1, col - 1);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                }
            }
            //top right
            if(!outOfBounds(row - 1, col + 1)){
                if(model[row - 1][col + 1].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 1, col + 1);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                }
            }
        }
    }

    private void reset(){
        possibleMoves.clear();
    }

    public void findMoves(Player.Color color){
        Piece piece = this.model[row][col].getPiece();
        if(boardview.isJumped()){
            if(color == Player.Color.RED) {
                jumpable(row, col, Piece.COLOR.RED, piece.getType());

            }
            else{
                jumpable(row, col, Piece.COLOR.WHITE, piece.getType());

            }
        }
        else{
            for(int r = 0; r < 8; r++){
                for(int c = 0; c < 8; c++){
                    if(model[r][c].isDark()) {
                        piece = model[r][c].getPiece();
                        if(piece != null) {
                            if (color == Player.Color.RED) {
                                if (model[r][c].getPieceColor() == Piece.COLOR.RED) {
                                    jumpable(r, c, Piece.COLOR.RED, piece.getType());
                                }
                            } else {
                                if (model[r][c].getPieceColor() == Piece.COLOR.WHITE) {
                                    jumpable(r, c, Piece.COLOR.WHITE, piece.getType());
                                }
                            }
                        }
                    }
                }
            }

            if(possibleMoves.size() == 0){
                for(int r = 0; r < 8; r++){
                    for(int c = 0; c < 8; c++){
                        if(model[r][c].isDark()) {

                            piece = model[r][c].getPiece();
                            if(piece != null) {
                                if (color == Player.Color.RED) {
                                    if (model[r][c].getPieceColor() == Piece.COLOR.RED) {
                                        movePiece(r, c, Piece.COLOR.RED, piece.getType());
                                    }
                                } else {
                                    if (model[r][c].getPieceColor() == Piece.COLOR.WHITE) {
                                        movePiece(r, c, Piece.COLOR.WHITE, piece.getType());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
