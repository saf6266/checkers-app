package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.app.GameCenter;
import com.webcheckers.app.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Gson gson;

    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    GetGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter, Gson gson){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        Objects.requireNonNull(playerLobby, "playerLobby is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gson = gson;

    }

    /**
     * Creates a string for the case that a player has captured all of your pieces
     * @param player The player that captured all your pieces
     * @return The string corresponding to this case
     */
    private String piecesCaptured(Player player){
        return player.getName() + " has captured all of your pieces.";
    }

    /**
     * Creates a string for the case that a player resigned
     * @param player The player that resigned
     * @return The string corresponding to the player resigning
     */
    private String playerResigned(Player player){
        return player.getName() + " has decided to resign because you are just too good.";
    }

    /**
     * Creates the modeOptions map for game end. Takes in a string for the reason
     * why the game ended.
     * @param gameEndMessage Game end string
     * @return modeOptions map
     */
    private Map<String, Object> gameEnd(String gameEndMessage){
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", gameEndMessage);
        return modeOptions;
    }

    /**
     * Check to see if the player has any pieces left. Used
     * for game end state
     * @param board The board
     * @param player The player
     * @return true if the player has pieces left, false otherwise
     */
    private boolean piecesLeft(BoardView board, Player player){
        Piece.COLOR colorCheck;
        if(player.getColor() == Player.Color.RED){
            colorCheck = Piece.COLOR.RED;
        }
        else{
            colorCheck = Piece.COLOR.WHITE;
        }
        Space[][] model = board.getModel();
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Piece piece = model[row][col].getPiece();
                if(piece != null){
                    if(piece.getColor() == colorCheck){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Object handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "GAME");

        final Session session = request.session();


        final Player redPlayer = session.attribute(RED_PLAYER);
        final Player whitePlayer = session.attribute(WHITE_PLAYER);
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        Player.Color activeColor = gameCenter.getBoardView().getActivecolor();
        BoardView board = gameCenter.getBoardView();

        whitePlayer.setWhite();
        redPlayer.setRed();
        //Set the players side
        if(currentUser == redPlayer) {
            vm.put(PostSignInRoute.CURR_USER_ATTR, redPlayer);
        }
        else{
            vm.put(PostSignInRoute.CURR_USER_ATTR, whitePlayer);
        }

        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(RED_PLAYER, redPlayer);


        if (activeColor == Player.Color.RED){
            vm.put(ACTIVE_COLOR, Piece.COLOR.RED);
        }
        else {
            vm.put(ACTIVE_COLOR, Piece.COLOR.WHITE);
        }
        //Set the view Mode as PLAY (for now)
        vm.put("viewMode", GameCenter.Mode.PLAY);

        //Check Game End
            //Check to see if there aren't any pieces left for red player
        if(!piecesLeft(board, redPlayer)){
            final Map<String, Object> modeOptions = gameEnd(piecesCaptured(whitePlayer));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }
        //Check to see if there aren't any pieces left for white player
        else if(!piecesLeft(board, whitePlayer)){
            final Map<String, Object> modeOptions = gameEnd(piecesCaptured(redPlayer));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }


        //add the board
        vm.put("board", board);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
