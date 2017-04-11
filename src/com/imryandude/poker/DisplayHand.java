package com.imryandude.poker;

/**
 * Created by ryan on 4/11/17.
 */
public class DisplayHand {
    StringBuilder handSetup = new StringBuilder();
    StringBuilder preFlop = new StringBuilder();
    StringBuilder summary = new StringBuilder();
    StringBuilder flop = null;
    StringBuilder turn = null;
    StringBuilder river = null;

    public DisplayHand(StringBuilder handSetup, StringBuilder preFlop, StringBuilder summary,
                        StringBuilder flop, StringBuilder turn, StringBuilder river){
        this.handSetup = handSetup;
        this.preFlop = preFlop;
        this.summary = summary;
        this.flop = flop;


    }
}
