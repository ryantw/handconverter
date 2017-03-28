package com.imryandude.poker;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by ryan on 3/27/17.
 */
public class CashGame {
    private String tableId;
    private String gameType;
    private Date start, end;
    private String filePath;
    private String fileName;
    private float smallBlind;
    private float bigBlind;
    private StringBuilder dirtyFile;
    private ArrayList<PokerHand> hands;
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public CashGame(String filePath){
        this.filePath = filePath;
        parseFileName();
        parseTableInfo();
    }

    public void parseFileName(){
        if(filePath.length() > 0){
            fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        } else {
            fileName = "";
        }
    }

    public void parseTableInfo(){
        this.tableId = parseTableId();
        parseBlinds();
    }

    // Extract table ID from filename
    public String parseTableId(){
        String tablePattern = "(.*)TBL No.(\\d+)(.*)";
        return this.fileName.replaceAll(tablePattern, "$2");
    }

    public void parseBlinds(){
        String blindPattern = "(.*)\\$(\\d+\\.\\d+)-\\$(\\d+\\.\\d+)(.*)";
        this.smallBlind = new Float(this.fileName.replaceAll(blindPattern, "$2"));
        this.bigBlind = new Float(this.fileName.replaceAll(blindPattern, "$3"));
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
            processDirtyFile();
        }
    }

    public void processDirtyFile(){
        String[] rawHands = dirtyFile.toString().split("\\n\\n");
        /*
        for(String hand: rawHands){
            hands.add(new PokerHand(hand));
        }*/
    }

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
