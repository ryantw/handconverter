package com.imryandude.poker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ryan on 3/27/17.
 */
public class PokerHand {
    private String dirtyHandHistory;
    private CashGameSeat[] seats;
    private String[] players;
    private StringBuilder handSetup = new StringBuilder();
    private StringBuilder preFlop = new StringBuilder();
    private StringBuilder summary = new StringBuilder();
    private StringBuilder flop = null;
    private StringBuilder turn = null;
    private StringBuilder river = null;
    private String handId;
    private String dateTime;
    private CashGame session;
    private static PlayerManager playerManager;
    private int numberOfPlayers;
    private int maxSeatNumbers;
    private int playerMap[];

    /**
     * Deconstruct hand
     * 1. Header (first line)
     * 2. Players (2nd~n)
     * Game Set up
     * +Set dealer
     * +Small Blind
     * +Big Blind
     * Preflop:
     * +Hole Cards & Action
     * Flop
     * Turn
     * River
     * Summary
     * Scan for player leave/sit/deposit & update PlayerManager
     */

    /**
     *
     * @param handHistory
     * @param session
     */
    public PokerHand(String handHistory, int maxSeatNumbers, CashGame session){
        playerManager = playerManager.getInstance();
        this.session = session;
        seats = new CashGameSeat[maxSeatNumbers];
        playerMap = new int[maxSeatNumbers];
        this.maxSeatNumbers = maxSeatNumbers;
        this.dirtyHandHistory = handHistory;
        deconstructHand();
        parseHandHistory();

        for(int i = 0; i < maxSeatNumbers; i++)
            playerMap[i] = 0;
    }

    @SuppressWarnings("Duplicates")
    public void deconstructHand(){
        String[] hand = handToArray();
        int gameSetupMarker = 0;
        int preFlopMarker = 0;
        int flopMarker = 0;
        int turnMarker = 0;
        int riverMarker = 0;

        // Game Setup Markers = 0 ~ gameSetupMarker (maybe -1)
        // Pre Flop Markers = gameSetUpMarker ~ preFlopMarker
        // Flop Marker = preFlopMarker ~ flopMarker
        // Turn Marker = flopMaker ~ turnMarker
        // River Marker = turnMarker ~ riverMarker
        // Summary Marker = riverMarker ~ hand.length
        // River Marker is never 0 as we always have a summary

        for(int i = 0; i < hand.length;i++){
            if(hand[i].startsWith(("*** HOLE CARDS ")))
                gameSetupMarker = i;
            else if(hand[i].startsWith("*** FLOP "))
                preFlopMarker = i;
            else if(hand[i].startsWith("*** TURN "))
                flopMarker = i;
            else if(hand[i].startsWith("*** RIVER "))
                turnMarker = i;
            else if(hand[i].startsWith("*** SUMMARY "))
                riverMarker = i;
        }

        // Get Summary
        for(int i = riverMarker; i < hand.length; i++)
            summary.append(hand[i] + System.lineSeparator());

        // Always a Hand Setup
        for (int i = 1; i < gameSetupMarker; i++)
            handSetup.append(hand[i] + System.lineSeparator());

        // Always hole card dealt, hand can be won
        for (int i = gameSetupMarker; i < preFlopMarker; i++) {
            preFlop.append(hand[i] + System.lineSeparator());
        }

        // We have a flop
        if (preFlopMarker != 0) {
            flop = new StringBuilder();
            // If we have flop, do we have turn?
            if(flopMarker != 0){
                for(int i = preFlopMarker; i < flopMarker; i++)
                    flop.append(hand[i] + System.lineSeparator());
            } else {
                // Won on flop gather until summary
                for(int i = preFlopMarker; i < riverMarker; i++)
                    flop.append(hand[i] + System.lineSeparator());
            }
        }

        if(flopMarker != 0 ){
            turn = new StringBuilder();
            // If we have turn, do we have river?
            if(turnMarker != 0){
                for(int i = flopMarker; i < turnMarker; i++)
                    turn.append(hand[i] + System.lineSeparator());
            } else {
                // Won on turn, gather summary
                for(int i = flopMarker; i < riverMarker; i++)
                    turn.append(hand[i] + System.lineSeparator());
            }
        }

        if(turnMarker != 0){
            river = new StringBuilder();
            for(int i = turnMarker; i < riverMarker; i++)
                river.append(hand[i] + System.lineSeparator());
        }

        /*
        StringBuilder results = new StringBuilder();

        if(flop != null)
            results.append("F ");
        else
            results.append("Won Preflop");
        if(turn != null)
            results.append("T ");
        if(river != null)
            results.append("R ");


        System.out.println(results.toString());
        */
    }

    public void parseHandHistory(){
        this.handId = parseHandHeader();
        this.dateTime = parseDateTime();
        this.numberOfPlayers = parseMaxPlayers();

        parsePlayers();
        if(getNumberOfPlayers() > 1 )
            addPlayers();
    }

    public String parseHandHeader(){
        String[] hand = handToArray();
        return hand[0].replaceAll("^Ignition Hand #(\\d+)(.*)", "$1").trim();
    }

    public String parseDateTime(){
        String[] hand = handToArray();
        return hand[0].replaceAll("^Ignition Hand (.*)(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})$", "$2");
    }

    public int parseMaxPlayers(){
        String[] hand = handToArray(this.handSetup);
        int maxPeople = 0;
        for(String line: hand){
            if(line.matches("^(Seat \\d{1}:)(.*)$"))
                maxPeople++;
        }
        return maxPeople;
    }

    public void parsePlayers(){
        this.players = new String[session.getMaxSeatNumbers()];
        String[] hand = handToArray(this.handSetup);
        for(int i = 0; i < players.length; i++){
            if(hand[i].matches("^(Seat \\d{1}:)(.*)$")) {
                int seatNumber = Integer.parseInt(hand[i].replaceAll("^Seat (\\d{1}):(.*)$", "$1"));
                players[seatNumber-1] = hand[i];
                playerMap[seatNumber -1] = 1;
            }
        }
    }

    public void addPlayers(){
        CashGameSeat[] previousPlayers = playerManager.getPreviousPlayers();
        CashGameSeat[] currentPlayers = new CashGameSeat[session.getMaxSeatNumbers()];

        // First Hand
        if(previousPlayers == null){
            previousPlayers = new CashGameSeat[session.getMaxSeatNumbers()];
            for(int i = 0; i < playerMap.length;i++) {
                previousPlayers[i] = new CashGameSeat(i+1);
                previousPlayers[i].generateUserName();
            }
        }

        for (int i = 0; i < playerMap.length; i++) {
            boolean isHero = false;
            String tablePosition;
            float playerMoney;
            currentPlayers[i] = new CashGameSeat(previousPlayers[i]);
            currentPlayers[i].setHandNumber(this.handId);

            if (playerMap[i] > 0 && players[i] != null) {
                isHero = players[i].contains("[ME]") ? true : false;
                //seatNumber = Integer.parseInt(players[i].replaceAll("^Seat (\\d{1})(.*)", "$1").trim());
                tablePosition = players[i].replaceAll("^(.*):(\\s+)(.*)(\\s+\\((.*))$", "$3").trim();
                playerMoney = Float.parseFloat(players[i].replaceAll("(.*)\\((\\$\\d*\\.?\\d*)(.*)$", "$2").replace("$", "").trim());

                if (currentPlayers[i].isEmptySeat())
                    currentPlayers[i].generateUserName();

                currentPlayers[i].setTablePosition(tablePosition);
                currentPlayers[i].setUserMoney(playerMoney);
                currentPlayers[i].setHero(isHero);
                currentPlayers[i].setSeatFull();
            } else {
                currentPlayers[i].setSeatEmpty();
            }
        }
        playerManager.addPlayers(currentPlayers);
    }


    public String[] handToArray(){ return this.dirtyHandHistory.split(System.lineSeparator()); }
    public String[] handToArray(StringBuilder hand){ return hand.toString().split(System.lineSeparator()); }
    public String getHandId(){ return this.handId; }
    public String getDateTime(){ return this.dateTime; }
    public int getNumberOfPlayers(){ return this.numberOfPlayers; }
    public String getDirtyHandHistory(){ return this.dirtyHandHistory; }
    public String getSummary() { return this.summary.toString(); }

    public void setTotalSeats(int total){
        seats = new CashGameSeat[total];
    }
}
