package com.imryandude.poker;

import java.util.ArrayList;

/**
 * Created by ryan on 4/8/17.
 */
public class PlayerManager {
    private static PlayerManager players;
    private ArrayList<CashGameSeat[]> playerHands = new ArrayList<>();

    private PlayerManager(){
        // Nadda
    }

    static {
        players = new PlayerManager();
    }

    public static PlayerManager getInstance(){
        return players;
    }

    public CashGameSeat[] getPreviousPlayers(){
        try {
            return playerHands.get(playerHands.size() - 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public CashGameSeat[] getPreviousPlayers(int index){
        try {
            return playerHands.get(index - 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public CashGameSeat[] getNextPlayers(int index){
        try {
            return playerHands.get(index + 1);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public CashGameSeat[] getPlayers(int handNumber){
        try {
            return playerHands.get(handNumber);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void addPlayers(CashGameSeat[] players){
        playerHands.add(players);
    }

    public void updatePlayer(int handNumber, int playerSeatNumber, String playerName){

    }

    public void updatePlayer(int handNumber, int playerSeatNumber){

    }
}
