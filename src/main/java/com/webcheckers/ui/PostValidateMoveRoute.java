package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import com.webcheckers.util.Message;
import com.webcheckers.util.Move;
import spark.*;

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
    public Object handle(Request request, Response response){
        LOG.finer("PostValidateMoveRoute is invoked.");

        final Session session = request.session();
        Player.Color activeColor = session.attribute(GetGameRoute.ACTIVE_COLOR);
        String query = request.queryParams("actionData");
        Move move = gson.fromJson(query, Move.class);

        //get the live board in gamecenter
        BoardView boardView = gameCenter.getBoardView();
        //checking if moved made was valid
        if(boardView.getMoveCheck().isValidMove(move, activeColor)){
             //moving the piece aka update the live model in boardVIew
             boardView.updateModel(move);
             //if last move is a jumped
             if (gameCenter.getBoardView().isJumped()){
                 //update moves made array
                 gameCenter.getBoardView().getMoveCheck().jumpable(move.getEnd().getRow(), move.getEnd().getCell(),
                         boardView.getModel()[move.getEnd().getRow()][move.getEnd().getCell()].getPieceColor(),
                         boardView.getModel()[move.getEnd().getRow()][move.getEnd().getCell()].getPiece().getType());

                if(gameCenter.getBoardView().getMoveCheck().getPossibleMoves().size() > 0) {
                    gameCenter.getBoardView().setTurnEnd(false);
                } else {
                    gameCenter.getBoardView().setTurnEnd(true);
                }

             } else {
                 gameCenter.getBoardView().setTurnEnd(true);
             }

             BoardView newBoard = new BoardView(boardView.getCurrentUser(), boardView.getOpponent(), boardView.getRows(),
                     boardView.getModel(), boardView.isJumped(), boardView.isTurnEnd(), boardView.getMoveCheck() );
             gameCenter.getStackOfBoardView().push(newBoard);
             return gson.toJson(Message.info("Valid move"));
        }else{
             return gson.toJson(Message.error("Invalid move"));
        }
    }
}
