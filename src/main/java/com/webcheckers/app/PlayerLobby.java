package com.webcheckers.app;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>(); //The list of players

    /**
     * This program checks to see if the player's username is already in use
     * @param player: The current user
     * @return false if player is not on the list, true if player is on the list
     */
    private boolean checkPlayer(Player player){
        if(!(players.isEmpty())) {
            for (Player person : players) {
                if (person.getName().equals(player.getName()))
                    return true;    //player is in the list
            }
        }
        return false;   //player is not in the list
    }

    /**
     * Adds a player to current number of players online
     * @param player: The current user
     * @return true if the player was added, false if the player name is already in the database
     */
    public boolean addPlayer(Player player){
        if(!(checkPlayer(player))) {
            players.add(player);
            return true;
        }
        return false;
    }

    /**
     * Removes a player from the lobby once they close the page
     * @param player: The current user
     */
    public void removePlayer(Player player){
        if(!(players.isEmpty()))
            this.players.remove(player);
    }

    /**
     * Get the list of players
     * @return list of players
     */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
