package com.webcheckers.app;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;

import java.util.ArrayList;

public class GameCenter {

    public enum Mode{
        PLAY,
        SPECTATOR,
        REPLAY
    }

    private BoardView board;
    private ArrayList<Player> inGameList = new ArrayList<>();

    public GameCenter(Player currentUser, Player opponent){

    }
}
