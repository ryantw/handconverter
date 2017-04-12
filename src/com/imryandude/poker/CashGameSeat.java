package com.imryandude.poker;

import java.util.Random;

/**
 * Created by ryan on 4/4/17.
 *
 */
public class CashGameSeat {
    private String handNumber;
    private int seatNumber;
    private String userName;
    private String tablePosition;
    private float userMoney;
    boolean isHero = false;
    boolean dealer = false;
    boolean isNewPlayer = false;
    private static Random random = new Random();
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CashGameSeat(){

    }

    public CashGameSeat(String handNumber, int seatNumber, String tablePosition, float userMoney, boolean isHero, boolean isNew){
        this.handNumber = handNumber;
        this.seatNumber = seatNumber;
        this.tablePosition = tablePosition;
        this.userMoney = userMoney;
        if(isNew && !isHero)
            this.userName = generateUserName();
        if(isHero)
            this.userName = "Hero";

        System.out.println(userName);
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
}
