package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221017 GreatCovers
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.apache.poi.hssf.record.ExtendedFormatRecord.CENTER;
import static org.apache.poi.hssf.record.ExtendedFormatRecord.LEFT;
import static org.wintrisstech.Main.excelRowIndexMap;
public class ExcelBuilder
{
    private String season;
    private String ouHome;
    private String ouAway;
    private String homeTeam;
    private String awayTeam;
    private String matchupDate;
    private String weekNumber;
    private String homeDivision;//M13
    private String awayDivision;//AB28
    private HashMap<String, String> homeTeamsMap = new HashMap<>();
    private HashMap<String, String> awayTeamsMap = new HashMap<>();
    public HashMap<String, String> gameDateMap = new HashMap<>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    //private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouOverMap;
    private HashMap<String, String> ouUndersMap;
    private HashMap<String, String> homeMLOddsMap = new HashMap<>();
    private HashMap<String, String> homeMoneyLineOddsMap = new HashMap<>();
    private HashMap<String, String> awayMoneyLineOddsMap = new HashMap<>();
    private HashMap<String, String> homeSpreadOddsMap = new HashMap<>();
    private HashMap<String, String> homeSpreadCloseOddsMap = new HashMap<>();
    private HashMap<String, String> homeSpreadOpenOddsMap = new HashMap<>();
    private HashMap<String, String> awaySpreadOddsMap = new HashMap<>();
    private HashMap<String, String> homeTotalOpenOddsMap = new HashMap<>();
    private HashMap<String, String> homeTotalCloseOddsMap = new HashMap<>();
    private XSSFSheet sportDataSheet;
    private XSSFWorkbook sportDataWorkBook = new XSSFWorkbook();
    private XSSFSheet sportDataUpdateSheet = null;
    private String atsHome;
    private String atsAway;
    private String completeHomeTeamName;
    private String completeAwayTeamName;
    private String gameIdentifier;
    private String awayMoneyLineOdds;
    private String homeMoneyLineOdds;
    private String awaySpreadOdds;
    private String homeSpreadOdds;
    private String awayMoneylineOdds;
    private String homeMoneylineOdds;
    private String awayTeamCompleteName;
    private String homeTeamCompleteName;
    private HashMap<String, String> homeShortNameMap;
    private HashMap<String, String> awayShortNameMap;
    private HashMap <String,String> homeCompleteNameMap = new HashMap();
    private HashMap <String,String> awayCompleteNameMap = new HashMap();//e.g Dallas Cowboys
    private HashMap<String, String> awayMoneylineCloseOddsMap = new HashMap<>();
    public static XSSFWorkbook sportDataWorkbook;
    private String mlATSaway;
    public ExcelBuilder(XSSFWorkbook sportDataWorkbook)
    {
        this.sportDataWorkbook = sportDataWorkbook;
        sportDataSheet = sportDataWorkbook.getSheet("Data");//TODO:Clean up
    }
    public void buildExcel(String dataEventID)
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        this.sportDataWorkbook = sportDataWorkbook;
        CellStyle leftStyle = sportDataWorkbook.createCellStyle();
        leftStyle.setAlignment(LEFT);
        CellStyle centerStyle = sportDataWorkbook.createCellStyle();
        centerStyle.setAlignment(CENTER);
        sportDataSheet.setDefaultColumnStyle(0, leftStyle);
        sportDataSheet.setDefaultColumnStyle(1, centerStyle);
        sportDataSheet.setColumnWidth(1, 25 * 256);
        homeTeam = homeTeamsMap.get(dataEventID);
        awayTeam = awayTeamsMap.get(dataEventID);
        matchupDate = gameDateMap.get(dataEventID);
        atsHome = Main.atsHomeMap.get(dataEventID);
        atsAway = Main.atsAwayMap.get(dataEventID);
        ouAway = Main.ouHomeMap.get(dataEventID);
        ouHome = Main.ouAwayMap.get(dataEventID);
        mlATSaway = Main.MLATSawayMap.get(dataEventID);
        season = Main.season;
        enterData();
    }
    public void enterData()//From HashMaps
    {
        int minute = LocalTime.now().getMinute();
        minute = (minute < 10) ? (minute + 10) : minute;//To stop time minutes = 7, e.g...should be 07 minutes
        String time = LocalDate.now() + " " + LocalTime.now().getHour() + ":" + minute;
        CellStyle leftStyle = sportDataWorkbook.createCellStyle();
        leftStyle.setAlignment(LEFT);
        CellStyle centerStyle = sportDataWorkbook.createCellStyle();
        centerStyle.setAlignment(CENTER);
        int eventIndex;
        for (String dataEventID : Main.xRefMap.keySet())
       {
           eventIndex = excelRowIndexMap.get(dataEventID);
           System.out.println("EB110...entering excel data to row: " + eventIndex);
           sportDataSheet.getRow(0).createCell(0);
           sportDataSheet.getRow(0).getCell(0).setCellStyle(leftStyle);
           sportDataSheet.getRow(0).getCell(0).setCellValue(time);
           sportDataSheet.getRow(eventIndex).getCell(0).setCellValue(DataCollector.gameIdentifierMap.get(dataEventID));//e.g. 2021 - Washington Football Team @ Dallas Cowboys
           sportDataSheet.getRow(eventIndex).createCell(1);
           sportDataSheet.getRow(eventIndex).getCell(1).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(1).setCellValue(Main.weekDate);
           sportDataSheet.getRow(eventIndex).createCell(2);
           sportDataSheet.getRow(eventIndex).getCell(2).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(2).setCellValue(season);
           sportDataSheet.getRow(eventIndex).createCell(3);//NFL week e.g. 5
           sportDataSheet.getRow(eventIndex).getCell(3).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(3).setCellValue("Week " + Main.weekNumber);
           sportDataSheet.getRow(eventIndex).createCell(10);// Home team full name e.g. Dallas Coyboys Column K11
           sportDataSheet.getRow(eventIndex).getCell(10).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(10).setCellValue(homeCompleteNameMap.get(dataEventID));
           sportDataSheet.getRow(eventIndex).createCell(64);//Consensus ATS away, column BM65
           sportDataSheet.getRow(eventIndex).getCell(64).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(64).setCellValue(Main.atsAwayMap.get(dataEventID));
           sportDataSheet.getRow(eventIndex).createCell(66);//Consensus ATS home BO67
           sportDataSheet.getRow(eventIndex).getCell(66).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(66).setCellValue(Main.atsHomeMap.get(dataEventID));
           sportDataSheet.getRow(eventIndex).createCell(67);//Consensus MLATSaway BP68
           sportDataSheet.getRow(eventIndex).getCell(67).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(67).setCellValue(Main.MLATSawayMap.get(dataEventID));
           sportDataSheet.getRow(eventIndex).createCell(70);//Consensus ou away BS71
           sportDataSheet.getRow(eventIndex).getCell(70).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(70).setCellValue(Main.ouAwayMap.get(dataEventID));
           sportDataSheet.getRow(eventIndex).createCell(72);//Consensus ouHome column BU73
           sportDataSheet.getRow(eventIndex).getCell(72).setCellStyle(leftStyle);
           sportDataSheet.getRow(eventIndex).getCell(72).setCellValue(String.valueOf(Main.ouHomeMap));
       }
    }
    public void setTotalOddsString(String totalOddsString)
    {
    }
    public void setHomeTeamsMap(HashMap<String, String> homeTeamsMap){this.homeTeamsMap = homeTeamsMap;}
    public void setThisWeekAwayTeamsMap(HashMap<String, String> thisWeekAwayTeamsMap){this.awayTeamsMap = thisWeekAwayTeamsMap;}
    public void setHomeShortNameMap(HashMap<String, String> homeShortNameMapMap){this.homeShortNameMap = homeShortNameMapMap;}
    public void setAwayShortNameMap(HashMap<String, String> awayShortNameMapMap){this.awayShortNameMap = awayShortNameMapMap;}

    public void setGameDateMap(HashMap<String, String> gameDateMap) {this.gameDateMap = gameDateMap;}
    public void setAtsHomeMap(HashMap<String, String> atsHomes)
    {
        this.atsHomesMap = atsHomes;
    }
    //public void setAtsAwaysMap(HashMap<String, String> atsAwayMap)
//    {
//        this.Main.atsAwaysMap = atsAwayMap;
//    }
    public void setOuOversMap(HashMap<String, String> ouOversMap){this.ouOverMap = ouOversMap;}
    public void setOuUndersMap(HashMap<String, String> ouUndersMap)
    {
        this.ouUndersMap = ouUndersMap;
    }
    public void setCompleteHomeTeamName(String completeHomeTeamName){this.completeHomeTeamName = completeHomeTeamName;}
    public void setCompleteAwayTeamName(String completeAwayTeamName){this.completeAwayTeamName = completeAwayTeamName;}
    public void setGameIdentifier(String gameIdentifier){this.gameIdentifier = gameIdentifier;}
   
    public void setSpreadOdds(String spreadOdds, String dataEventId)
    {
        String[] spreadOddsArray = spreadOdds.split(" ");
        if (spreadOddsArray.length > 0)
        {
            awaySpreadOdds = spreadOddsArray[0];
            awaySpreadOddsMap.put(dataEventId, awayMoneyLineOdds);
            homeSpreadOdds = spreadOddsArray[1];
            homeSpreadOddsMap.put(dataEventId, homeMoneyLineOdds);
        }
    }
    public void setAwayTeamCompleteName(String awayTeamCompleteName)
    {
        this.awayTeamCompleteName = awayTeamCompleteName;
    }
    public void setHomeTeamCompleteName(String homeTeamCompleteName)
    {
        this.homeTeamCompleteName = homeTeamCompleteName;
    }
    public void setAwayCompleteNameMap(HashMap<String, String> awayCompleteNameMap)
    {
        this.awayCompleteNameMap = awayCompleteNameMap;
    }
    public void setHomeCompleteNameMap(HashMap<String, String> homeCompleteNameMap) {this.homeCompleteNameMap = homeCompleteNameMap;}
    public void setTotalHomeOpenOddsMap(HashMap<String, String> homeTotalOpenOddsMap)
    {
        this.homeTotalOpenOddsMap = homeTotalOpenOddsMap;
    }
    public void setTotalHomeCloseOddsMap(HashMap<String, String> homeTotalCloseOddsMap)
    {
        this.homeTotalCloseOddsMap = homeTotalCloseOddsMap;
    }
    public void setWeekNumber(String weekNumber)
    {
        this.weekNumber = weekNumber;
    }
    public void setHomeDivision(String homeDivision)
    {
        this.homeDivision = homeDivision;
    }
    public void setAwayDivision(String awayDivision)
    {
        this.awayDivision = awayDivision;
    }
    public void setHomeSpreadCloseOddsMap(HashMap<String, String> homeSpreadCloseOddsMap)
    {
        this.homeSpreadCloseOddsMap = homeSpreadCloseOddsMap;
    }
    public void setAwayMoneylineCloseOddsMap(HashMap<String, String> awayMoneylineCloseOddsMap)
    {
        this.awayMoneylineCloseOddsMap = awayMoneylineCloseOddsMap;
    }
}
