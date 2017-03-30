package com.imryandude.poker;

import java.util.Date;

/**
 * Created by ryan on 3/27/17.
 */
public class PokerHand {
    private String dirtyHandHistory;
    private String handId;
    private String dateTime;
    private Date startTime;
    private String limitType;
    private CashGame session;
    private int numberOfPlayers;
    private float potSize;
    private float potRake;

    public PokerHand(String handHistory, CashGame session){
        this.dirtyHandHistory = handHistory;
        this.session = session;
        parseHandHistory();
    }

    public void parseHandHistory(){
        this.handId = parseHandHeader();
        this.dateTime = parseDateTime();
    }

    public String parseHandHeader(){
        String[] hand = handToArray();
        return hand[0].replaceAll("^Ignition Hand #(\\d+)(.*)", "$1").trim();
    }

    public String parseDateTime(){
        String[] hand = handToArray();
        return hand[0].replaceAll("(.*)(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})$", "$2");
    }

    public String[] handToArray(){ return this.dirtyHandHistory.split(System.lineSeparator()); }
    public String getHandId(){ return this.handId; }
    public String getDateTime(){ return this.dateTime; }
    public String getDirtyHandHistory(){ return this.dirtyHandHistory; }
}
