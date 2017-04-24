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
    private int totalHands;
    private String tableId;
    private String gameType;
    private String filePath;
    private String fileName;
    private int maxSeatNumbers;
    private float smallBlind;
    private float bigBlind;
    private StringBuilder dirtyFile;
    private ArrayList<PokerHand> hands = new ArrayList<>();
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
            this.maxSeatNumbers = determineMaxSeatNumbers();
            this.tableId = parseTableId();
            this.smallBlind = parseSmallBlind();
            this.bigBlind = parseBigBlind();
            this.gameType = determineGameType(this.maxSeatNumbers);
            createHands();
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
    }

    public void createHands(){
        if(dirtyFile.length() > 0){
            String[] rawHands = dirtyFile.toString().split(System.lineSeparator()+System.lineSeparator());
            System.out.println(rawHands.length);
            for(String hand: rawHands){
                hands.add(new PokerHand(hand.trim(), getMaxSeatNumbers(), this));
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
    public int determineMaxSeatNumbers(){
        int maxSeats = -1;
        String[] rawHands = dirtyFile.toString().split(System.lineSeparator()+System.lineSeparator());
        for(int i = 0; i < rawHands.length;i++){
            int handSeats = 0;
            String[] handLines = rawHands[i].split(System.lineSeparator());
            for(String line: handLines){
                if(line.matches("^Seat \\d{1}: .*$")) {
                    handSeats++;
                }
                if(line.matches("^Dealer : .*$"))
                    break;
            }
            if(handSeats > maxSeats)
                maxSeats = handSeats;
        }
        return maxSeats;
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

    public void requestHandInfo(int handNumber){
        CashGameSeat[] handPlayers = playerManager.getPlayers(handNumber);
        PokerHand hand = hands.get(handNumber);

        // Hand must exist if not null
        if(handPlayers != null){
            System.out.println("Players");
            for(CashGameSeat player: handPlayers){
                System.out.printf("(%s) %s, Seat %d ($%.2f)\n", player.getHandNumber(), player.getUserName(), player.getSeatNumber(), player.getUserMoney());
            }
            System.out.print(hand.getSummary());
        }
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
