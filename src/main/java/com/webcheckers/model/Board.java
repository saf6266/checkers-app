package com.webcheckers.model;

import com.webcheckers.app.GameCenter;
import com.webcheckers.util.Move;
import com.webcheckers.util.Position;

import java.util.ArrayList;

public class Board {

    private GameCenter gameCenter;
    private boolean jumped = false;
    private ArrayList<Move> possibleMoves;
    private int row;
    private int col;
    boolean turn;

    public Board(GameCenter gameCenter){
        this.gameCenter = gameCenter;
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

    public boolean isValidMove(Move playerMove){
        findMoves(gameCenter.getBoardView());
        for(Move move : possibleMoves){
            if(playerMove.equals(move)){
                setPossibleMoves(null);
                if(jumped) {
                    setTurn(true);
                    row = playerMove.getEnd().getRow();
                    col = playerMove.getEnd().getCell();
                }
                else
                    setTurn(false);
                return true;
            }
        }
        return false;
    }


    private boolean outOfBounds(int row, int col){
        return row < 0 || row >= 8 || col >= 8 || col < 0;
    }

    public boolean canJump(Position start){
        BoardView boardView = gameCenter.getBoardView();
        Space[][] board = boardView.getModel();
        Player currentUser = boardView.getCurrentUser();

        if(currentUser.getColor() == Player.Color.RED){
            int startRow = start.getRow();
            int startCell = start.getCell();

            if(board[startRow - 1][startCell - 1].getPieceColor() == Piece.COLOR.WHITE &&
                    !outOfBounds(startRow - 1, startCell - 1))
                //(row - 2)(col - 2)
                if(board[startRow - 2][startCell - 2].getPiece() == null
                        && !outOfBounds(startRow - 2, startCell - 2))
                    return true;
                else
                    return false;
        }
        return true;
    }

    /*
    Checks for any possible jumps that can be made
     */
    private void jumpable(int row, int col, Piece.COLOR color, Piece.TYPE type){
        Space[][] model = gameCenter.getBoardView().getModel();
        if(type == Piece.TYPE.SINGLE){
            if(color == Piece.COLOR.WHITE){
                //bottom left
                if(!outOfBounds(row + 2, col - 2)){
                    if(model[row + 1][col - 1].getPieceColor() == Piece.COLOR.RED){
                        if(model[row + 2][col - 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 2, col - 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPieceColor() == Piece.COLOR.RED){
                        if(model[row + 2][col + 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 2, col + 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                    }
                }
            }
            //player color is red
            else{
                //top left
                if(!outOfBounds(row - 2, col - 2)){
                    if(model[row - 1][col - 1].getPieceColor() == Piece.COLOR.WHITE){
                        if(model[row - 2][col - 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row - 2, col - 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                    }
                }
                //top right
                if(!outOfBounds(row - 2, col + 2)){
                    if(model[row - 1][col + 1].getPieceColor() == Piece.COLOR.WHITE){
                        if(model[row - 2][col + 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row - 2, col + 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
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
                    if(model[row + 1][col - 1].getPieceColor() == Piece.COLOR.RED){
                        if(model[row + 2][col - 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 2, col - 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPieceColor() == Piece.COLOR.RED){
                        if(model[row + 2][col + 2].getPiece() == null){
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
                if(model[row - 1][col - 1].getPieceColor() == Piece.COLOR.RED){
                    if(model[row - 2][col - 2].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 2, col - 2);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
            }
            //top right
            if(!outOfBounds(row - 2, col + 2)){
                if(model[row - 1][col + 1].getPieceColor() == Piece.COLOR.RED){
                    if(model[row - 2][col + 2].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 2, col + 2);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
            }
            else{
                //bottom left
                if(!outOfBounds(row + 2, col - 2)){
                    if(model[row + 1][col - 1].getPieceColor() == Piece.COLOR.WHITE){
                        if(model[row + 2][col - 2].getPiece() == null){
                            Position start = new Position(row, col);
                            Position end = new Position(row + 2, col - 2);
                            Move move = new Move(start, end);
                            possibleMoves.add(move);
                        }
                    }
                }
                //bottom right
                if(!outOfBounds(row + 2, col + 2)){
                    if(model[row + 1][col + 1].getPieceColor() == Piece.COLOR.WHITE){
                        if(model[row + 2][col + 2].getPiece() == null){
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
                if(model[row - 1][col - 1].getPieceColor() == Piece.COLOR.WHITE){
                    if(model[row - 2][col - 2].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 2, col - 2);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
            }
            //top right
            if(!outOfBounds(row - 2, col + 2)){
                if(model[row - 1][col + 1].getPieceColor() == Piece.COLOR.WHITE){
                    if(model[row - 2][col + 2].getPiece() == null){
                        Position start = new Position(row, col);
                        Position end = new Position(row - 2, col + 2);
                        Move move = new Move(start, end);
                        possibleMoves.add(move);
                    }
                }
            }
        }

    }

    /*
    Checks for singular movability
     */
    private void movePiece(int row, int col, Piece.COLOR color, Piece.TYPE type){
        Space[][] model = gameCenter.getBoardView().getModel();
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
        jumped = false;
        possibleMoves = null;
    }

    public void findMoves(BoardView board){
        Space[][] model = board.getModel();
        Piece piece = model[row][col].getPiece();
        Player currentUser = board.getCurrentUser();
        if(jumped){
            if(currentUser.getColor() == Player.Color.RED) {
                jumpable(row, col, Piece.COLOR.RED, piece.getType());
                if(possibleMoves.size() == 0){
                    reset();
                }
            }
            else{
                jumpable(row, col, Piece.COLOR.WHITE, piece.getType());
                if(possibleMoves.size() == 0){
                    reset();
                }
            }
        }
        else{
            setJumped(true);
            for(int r = 0; r < 8; r++){
                for(int c = 0; c < 8; c++){
                    if(model[r][c].isDark()) {
                        piece = model[r][c].getPiece();
                        if (currentUser.getColor() == Player.Color.RED) {
                            if (model[r][c].getPieceColor() == Piece.COLOR.RED) {
                                jumpable(r, c, Piece.COLOR.RED, piece.getType());
                            }
                        }

                        else {
                            if (model[r][c].getPieceColor() == Piece.COLOR.WHITE) {
                                jumpable(r, c, Piece.COLOR.WHITE, piece.getType());
                            }
                        }
                    }
                }
            }

            if(possibleMoves.size() == 0){
                setJumped(false);
                for(int r = 0; r < 8; r++){
                    for(int c = 0; c < 8; c++){
                        if(model[r][c].isDark()) {

                            piece = model[r][c].getPiece();
                            if (currentUser.getColor() == Player.Color.RED) {
                                if (model[r][c].getPieceColor() == Piece.COLOR.RED) {
                                    movePiece(r, c, Piece.COLOR.RED, piece.getType());
                                }
                            }

                            else {
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
