package com.imryandude.poker;

import java.util.Random;

/**
 * Created by ryan on 4/4/17.
 *
 */
public class CashGameSeat {
    private String handNumber;
    private int seatNumber = 0;
    private String userName = null;
    private String tablePosition;
    private float userMoney;
    boolean isHero = false;
    boolean dealer = false;
    boolean emptySeat = true;
    private static Random random = new Random();
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CashGameSeat(){
        emptySeat = true;
    }

    public CashGameSeat(String handNumber, int seatNumber, String tablePosition, float userMoney, boolean isHero){
        this.handNumber = handNumber;
        this.seatNumber = seatNumber;
        this.tablePosition = tablePosition;
        this.userMoney = userMoney;
        this.emptySeat = false;
        if(!isHero)
            this.userName = generateUserName();
        else
            this.userName = "Hero";
    }

    private String generateUserName(){
        StringBuilder userName = new StringBuilder("PLR_");
        for(int i = 0; i < 7; i++)
            userName.append(randomInt());

        for(int i = 0; i < 2; i++)
            userName.append(randomChar());

        return userName.toString();
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

    public void setHero(boolean hero){ this.isHero = hero; }

    public void setHandNumber(String handNumber) { this.handNumber = handNumber; }

    public String getHandNumber(){ return this.handNumber; }

    public boolean isEmptySeat() { return this.emptySeat; }
}
