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

/**
 * The UI Controller to GET the Game page.
 */
public class GetGameRoute implements Route {

    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;

    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /game} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     * @param gameCenter
     *   the single game center that holds all of the games
     * @param gson
     *   the gson
     */
    GetGameRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson){
        Objects.requireNonNull(templateEngine, "templateEngine is required.");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required.");
        this.templateEngine = templateEngine;
        this.gson = gson;
        LOG.config("GetGameRoute is initialized");
    }

    /**
     * Creates a string for the case that a player has captured all of your pieces
     * @param player The player that captured all your pieces
     * @return The string corresponding to this case
     */
    private String piecesCaptured(Player player, Player opponent){
        return player.getName() + " has captured all of " + opponent.getName() + "'s pieces.";
    }

    /**
     * Creates a string for the case that a player has no more valid moves
     * @param player the player with no more moves
     * @param opponent the player's opponent
     * @return The string corresponding to this case
     */
    private String noValidMoves(Player player, Player opponent){
        return player.getName() + " has no more valid moves! " + opponent.getName() + " wins the game!";
    }

    /**
     * Creates a string for the case that a player resigned
     * @return The string corresponding to the player resigning
     */
    private String playerResignedMessage(){
        return "Your opponent has decided to resign because you are just too good.";
    }

    private Map<String, Object> playerResigned(){
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", playerResignedMessage());
        return modeOptions;
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
                if(piece != null)
                    if(piece.getColor() == colorCheck)
                        return true;
            }
        }
        return false;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetGameRoute is invoked");
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "GAME");

        final Session session = request.session();


        final Player redPlayer = session.attribute(RED_PLAYER);
        final Player whitePlayer = session.attribute(WHITE_PLAYER);
        final Player currentUser = session.attribute(PostSignInRoute.CURR_USER_ATTR);
        String gameCode = redPlayer.getName() + whitePlayer.getName();
        BoardView board = gameCenter.getBoardView(gameCode);
        Player.Color player = session.attribute(ACTIVE_COLOR);
        Player.Color activeColor = board.getActivecolor();
        player = activeColor;


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

        //add the board
        vm.put("board", board);

        //Check Game End
            //Check to see if there aren't any pieces left for red player
        if(!piecesLeft(board, redPlayer)){
            final Map<String, Object> modeOptions = gameEnd(piecesCaptured(whitePlayer, redPlayer));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            gameCenter.removePlayer(gameCode, redPlayer);
            gameCenter.removePlayer(gameCode, whitePlayer);
        }
            //Check to see if there aren't any pieces left for white player
        else if(!piecesLeft(board, whitePlayer)){
            final Map<String, Object> modeOptions = gameEnd(piecesCaptured(redPlayer, whitePlayer));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            gameCenter.removePlayer(gameCode, redPlayer);
            gameCenter.removePlayer(gameCode, whitePlayer);
        }
            //Check to see if white player doesn't have any moves left
        else if(board.getMoveCheck().noMoves(Player.Color.WHITE)){
            final Map<String, Object> modeOptions = gameEnd(noValidMoves(whitePlayer, redPlayer));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            gameCenter.removePlayer(gameCode, redPlayer);
            gameCenter.removePlayer(gameCode, whitePlayer);
        }
            //Check to see if red player doesn't have any moves left
        else if(board.getMoveCheck().noMoves(Player.Color.RED)){
            final Map<String, Object> modeOptions = gameEnd(noValidMoves(redPlayer, whitePlayer));
            vm.put("modeOptionsAsJSON" , gson.toJson(modeOptions));
            gameCenter.removePlayer(gameCode, redPlayer);
            gameCenter.removePlayer(gameCode, whitePlayer);
        }
            //Check to see if a player has resigned
        else if(board.getCurrentUser() == null || board.getOpponent() == null){
            final Map<String, Object> modeOptions = playerResigned();
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            if(board.getCurrentUser() == null){
                gameCenter.removePlayer(gameCode, board.getOpponent());
            }
            else{
                gameCenter.removePlayer(gameCode, board.getCurrentUser());
            }
        }

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
