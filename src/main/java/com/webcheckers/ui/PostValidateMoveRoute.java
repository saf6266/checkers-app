package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import com.webcheckers.util.Move;
import com.webcheckers.util.Position;
import spark.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    PostValidateMoveRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");

        final Session session = request.session();

        Player.Color activeColor = gameCenter.getBoardView().getActivecolor();
        String query = request.queryParams("actionData");
        Move move = gson.fromJson(query, Move.class);
        //get the live board in gamecenter
        BoardView boardView = gameCenter.getBoardView();
        Space[][] model = boardView.getModel();
        Board moves = boardView.getMoveCheck(model);
//        if(gameCenter.getStackOfBoardView().size() > 1)
//            gameCenter.getStackOfBoardView().pop();
//        gameCenter.getStackOfBoardView().push(boardView);
//        boardView.setTurnEnd(false);
//        boardView.setJumped(false);

        if (!boardView.isTurnEnd()) {
            //checking if moved made was valid
            ArrayList<Object> validationResults = moves.isValidMove(move, activeColor);
            boolean isValid = (boolean) validationResults.get(0);
            String message = (String) validationResults.get(1);
            if (isValid) {

                //if last move is a jumped
//                if (boardView.isJumped()) {
//                    //update moves made array
//                    boardView.getMoveCheck(boardView.getModel()).jumpable(move.getEnd().getRow(), move.getEnd().getCell(),
//                            boardView.getModel()[move.getEnd().getRow()][move.getEnd().getCell()].getPieceColor(),
//                            boardView.getModel()[move.getEnd().getRow()][move.getEnd().getCell()].getPiece().getType());
//
//                    if (boardView.getMoveCheck(boardView.getModel()).getPossibleMoves().size() > 0) {
//                        boardView.setTurnEnd(false);
//                    } else {
//                        boardView.setTurnEnd(true);
//                    }
//
//                } else {
//                    boardView.setTurnEnd(true);
//                }

                Space[][] newModel = boardView.generateCopyBoard(model);
                BoardView newBoard = new BoardView(boardView.getCurrentUser(), boardView.getOpponent(), boardView.getRows(),
                        newModel, boardView.isJumped(), boardView.isTurnEnd(), boardView.getMoveCheck(newModel), boardView.getActivecolor());
                //moving the piece aka update the live model in boardView
                newBoard.updateModel(move, boardView);
                gameCenter.getStackOfBoardView().push(newBoard);
                gameCenter.setBoardView(newBoard);
                return gson.toJson(Message.info(message));
            } else {
                return gson.toJson(Message.error(message));
            }
        }
        if(moves.isJump(move.getStart(), move.getEnd())){
            return gson.toJson(Message.error("You can't jump over your own piece!"));
        }
        return gson.toJson(Message.error("You can't make two moves at once"));
    }
}
