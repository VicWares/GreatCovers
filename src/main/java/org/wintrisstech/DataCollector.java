package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221015 GreatCovers
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;

import static org.wintrisstech.Main.atsAwayMap;
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
    private String homeTeamNickname;//e.g. Browns...data-home-team-nickname-search
    private String awayTeamNickname;//e.g Texans...data-away-team-nickname-search
    private String awayTeamFullName;//e.g. Cleveland...data-home-team-fullname-search
    private String homeTeamFullName;//e.g Houston...data-home-team-fullname-search
    private String awayTeamCompleteName;//e.g. Kansas City Chiefs
    private String homeTeamCompleteName;//e.g Houston Texans
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
    private HashMap<String, String> gameIdentifierMap = new HashMap<>();
    private HashMap<String, String> homeFullNameMap = new HashMap<>();
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
    public void collectTeamInfo(Elements weekElements)//From covers.com website for this week's matchups
    {
        for (Element e : weekElements)//Build week matchup IDs array
        {
            month = e.attr("");
            homeTeamFullName = e.attr("data-home-team-fullname-search");//e.g. Houston...correcting for different city/name usage
            homeTeamNickname = e.attr("data-home-team-nickname-search");//e.g. Texans
            homeTeamShortName = weekElements.attr("data-home-team-shortname-search");//Home team abbreviation e.g. LAR
            awayTeamShortName = weekElements.attr("data-away-team-shortname-search");//Home team abbreviation e.g. BUF
            homeTeamCity = e.attr("data-home-team-city-search");
            homeTeamCity = cityNameMap.get(homeTeamCity);
            homeTeamCompleteName = homeTeamCity + " " + homeTeamNickname;
            awayTeamFullName = e.attr("data-away-team-fullname-search");//e.g. Dallas
            awayTeamNickname = e.attr("data-away-team-nickname-search");//e.g. Cowboys
            awayTeamCity = e.attr("data-away-team-city-search");
            awayTeamCity = cityNameMap.get(awayTeamCity);
            awayTeamCompleteName = awayTeamCity + " " + awayTeamNickname;
            gameIdentifier = thisSeason + " - " + awayTeamCompleteName + " @ " + homeTeamCompleteName;
            dataEventId = e.attr("data-event-id");
            gameDateTime = e.attr("data-game-date").split(" ");
            gameDate = gameDateTime[0];
            awayTeamScore = e.attr("data-away-score");
            thisWeek = e.attr("data-competition-type");
            thisWeekGameDates.add(gameDate);
            gameDatesMap.put(dataEventId, gameDate);
            gameIdentifierMap.put(dataEventId, gameIdentifier);
            thisWeekHomeTeams.add(homeTeamCompleteName);
            thisWeekAwayTeams.add(awayTeamCompleteName);
            homeFullNameMap.put(dataEventId, homeTeamFullName);
            awayFullNameMap.put(dataEventId, awayTeamFullName);
            homeShortNameMap.put(dataEventId, homeTeamShortName);
            awayShortNameMap.put(dataEventId, awayTeamShortName);
            homeTeamCompleteNameMap.put(dataEventId, homeTeamCompleteName);
            awayTeamCompleteNameMap.put(dataEventId, awayTeamCompleteName);
            thisWeekHomeTeamScores.add(homeTeamScore);
            thisWeekAwayTeamScores.add((awayTeamScore));
            thisGameWeekNumbers.add(thisWeek);
            awayShortName = e.attr("data-away-team-shortname-search");//Away team
            awayShortNameMap.put(dataEventId, awayShortName);
            homeShortName = e.attr("data-home-team-shortname-search");//Home team
            homeShortNameMap.put(dataEventId, homeShortName);
        }
    }
    public void collectConsensusData(Elements thisMatchupConsensus, String dataEventId)
    {
        this.dataEventId = String.valueOf(dataEventId);
        String ouAway = null;
        String ouHome = null;
        String atsHome = null;
        String atsAway = null;
        Elements rightConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersright");//Home/Under
        Elements leftConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersleft");//Away/Over
        try//To catch missing consensus data due to delayed or cancelled game
        {
            ouHome = rightConsensus.select("div").get(1).text();
            System.out.println("DC145 ouHome => " + ouHome);
            ouAway = leftConsensus.select("div").get(1).text();
            atsAway = leftConsensus.select("div").get(0).text();
            atsHome = rightConsensus.select("div").get(0).text();
        }
        catch (Exception e)
        {
            System.out.println("DC151 DataCollector, no consensus data for " + gameIdentifier);
        }
        Main.ouAwayMap.put(dataEventId, ouAway);
        Main.ouHomeMap.put(String.valueOf(dataEventId), ouHome);
        Main.atsHomeMap.put(String.valueOf(dataEventId), atsAway);
        Main.atsAwayMap.put(String.valueOf(dataEventId), atsHome);
    }
    public void collectOddsData(WebElement moneyLineElements)
    {
        try
        {
            System.out.println("DC169 Starting collectTotalHomeCloseOdds()");
            String totalHomeCloseOdds = String.valueOf(Main.driver.findElement(By.cssSelector("#__totalDiv-nfl-265308 > table:nth-child(2) > tbody:nth-child(3) > tr:nth-child(2) > td:nth-child(9) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1)")));
            System.out.println("DC171 totalHomeCloseOdds => " + totalHomeCloseOdds);
        }
        catch (Exception e)
        {
            System.out.println("DC175 Can't find totalHomeCloseOdds");
            throw new RuntimeException(e);
        }
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
}

