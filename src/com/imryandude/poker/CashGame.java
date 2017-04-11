package com.imryandude.poker;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by ryan on 3/27/17.
 */
public class CashGame {
    private String tableId;
    private String gameType;
    private String filePath;
    private String fileName;
    private int maxSeatNumbers;
    private float smallBlind;
    private float bigBlind;
    private StringBuilder dirtyFile;
    private ArrayList<PokerHand> hands = new ArrayList<PokerHand>();
    private PlayerManager playerManager = PlayerManager.getInstance();
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public CashGame(String filePath){
        this.filePath = filePath;
        if(this.filePath.length() > 0) {
            parseFileName();
            parseTableInfo();
        }
    }

    public void parseFileName(){
        this.fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
    }

    public void parseTableInfo(){
        try {
            readDirtyFile();
            this.tableId = parseTableId();
            this.smallBlind = parseSmallBlind();
            this.bigBlind = parseBigBlind();
            this.maxSeatNumbers = setMaxSeatNumbers();
            this.gameType = determineGameType(this.maxSeatNumbers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Extract table ID from filename
    public String parseTableId(){
        String tablePattern = "(.*)TBL No.(\\d+)(.*)";
        return this.fileName.replaceAll(tablePattern, "$2");
    }

    public float parseSmallBlind(){
        String blindPattern = "(.*)\\$(\\d+\\.\\d+)-\\$(\\d+\\.\\d+)(.*)";
        return new Float(this.fileName.replaceAll(blindPattern, "$2"));
    }

    public float parseBigBlind(){
        String blindPattern = "(.*)\\$(\\d+\\.\\d+)-\\$(\\d+\\.\\d+)(.*)";
        return new Float(this.fileName.replaceAll(blindPattern, "$3"));
    }
    /**
     * reads in a file of poker hands that needs
     * to be converted
     */
    public void readDirtyFile() throws IOException {
        dirtyFile = new StringBuilder();

        try(Stream<String> stream = Files.lines(Paths.get(filePath), ENCODING)){
            stream.forEach(s -> dirtyFile.append(s + System.lineSeparator()));
            //stream.forEach(dirtyFile::append);
        }

        if(dirtyFile.length() > 0){
            String[] rawHands = dirtyFile.toString().split("\\n\\n");
            for(String hand: rawHands){
                hands.add(new PokerHand(hand.trim(), this));
            }
        }
    }

    public void printSessionHandIds(){
        int count = 1;
        for(PokerHand hand: this.hands){
            System.out.println("Hand #" + count + " -> " + hand.getHandId() + ":" + hand.getDateTime());
            count++;
        }
    }

    /**
     * Determine the total number of seats in session
     * @return number of seats
     */
    public int setMaxSeatNumbers(){
        int maxNumber = 0;
        if(!hands.isEmpty()) {
            for (PokerHand hand : this.hands) {

                if (hand.getNumberOfPlayers() > maxNumber)
                    maxNumber = hand.getNumberOfPlayers();
            }
        }
        return maxNumber;
    }

    public String determineGameType(int maxSeats){
        if(maxSeats == 2){
            return "Heads-Up";
        } else if (maxSeats > 2 && maxSeats < 7){
            return "6-MAX";
        } else if(maxSeats >= 7 && maxSeats <= 9){
            return "9-MAX";
        }

        return "GAME TYPE ERROR";
    }

    public int getMaxSeatNumbers() { return this.maxSeatNumbers; }
    public String getGameType() { return this.gameType; }
    public String getFileName(){
        return this.fileName;
    }
    public String getTableId() { return this.tableId; }
    public String getFilePath(){
        return this.filePath;
    }
    public String getDirtyFile(){
        return this.dirtyFile.toString();
    }

}
