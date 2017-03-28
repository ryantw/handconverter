package com.imryandude.poker;

import java.io.IOException;

/**
 * Created by ryan on 3/27/17.
 */

public class PokerSession {
    // Session will hold game types and table

    public static void main(String args[]){
        String gameType = "cash";

        if(gameType.equals("cash")){
            CashGame session = new CashGame("/Users/ryan/code/ignition-converter/hands/HH20170324-213135 - 5219195 - RING - $0.10-$0.25 - HOLDEM - NL - TBL No.11464775.txt");
            try {
                session.readDirtyFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(session.getFileName());
            //System.out.println(session.getDirtyFile());
        }

    }
}
