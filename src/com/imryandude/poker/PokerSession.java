package com.imryandude.poker;

import java.util.ArrayList;

/**
 * Created by ryan on 3/27/17.
 */

public class PokerSession {
    // Session will hold game types and table

    public static void main(String args[]){
        ArrayList<CashGame> games;
        String gameType = "cash";

        if(gameType.equals("cash")){
            // Loop through directory and new instance of Cash Game
            CashGame session = new CashGame("resources/HH20170324-213135 - 5219195 - RING - $0.10-$0.25 - HOLDEM - NL - TBL No.11464775.txt");
            for(int i = 0; i < 271; i++)
                session.requestHandInfo(i);
        }

    }
}
