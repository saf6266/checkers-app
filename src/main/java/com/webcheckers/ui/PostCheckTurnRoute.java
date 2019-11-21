package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import com.webcheckers.util.Message;
import com.webcheckers.util.Move;
import spark.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private final static Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    final static String TEXT = "text";

    private TemplateEngine templateEngine;
    private Gson gson;
    private GameCenter gameCenter;

    PostCheckTurnRoute(TemplateEngine templateEngine, Gson gson, GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gson = gson;
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        final Player redPlayer = session.attribute(GetGameRoute.RED_PLAYER);
        final Player whitePlayer = session.attribute(GetGameRoute.WHITE_PLAYER);
        String gameCode = redPlayer.getName() + whitePlayer.getName();
        //Get the active color
        Player.Color activeColor = this.gameCenter.getBoardView(gameCode).getActivecolor();
        //Get the current User
        Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        //Player currentUser = this.gameCenter.getBoardView(gameCode).getCurrentUser();
        BoardView boardView = this.gameCenter.getBoardView(gameCode);
        //Create the text message
        Message text;

        if (boardView.getOpponent() == null || boardView.getCurrentUser() == null) {
            text = Message.info("true");
            session.attribute("INFO", text);
            return gson.toJson(text);
        }

        if(boardView.getActivecolor() == Player.Color.WHITE) {
            if (whitePlayer.getName().equals("#iridocyclitis")) {
                Space[][] model = boardView.getModel();
                Board boardCheck = boardView.getMoveCheck(model);
                //Ai makes a move
                while (!boardView.isTurnEnd()) {

                    boardCheck.findMoves(Player.Color.WHITE);
                    //get random move
                    ArrayList<com.webcheckers.util.Move> validMoves = boardCheck.getPossibleMoves();
                    if (validMoves.size() == 0) {
                        boardView.setActivecolor(Player.Color.RED);
                        boardView.setCurrentUser(redPlayer);
                        boardView.setOpponent(whitePlayer);
                        text = Message.info("true");
                        session.attribute("INFO", text);
                        return gson.toJson(text);
                    } else {
                        int lenResults = validMoves.size() - 1;
                        double randomDouble = Math.random();
                        randomDouble = randomDouble * (lenResults - 1);
                        int randomInt = (int) randomDouble;
                        Move testMove = validMoves.get(randomInt);

                        Board moves = boardView.getMoveCheck(model);
                        boardCheck.isValidMove(testMove, activeColor);
                        ArrayList<Object> validationResults = moves.isValidMove(testMove, activeColor);
                        boardView.updateModel(testMove, boardView);
                    }
                }

                Stack<BoardView> stackBoardViews = gameCenter.getStackOfBoardView(gameCode);
                if (boardView.isTurnEnd() && stackBoardViews.size() == 1) {
                    BoardView mostRecent = stackBoardViews.peek();
                    //reset game center's stack
                    stackBoardViews.clear();
                    stackBoardViews.push(mostRecent);
                    gameCenter.setBoardView(gameCode, mostRecent);
                    mostRecent.setTurnEnd(false);
                    //back to Red's turn
                    mostRecent.setActivecolor(Player.Color.RED);
                    mostRecent.setCurrentUser(redPlayer);
                    mostRecent.setOpponent(whitePlayer);
                }
                text = Message.info("true");
                session.attribute("INFO", text);
                return gson.toJson(text);
            }
        }
        //Determine if it is the current User's turn or not
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        } else{ //If if condition fails, tell them it's their turn
            text = Message.info("true");
            session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }

}//End of class


