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
        Player currentUser = this.gameCenter.getBoardView(gameCode).getCurrentUser();
        BoardView boardView = this.gameCenter.getBoardView(gameCode);
        //Create the text message
        Message text;

        if(boardView.getOpponent() == null || boardView.getCurrentUser() == null){
            text = Message.info("true");
            session.attribute("INFO", text);
            return gson.toJson(text);
        }
        //Determine if it is the current User's turn or not
        if (activeColor != currentUser.getColor()) {
            text = Message.info("false");
            session.attribute("INFO", text);
        }
        else{
            if (currentUser.getName().equals("iridocyclitis")){
                //Ai makes a move
                Space[][] model = boardView.getModel();
                Board boardCheck = boardView.getMoveCheck(model);
                boardCheck.findMoves(Player.Color.WHITE);
                Random rand = new Random();

                while (!boardView.isTurnEnd()) {
                    //get random move
                    Move move = boardCheck.getPossibleMoves().get(rand.nextInt(boardCheck.getPossibleMoves().size()));
                    ArrayList<Object> validationResults = boardCheck.isValidMove(move, activeColor);
                        boolean isValid = (boolean) validationResults.get(0);
                        if (isValid) {
                            Space[][] newModel = boardView.generateCopyBoard(boardView.getModel());
                            BoardView newBoard = new BoardView(boardView.getCurrentUser(), boardView.getOpponent(), boardView.getRows(), newModel, boardView.isJumped(), boardView.isTurnEnd(), boardView.getMoveCheck(newModel), boardView.getActivecolor());
                            //moving the piece aka update the live model in boardView
                            newBoard.updateModel(move, boardView);
                            gameCenter.getStackOfBoardView(gameCode).push(newBoard);
                            gameCenter.setBoardView(gameCode, newBoard);
                            boardCheck = gameCenter.getBoardView(gameCode).getMoveCheck();

                            boardCheck.findMoves(Player.Color.WHITE);
                            rand = new Random();
                        }

                }
                Stack<BoardView> stackBoardViews = gameCenter.getStackOfBoardView(gameCode);
                if ( boardView.isTurnEnd() && stackBoardViews.size() > 1) {
                    BoardView mostRecent = stackBoardViews.peek();
                    //reset game center's stack
                    stackBoardViews.clear();
                    stackBoardViews.push(mostRecent);
                    gameCenter.setBoardView(gameCode, mostRecent);

                    mostRecent.setTurnEnd(false);


                    //back to Red's turn
                    mostRecent.setActivecolor(Player.Color.RED);
                    mostRecent.setCurrentUser(whitePlayer);
                    mostRecent.setOpponent(redPlayer);
                }
            }
                text = Message.info("true");
                session.attribute("INFO", text);
        }
        return gson.toJson(text);
    }
}
