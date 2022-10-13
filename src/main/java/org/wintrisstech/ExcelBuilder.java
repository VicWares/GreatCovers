package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221013 GreatCovers
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
    private HashMap<String, String> gameDatesMap = new HashMap<>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouOversMap;
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
    byte[] redColor = new byte[]{(byte) 255, (byte) 0, (byte) 0};
    Color color =  new Color(215,228,188);
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
    public XSSFWorkbook buildExcel(XSSFWorkbook sportDataWorkbook, String dataEventID, int eventIndex, String gameIdentifier)
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        String time = LocalDate.now() + " " + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        CellStyle leftStyle = sportDataWorkbook.createCellStyle();
        leftStyle.setAlignment(LEFT);
        CellStyle centerStyle = sportDataWorkbook.createCellStyle();
        centerStyle.setAlignment(CENTER);
        CellStyle myStyle = sportDataWorkbook.createCellStyle();
        XSSFCellStyle redStyle = sportDataWorkbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        sportDataSheet.setDefaultColumnStyle(0, leftStyle);
        sportDataSheet.setDefaultColumnStyle(1, centerStyle);
        sportDataSheet.createRow(eventIndex);
        sportDataSheet.setColumnWidth(1, 25 * 256);
        homeTeam = homeTeamsMap.get(dataEventID);
        awayTeam = awayTeamsMap.get(dataEventID);
        matchupDate = gameDatesMap.get(dataEventID);
        atsHome = atsHomesMap.get(dataEventID);
        atsAway = atsAwaysMap.get(dataEventID);
        ouAway = ouOversMap.get(dataEventID);
        ouHome = ouUndersMap.get(dataEventID);

        sportDataSheet.getRow(eventIndex).createCell(0);
        sportDataSheet.getRow(eventIndex).getCell(0).setCellStyle(leftStyle);
        sportDataSheet.getRow(0).getCell(0).setCellValue(time);
        sportDataSheet.getRow(eventIndex).getCell(0).setCellValue(gameIdentifier);//e.g. 2021 - Washington Football Team @ Dallas Cowboys

        sportDataSheet.getRow(eventIndex).createCell(1);
        sportDataSheet.getRow(eventIndex).getCell(1).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(1).setCellValue(matchupDate);

        sportDataSheet.getRow(eventIndex).createCell(2);
        sportDataSheet.getRow(eventIndex).getCell(2).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(2).setCellValue(season);

        sportDataSheet.getRow(eventIndex).createCell(3);
        sportDataSheet.getRow(eventIndex).getCell(3).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(3).setCellValue("Week " + weekNumber);

        sportDataSheet.getRow(eventIndex).createCell(10);// Home team full name e.g. Dallas Coyboys Column K11
        sportDataSheet.getRow(eventIndex).getCell(10).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(10).setCellValue(homeCompleteNameMap.get(dataEventID));

//        sportDataSheet.getRow(eventIndex).createCell(11);// Home team short name e.g. DAL Column L 12
//        sportDataSheet.getRow(eventIndex).getCell(11).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(11).setCellValue(homeShortNameMap.get(dataEventID));

//        sportDataSheet.getRow(eventIndex).createCell(14);// Home Spread Close Odds e.g. +4.0 Column O 15
//        sportDataSheet.getRow(eventIndex).getCell(14).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(14).setCellValue(homeSpreadCloseOddsMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(12);//Spread home odds, column M
//        sportDataSheet.getRow(eventIndex).getCell(12).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(12).setCellValue(homeSpreadOddsMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(17);//MoneyLine Bet365 home odds, column R
//        sportDataSheet.getRow(eventIndex).getCell(17).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(17).setCellValue(homeMoneyLineOddsMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(18);//MoneyLine Bet365 home odds, column S19
//        sportDataSheet.getRow(eventIndex).getCell(18).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(18).setCellValue(homeMoneylineOdds);
//
//        sportDataSheet.getRow(eventIndex).createCell(25);//Away team complete name Z26 e.g. Washingtopn Commanders
//        sportDataSheet.getRow(eventIndex).getCell(25).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(25).setCellValue(awayCompleteNameMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(26);//Away Short Name AA27
//        sportDataSheet.getRow(eventIndex).getCell(26).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(26).setCellValue(awayShortNameMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(31);//MoneyLine Bet365 away odds, column AF
//        sportDataSheet.getRow(eventIndex).getCell(31).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(31).setCellValue(awayMoneyLineOddsMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(33);//MoneyLine Bet365 away close odds, column AH34
//        sportDataSheet.getRow(eventIndex).getCell(33).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(33).setCellValue(awayMoneylineCloseOddsMap.get(dataEventID));
//
//        sportDataSheet.getRow(eventIndex).createCell(66);
//        sportDataSheet.getRow(eventIndex).getCell(66).setCellStyle(leftStyle);
//        sportDataSheet.getRow(eventIndex).getCell(66).setCellValue(atsHome);

        sportDataSheet.getRow(eventIndex).createCell(64);//Consensus ATS away, column BM65
        sportDataSheet.getRow(eventIndex).getCell(64).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(64).setCellValue(atsAwaysMap.get(dataEventID));

        sportDataSheet.getRow(eventIndex).createCell(70);
        sportDataSheet.getRow(eventIndex).getCell(70).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(70).setCellValue(ouAway);

        sportDataSheet.getRow(eventIndex).createCell(72);
        sportDataSheet.getRow(eventIndex).getCell(72).setCellStyle(leftStyle);
        sportDataSheet.getRow(eventIndex).getCell(72).setCellValue(ouHome);

        return sportDataWorkbook;
    }

    public void setTotalOddsString(String totalOddsString)
    {
    }
    public void setHomeTeamsMap(HashMap<String, String> homeTeamsMap){this.homeTeamsMap = homeTeamsMap;}
    public void setThisWeekAwayTeamsMap(HashMap<String, String> thisWeekAwayTeamsMap){this.awayTeamsMap = thisWeekAwayTeamsMap;}
    public void setHomeShortNameMap(HashMap<String, String> homeShortNameMapMap){this.homeShortNameMap = homeShortNameMapMap;}
    public void setAwayShortNameMap(HashMap<String, String> awayShortNameMapMap){this.awayShortNameMap = awayShortNameMapMap;}

    public void setGameDatesMap(HashMap<String, String> gameDatesMap) {this.gameDatesMap = gameDatesMap;}
    public void setAtsHomesMap(HashMap<String, String> atsHomes)
    {
        this.atsHomesMap = atsHomes;
    }
    public void setAtsAwaysMap(HashMap<String, String> atsAwayMap)
    {
        this.atsAwaysMap = atsAwayMap;
    }
    public void setOuOversMap(HashMap<String, String> ouOversMap){this.ouOversMap = ouOversMap;}
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
    public void setHomeCompleteNameMap(HashMap<String, String> homeCompleteNameMap)
    {
        this.homeCompleteNameMap = homeCompleteNameMap;
    }
    public void setTotalHomeOpenOddsMap(HashMap<String, String> homeTotalOpenOddsMap)
    {
        this.homeTotalOpenOddsMap = homeTotalOpenOddsMap;
    }
    public void setTotalHomeCloseOddsMap(HashMap<String, String> homeTotalCloseOddsMap)
    {
        this.homeTotalCloseOddsMap = homeTotalCloseOddsMap;
    }
    public void setSeason(String season)
    {
        this.season = season;
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
