package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221018 GreatCovers
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.wintrisstech.Main.*;
public class DataCollector
{
    private static HashMap<String, String> bet365HomeTeamOdds = new HashMap<>();
    private static HashMap<String, String> bet365AwayTeamOdds = new HashMap<>();
    private static HashMap<String, String> bet365Odds = new HashMap<>();
    private static ArrayList<String> thisWeekMatchuplist = new ArrayList<>();
    private static ArrayList<String> homeAmericanOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeAmericanOddsMap = new HashMap<>();
    private static ArrayList<String> homeDecimalOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeDecimalOddsMap = new HashMap<>();
    private static ArrayList<String> homeFractionalOddsArray = new ArrayList<>();
    private static HashMap<String, String> homeFractionalOddsMap = new HashMap<>();
    private static ArrayList<String> awayAmericanOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayAmericanOddsMap = new HashMap<>();
    private static ArrayList<String> awayDecimalOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayMLoddsMap = new HashMap<>();
    private static HashMap<String, String> homeMLoddsMap = new HashMap<>();
    private static ArrayList<String> awayFractionalOddsArray = new ArrayList<>();
    private static HashMap<String, String> awayFractionalOddsMap = new HashMap<>();
    private static HashMap<String, String> totalHomeOpenOddsMap = new HashMap<>();
    private static HashMap<String, String> totalHomeCloseOddsMap = new HashMap<>();
    private HashMap getHomeTotalCloseOddsMap = new HashMap<String, String>();
    private HashMap getHomeTotalOpenOddsMap = new HashMap<String, String>();
    private HashMap<String, String> mlHomeOdds = new HashMap<String, String>();
    private HashMap<String, String> mlAwayOdds = new HashMap<String, String>();
    private String dataEventId;
    private String MLhomeOdds;
    private String MLawayOdds;
    private String homeNickname;//e.g. Browns...data-home-team-nickname-search
    private String awayTeamNickname;//e.g Texans...data-away-team-nickname-search
    private String awayTeamFullName;//e.g. Cleveland...data-home-team-fullname-search
    private String homeTeamFullName;//e.g Houston...data-home-team-fullname-search
    private String awayCompleteName;//e.g. Kansas City Chiefs
    private String homeCompleteName;//e.g Houston Texans
    private String gameIdentifier;//e.g 2020 - Houston Texans @ Kansas City Chiefs
    private String awayTeamScore;
    private String homeTeamScore;
    private String gameDate;
    private String awayTeamCity;
    private String homeTeamCity;
    private String thisWeek;
    private String thisSeason;
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private ArrayList<String> atsHomes = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> gameDatesMap = new HashMap<>();
    public static HashMap<String, String> gameIdentifierMap = new HashMap<>();
    public static HashMap<String, String> homeFullNameMap = new HashMap<>();
    private HashMap<String, String> awayFullNameMap = new HashMap<>();
    private HashMap<String, String> homeShortNameMap = new HashMap<>();
    private HashMap<String, String> awayShortNameMap = new HashMap<>();
    private HashMap<String, String> atsHomeMap = new HashMap<>();
    //private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouHomeMap = new HashMap<>();
    private HashMap<String, String> ouOversMap = new HashMap<>();
    private HashMap<String, String> cityNameMap = new HashMap<>();
    private HashMap<String, String> idXref = new HashMap<>();
    private HashMap<String, String> homeTotalOpenOddsMap = new HashMap<>();
    private HashMap<String, String> homeTotalCloseOddsMap = new HashMap<>();
    private String[] bet365OddsArray = new String[6];
    private String homeTeamShortName;
    private String awayTeamShortName;
    private HashMap<String, String> homeTeamCompleteNameMap = new HashMap<>();
    private HashMap<String, String> awayTeamCompleteNameMap = new HashMap<>();
    private String awayShortName;
    private String[] gameDateTime;
    private String homeShortName;
    private String month;
    private String day;
    private HashMap<String, String> homeCityMap = new HashMap<>();
    private HashMap<String, String> homeNicknameMap = new HashMap<>();
    private XSSFWorkbook sportDataWorkbook;
    private XSSFSheet sportDataSheet;
    public void collectTeamDataForThisWeek()//From covers.com website for this week's matchups
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        Main.driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate="+ weekDate);//Web driver has all team info for this week
        for (String dataEventId : Main.xRefMap.keySet())//Build week matchup IDs array
        {
            int excelRowIndex = excelRowIndexMap.get(dataEventId);
            WebElement dataEventIdElement = Main.driver.findElement(By.cssSelector("[data-event-id='" + dataEventId + "']"));//Driver gets all team elements associated with this dataEventId

            int minute = LocalTime.now().getMinute();
            minute = (minute < 10) ? (minute + 10) : minute;//To stop time minutes = 7, e.g...should be 07 minutes
            String time = LocalDate.now() + " " + LocalTime.now().getHour() + ":" + minute;
            System.out.println("DC110...entering excel data to row: " + excelRowIndex);
            sportDataSheet.getRow(0).createCell(0);//Column A1, Report time e.g. 10/18/22 13:17
            sportDataSheet.getRow(0).getCell(0).setCellValue(time);

            String homeFullName = dataEventIdElement.getAttribute("data-home-team-fullname-search");//e.g. Dallas
            String awayFullname = dataEventIdElement.getAttribute("data-away-team-fullname-search");//e.g. Miami
            String homeNickname = dataEventIdElement.getAttribute("data-home-team-nickname-search");//e.g. Texans
            String awayNickname = dataEventIdElement.getAttribute("data-away-team-nickname-search");//e.g. Dolphins
            homeCompleteName = homeFullName + " " + homeNickname;
            awayCompleteName = awayFullname + " " + awayNickname;
            gameIdentifier = season + " - " + awayCompleteName + " @ " + homeCompleteName;//Column A1, gameIentifier e.g. 2022-Buffalo Bills @ Los Angles Rams
            sportDataSheet.getRow(excelRowIndex).createCell(0);
            sportDataSheet.getRow(excelRowIndex).getCell(0).setCellValue(gameIdentifier);

            sportDataSheet.getRow(excelRowIndex).createCell(1);//Column B2, date e.g. 2022-10-06
            sportDataSheet.getRow(excelRowIndex).getCell(1).setCellValue(Main.weekDate);

            sportDataSheet.getRow(excelRowIndex).createCell(2);//Column C3, Season
            sportDataSheet.getRow(excelRowIndex).getCell(2).setCellValue(Main.season);

            sportDataSheet.getRow(excelRowIndex).createCell(3);//Column D4 NFL week e.g. 5
            sportDataSheet.getRow(excelRowIndex).getCell(3).setCellValue("Week " + Main.weekNumber);

            sportDataSheet.getRow(excelRowIndex).createCell(10);// Column K11, Home team full name e.g. Dallas Coyboys Column K11
            sportDataSheet.getRow(excelRowIndex).getCell(10).setCellValue(homeCompleteName);

//            String totalHomeCloseOdds = String.valueOf(Main.driver.findElement(By.cssSelector("#__totalDiv-nfl-265308 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")));
//            sportDataSheet.getRow(excelRowIndex).createCell(64);// BM65 ats away odds
//            sportDataSheet.getRow(excelRowIndex).getCell(64).setCellValue(Main.atsAwayMap.get(dataEventId));
            //            homeTeamCity = cityNameMap.get(homeTeamCity);
            //            homeTeamShortName = weekElements.attr("data-home-team-shortname-search");//Home team abbreviation e.g. LAR
            //            awayTeamShortName = weekElements.attr("data-away-team-shortname-search");//Home team abbreviation e.g. BUF
            //            homeTeamCity = e.attr("data-home-team-city-search");
//            awayTeamFullName = e.attr("data-away-team-fullname-search");//e.g. Dallas
//            awayTeamNickname = e.attr("data-away-team-nickname-search");//e.g. Cowboys
//            awayTeamCity = e.attr("data-away-team-city-search");
//            awayTeamCity = cityNameMap.get(awayTeamCity);
//            awayTeamCompleteName = awayTeamCity + " " + awayTeamNickname;
//            gameIdentifier = thisSeason + " - " + awayTeamCompleteName + " @ " + homeTeamCompleteName;
//            dataEventId = e.attr("data-event-id");
//            gameDateTime = e.attr("data-game-date").split(" ");
//            gameDate = gameDateTime[0];
//            awayTeamScore = e.attr("data-away-score");
//            thisWeek = e.attr("data-competition-type");
//            thisWeekGameDates.add(gameDate);
//            gameDatesMap.put(dataEventId, gameDate);
//            gameIdentifierMap.put(dataEventId, gameIdentifier);
//            thisWeekHomeTeams.add(homeTeamCompleteName);
//            thisWeekAwayTeams.add(awayTeamCompleteName);
//            awayFullNameMap.put(dataEventId, awayTeamFullName);
//            homeShortNameMap.put(dataEventId, homeTeamShortName);
//            awayShortNameMap.put(dataEventId, awayTeamShortName);
//            homeTeamCompleteNameMap.put(dataEventId, homeTeamCompleteName);
//            awayTeamCompleteNameMap.put(dataEventId, awayTeamCompleteName);
//            thisWeekHomeTeamScores.add(homeTeamScore);
//            thisWeekAwayTeamScores.add((awayTeamScore));
//            thisGameWeekNumbers.add(thisWeek);
//            awayShortName = e.attr("data-away-team-shortname-search");//Away team
//            awayShortNameMap.put(dataEventId, awayShortName);
//            homeShortName = e.attr("data-home-team-shortname-search");//Home team
//            homeShortNameMap.put(dataEventId, homeShortName);
        }
        System.out.println("DC133 homeFullNameMap => " + homeFullNameMap);
    }
    public void collectConsensusData(WebDriver consensusElements)
    {
            try
            {
                String s = "li.covers-CoversConsensus-sides:nth-child(1) > a:nth-child(1)";//Get Money Leaders
                Main.driver.findElement(By.cssSelector(s)).click();//Money Leaders
                Main.clickCookies("DC139");
                String MlATSaway = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div[5]/div[2]/div[1]/div[2]")).getText().trim();
                System.out.println("Main85...MLATSaway " + MlATSaway);
                Main.MLATSawayMap.put(dataEventId, MlATSaway);
                System.out.println("Main81........clicked Money Leaders " + Main.MLATSawayMap);
            }
            catch (Exception e)
            {
                System.out.println("Main85.....can't find Money Leaders button.");
            }

//        Main.ouAwayMap.put(dataEventId, ouAway);
//        Main.ouHomeMap.put(String.valueOf(dataEventId), ouHome);
//        Main.atsHomeMap.put(String.valueOf(dataEventId), atsAway);
//        Main.atsAwayMap.put(String.valueOf(dataEventId), atsHome);
    }
    public void collectOddsData(WebElement moneyLineElements)
    {
//        String totalHomeCloseOdds = String.valueOf(Main.driver.findElement(By.cssSelector("#__totalDiv-nfl-265308 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")))
//        sportDataSheet.getRow(excelRowIndex).createCell(64);// BM65 ats away odds
//        sportDataSheet.getRow(excelRowIndex).getCell(64).setCellValue(Main.atsAwayMap.get(dataEventId));
//        try
//        {
//            System.out.println("DC169 Starting collectTotalHomeCloseOdds()");
//            String totalHomeCloseOdds = String.valueOf(driver.findElement(By.cssSelector("#__totalDiv-nfl-265308 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")));
//            System.out.println("DC171 totalHomeCloseOdds => " + totalHomeCloseOdds);
//        }
//        catch (Exception e)
//        {
//            System.out.println("DC175 Can't find totalHomeCloseOdds");
//            throw new RuntimeException(e);
//        }
    }
    public HashMap<String, String> getHomeFullNameMap()
    {
        return homeFullNameMap;
    }
    public HashMap<String, String> getAwayFullNameMap()
    {
        return awayFullNameMap;
    }
    public HashMap<String, String> getGameDatesMap()
    {
        return gameDatesMap;
    }
    public HashMap<String, String> getAtsHomeMap()
    {
        return atsHomeMap;
    }
    public HashMap<String, String> getAtsAwayMap()
    {
        return atsAwayMap;
    }
    public HashMap<String, String> getOuAwayMap()
    {
        return ouOversMap;
    }
    public HashMap<String, String> getOuHomeMap()
    {
        return ouHomeMap;
    }
    public HashMap<String, String> getGameIdentifierMap() {return gameIdentifierMap;}
    public void setCityNameMap(HashMap<String, String> cityNameMap)
    {
        this.cityNameMap = cityNameMap;
    }
    public HashMap<String, String> getAwayShortNameMap()
    {
        return awayShortNameMap;
    }
    public HashMap<String, String> getHomeShortNameMap()
    {
        return homeShortNameMap;
    }
    public HashMap<String, String> getHomeTeamCompleteNameMap()
    {
        return homeTeamCompleteNameMap;
    }
    public HashMap<String, String> getAwayTeamCompleteNameMap()
    {
        return awayTeamCompleteNameMap;
    }
    public HashMap<String, String> getGetHomeTotalCloseOddsMap()
    {
        return getHomeTotalCloseOddsMap;
    }
    public HashMap<String, String> getGetTotalHomeOpenOddsMap()
    {
        return getHomeTotalOpenOddsMap;
    }
    public HashMap<String, String> getTotalHomeOpenOddsMap()
    {
        return totalHomeOpenOddsMap;
    }
    public HashMap<String, String> getTotalHomeCloseOddsMap()
    {
        return totalHomeCloseOddsMap;
    }
    public HashMap<String, String> getAwayMLoddsMap()
    {
        return awayMLoddsMap;
    }
    public HashMap<String, String> getHomeMLoddsMap()
    {
        return awayMLoddsMap;
    }
    public void setThisSeason(String thisSeason)
    {
        this.thisSeason = thisSeason;
    }
    public void setSportDataWorkbook(XSSFWorkbook sportDataWorkbook)
    {
        this.sportDataWorkbook = sportDataWorkbook;
    }
}

