package com.imryandude.poker;

/**
 * Created by ryan on 4/4/17.
 */
public class CashGameSeat {
    private int seatNumber;
    private String userName;
    private float userMoney;
    boolean dealer = false;
    boolean isNewPlayer = false;

    public CashGameSeat(int seatNumber, String userName, float userMoney){
        this.seatNumber = seatNumber;
        this.userName = userName;
        this.userMoney = userMoney;
        this.isNewPlayer = true;
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
