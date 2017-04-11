package com.imryandude.poker;

import java.util.ArrayList;

/**
 * Created by ryan on 4/8/17.
 */
public class PlayerManager {
    private static PlayerManager players;
    private ArrayList<ArrayList<CashGameSeat>> playerHands = new ArrayList<ArrayList<CashGameSeat>>();

    private PlayerManager(){
        // Nadda
    }

    static {
        players = new PlayerManager();
    }

    public static PlayerManager getInstance(){
        return players;
    }

    public ArrayList<CashGameSeat> getPreviousPlayers(){
        try {
            return playerHands.get(playerHands.size() - 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public ArrayList<CashGameSeat> getPreviousPlayers(int index){
        try {
            return playerHands.get(index - 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public ArrayList<CashGameSeat> getNextPlayers(int index){
        try {
            return playerHands.get(index + 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void addPlayers(ArrayList<CashGameSeat> players){
        playerHands.add(players);
    }

    public void updatePlayer(int handNumber, int playerSeatNumber, String playerName){

    }

    public void updatePlayer(int handNumber, int playerSeatNumber){

    }
}
