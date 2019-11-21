package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    PostSubmitTurnRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.gson = gson;
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostSubmitTurnRoute is invoked.");

        final Session session = request.session();
        //Player.Color activeColor = session.attribute(GetGameRoute.ACTIVE_COLOR);
        Player r = session.attribute(GetGameRoute.RED_PLAYER);
        Player w = session.attribute(GetGameRoute.WHITE_PLAYER);

        String gameCode = r.getName() + w.getName();

        BoardView boardView = gameCenter.getBoardView(gameCode);
        Stack<BoardView> stackBoardViews = gameCenter.getStackOfBoardView(gameCode);
        //if they can submit turn
        if ( boardView.isTurnEnd() && stackBoardViews.size() > 1){
            BoardView mostRecent = stackBoardViews.peek();
            //reset game center's stack
            stackBoardViews.clear();
            stackBoardViews.push(mostRecent);
            gameCenter.setBoardView(gameCode, mostRecent);

            boardView.setTurnEnd(false);
            Player redPlayer = boardView.getRedPlayer();
            Player whitePlayer = boardView.getWhitePlayer();
            //Check to see which player is submitting a turn
                //Red Player
            if (boardView.getActivecolor() == Player.Color.RED){
                boardView.setActivecolor(Player.Color.WHITE);
                boardView.setCurrentUser(whitePlayer);
                boardView.setOpponent(redPlayer);
            }
                //White Player
            else {
                boardView.setActivecolor(Player.Color.RED);
                boardView.setCurrentUser(redPlayer);
                boardView.setOpponent(whitePlayer);

            }
            return gson.toJson(Message.info("Success"));
        } else {
            if(boardView.isJumped())
                return gson.toJson(Message.error("A jump exists"));
            else if(boardView.isTurnEnd())
                return gson.toJson(Message.error("Move still available"));
        }
        return null;
    }
}
