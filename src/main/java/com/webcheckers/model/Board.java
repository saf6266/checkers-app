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
    public boolean isValidMove(Move playerMove, Player.Color color){
        reset();
        row = playerMove.getStart().getRow();
        col = playerMove.getStart().getCell();
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

                return true;

            }
        }
        return false;
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

    private boolean isJump(Position start, Position end){
        return Math.abs(end.getRow() - start.getRow()) == 2;
    }

    public ArrayList<Move> canJump(int row, int col, Player.Color activeColor){
        ArrayList<Move> jumps;

        if(activeColor == Player.Color.RED){
            jumps = canJumpHelper(row, col, Piece.COLOR.WHITE);
        }
        else{
            jumps = canJumpHelper(row, col, Piece.COLOR.RED);
        }
        return jumps;
    }

    private ArrayList<Move> canJumpHelper(int row, int col, Piece.COLOR color){

        ArrayList<Move> jumps = new ArrayList<>();

        Position start = new Position(row, col);
        Position end;
        //top left
        if(model[row - 1][col - 1].getPiece() != null) {
            if (model[row - 1][col - 1].getPieceColor() == color &&
                    !outOfBounds(row - 1, col - 1)) {
                if(model[row - 2][col - 2].getPiece() == null
                        && !outOfBounds(row - 2, col - 2)){
                    end = new Position(row - 2, col - 2);
                    Move move = new Move(start, end);
                    jumps.add(move);
                }
            }
        }

        //top right
        if(model[row - 1][col + 1].getPiece() != null) {
            if (model[row - 1][col + 1].getPieceColor() == color &&
                    !outOfBounds(row - 1, col + 1)) {
                if(model[row - 2][col + 2].getPiece() == null
                        && !outOfBounds(row - 2, col + 2)){
                    end = new Position(row - 2, col + 2);
                    Move move = new Move(start, end);
                    jumps.add(move);
                }
            }
        }

        //bottom left
        if(model[row + 1][col - 1].getPiece() != null) {
            if (model[row + 1][col - 1].getPieceColor() == color &&
                    !outOfBounds(row + 1, col - 1)) {
                if(model[row + 2][col - 2].getPiece() == null
                        && !outOfBounds(row + 2, col - 2)){
                    end = new Position(row + 2, col - 2);
                    Move move = new Move(start, end);
                    jumps.add(move);
                }
            }
        }

        //bottom right
        if(model[row + 1][col + 1].getPiece() != null) {
            if (model[row + 1][col + 1].getPieceColor() == color &&
                    !outOfBounds(row + 1, col + 1)) {
                if(model[row + 2][col + 2].getPiece() == null
                        && !outOfBounds(row + 2, col + 2)){
                    end = new Position(row + 2, col + 2);
                    Move move = new Move(start, end);
                    jumps.add(move);
                }
            }
        }

        return jumps;
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
