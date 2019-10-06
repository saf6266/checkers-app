package com.webcheckers.app;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>();

    private boolean checkPlayer(Player player){
        if(!(players.isEmpty())) {
            for (Player person : players) {
                if (person.getName().equals(player.getName()))
                    return true;    //player is in the list
            }
        }
        return false;   //player is not in the list
    }

    public boolean addPlayer(Player player){
        if(!(checkPlayer(player))) {
            players.add(player);
            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
