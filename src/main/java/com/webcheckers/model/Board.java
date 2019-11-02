package com.webcheckers.model;

import com.webcheckers.app.GameCenter;
import com.webcheckers.util.Move;
import com.webcheckers.util.Position;

public class Board {

    private GameCenter gameCenter;

    Board(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public boolean isValidMove(Move move){
        BoardView boardView = gameCenter.getBoardView();
        Space[][] board = boardView.getModel();

        Position start = move.getStart();
        Position end = move.getEnd();

        Space startSpace = board[start.getRow()][start.getCell()];
        Space endSpace = board[end.getRow()][end.getCell()];

        if(endSpace.isValid()){
            return true;
        }
        return false;


    }

    public boolean isJump(Move move){
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return (endRow % 2 == startRow % 2);
    }

    public boolean canJump(Position start){
        BoardView boardView = gameCenter.getBoardView();
        Space[][] board = boardView.getModel();
        Player currentUser = boardView.getCurrentUser();

        if(currentUser.getColor() == Player.Color.RED){
            int startRow = start.getRow();
            int startCell = start.getCell();

            if(board[startRow - 1][startCell - 1].getPieceColor() == Piece.COLOR.WHITE )

        }


    }
}
