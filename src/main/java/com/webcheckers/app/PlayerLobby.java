package com.webcheckers.app;

import com.webcheckers.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * PlayerLobby is the lobby of players that are signed in to the
 * Web Checkers site. Various functions are in this class to determine
 * if a player name is already been used, adding a player name that signs in
 * to the player list, or removing a player name that signs out from the player list.
 */
public class PlayerLobby {

    private ArrayList<Player> players = new ArrayList<>(); //The list of players

    /**
     * Check to see if the player's username is already in use/or is invalid
     * @param player: The current user
     * @return true if player is ok to add, false if player cannot be added
     */
    public boolean checkPlayer(Player player){
        if(!player.validName())
            return false;   //player's name is not valid
        if(!(players.isEmpty())) {
            for (Player person : players) {
                if (person.getName().equals(player.getName()))
                    return false;    //player fails the model, name is a repeat
            }
        }
        return true;   //player is not in the list
    }

    /**
     * Adds a player to current number of players online
     * @param player: The current user
     * @return true if the player was added, false if the player name is already in the database
     */
    public boolean addPlayer(Player player){
        if((checkPlayer(player))) {
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

    /**
     * Get number of players in the lobby - current player
     * @return int for the number of players
     */
    public int getNumPlayers(){
        return this.players.size();
    }

}
