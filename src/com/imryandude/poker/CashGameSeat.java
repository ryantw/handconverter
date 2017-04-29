package com.imryandude.poker;

import java.util.Random;

/**
 * Created by ryan on 4/4/17.
 *
 * Move random somewhere else, a lot of instances.
 */
public class CashGameSeat {
    private String handNumber;
    private CashGameSeat copy;
    private int seatNumber = 0;
    private String userName = null;
    private String tablePosition;
    private float userMoney;
    boolean isHero = false;
    boolean dealer = false;
    boolean emptySeat = true;
    private static Random random = new Random();
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CashGameSeat(CashGameSeat copy){
        this.seatNumber = copy.getSeatNumber();
        if(!copy.isEmptySeat()){
            this.userName = copy.getUserName();
            this.tablePosition = copy.getTablePosition();
            this.userMoney = copy.getUserMoney();
            this.isHero = copy.getHero();
            this.emptySeat = false;
            //System.out.println("Username: " + this.userName);
        }

    }
    public CashGameSeat(int i){
        this.emptySeat = false;
        this.setSeatNumber(i);
    }

    public void generateUserName(){
        StringBuilder userName = new StringBuilder("PLR_");
        for(int i = 0; i < 7; i++)
            userName.append(randomInt());

        for(int i = 0; i < 2; i++)
            userName.append(randomChar());

        this.userName = userName.toString();
    }

    public int randomInt(){
        return random.nextInt(10);
    }
    public char randomChar(){
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(float userMoney) {
        this.userMoney = userMoney;
    }

    public String getTablePosition() { return this.tablePosition; }

    public void setTablePosition(String position){ this.tablePosition = position; }

    public boolean getHero() { return this.isHero; }

    public void setHero(boolean hero){ this.isHero = hero; if(hero){ this.userName = "Hero"; } }

    public void setHandNumber(String handNumber) { this.handNumber = handNumber; }

    public String getHandNumber(){ return this.handNumber; }

    public boolean isEmptySeat() { return this.emptySeat; }

    public void setSeatFull() { this.emptySeat = false; }

    public void setSeatEmpty(){
        this.emptySeat = true;
        this.userName = null;
        this.isHero = false;
        this.tablePosition = null;
        this.userMoney = 0;
    }
}
