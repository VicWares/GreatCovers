package org.wintrisstech;
/**********************************************************************************
 * Must be run before Selenium for initial setup
 * cd /usr/bin/
 * sudo safaridriver --enable
 * version 221013 GreatCovers
 **********************************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class Main
{
    private static String weekDate;
    private static XSSFWorkbook sportDataWorkbook;
    private static HashMap<String, String> xRefMap = new HashMap<>();
    public static WebSiteReader webSiteReader = new WebSiteReader();
    public static ExcelReader excelReader = new ExcelReader();
    public static DataCollector dataCollector = new DataCollector();
    private static Elements nflElements;
    private static Elements weekElements;
    private static String dataEventId;
    public static ExcelBuilder excelBuilder = new ExcelBuilder();
    public static ExcelWriter excelWriter = new ExcelWriter();
    private static Elements consensusElements;
    private static int excelLineNumberIndex = 3;//Start filling excel sheet after header
    private Elements oddsElements;
    private static String version = "GreatCovers 221009A";
    private static String season = "2022";
    private static String weekNumber = "1";
    public static WebDriver driver = new SafariDriver();
    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    private Actions act = new Actions(driver);
    public static HashMap<String, String> weekDateMap = new HashMap<String, String>();
    public static HashMap<String, String> cityNameMap = new HashMap<String, String>();
    public Main()
    {
        driver = new SafariDriver();
    }
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("SharpMarkets, version " + version + ", Copyright 2022 Dan Farris");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        new CityNameMapBuilder();//Builds full city name map to correct for Covers variations in team city names
        new WeekDateMapBuilder();//Builds Game dates for current week
        nflElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate);//Jsoup Elements
        weekElements = nflElements.select(".cmg_game_data.cmg_matchup_game_box");//Jsoup Elements
        driver.get("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDateMap.get(weekNumber));//Current week scores & matchups
        clickCookies(53);
        System.out.println("sleepy, sleepy...line #54");
        Thread.sleep(10000);
        xRefMap = buildXref(weekElements);//Cross-reference from dava-event-id to data-game e.g. 87700=265355.  Both are used for referencing matchups at various times!!
        sportDataWorkbook = excelReader.readSportDataWorkbook();
        System.out.println(xRefMap);
        System.out.println(weekDateMap);
        System.out.println(cityNameMap);
        for (Map.Entry<String, String> entry : xRefMap.entrySet())
        {
            dataEventId = entry.getKey();
            System.out.println("Main63 START MAIN LOOP-----------------------------------------------------START MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + xRefMap.get(dataEventId) + "-------------------------------------------------------------------------------------------START MAIN LOOP");
            String dataGame = xRefMap.get(dataEventId);
            System.out.println("dataEVentId =  " + dataEventId);
            System.out.println("dataGame =  " + dataGame);
            consensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventId);
            dataCollector.collectConsensusData(consensusElements, dataEventId);
            excelBuilder.setAtsHomesMap(dataCollector.getAtsHomeMap());
            excelBuilder.setAtsAwaysMap(dataCollector.getAtsAwayMap());
            excelBuilder.setOuOversMap(dataCollector.getOuAwayMap());
            excelBuilder.setOuUndersMap(dataCollector.getOuHomeMap());
            try
            {
                WebElement oddsButton = Main.driver.findElement(By.xpath("//a[@href='/sport/football/nfl/odds']"));
                js.executeScript("arguments[0].click();", oddsButton);
                System.out.println("Main81....Success! clicked on odds button...sleepy, sleepy 2 seconds");
                Thread.sleep(2000);
            }
            catch (Exception e)
            {
                System.out.println("Main86.....can't find odds page.");
            }
            String s = "[data-book='bet365'][data-game='" + dataGame + "'][data-type='moneyline']";//.__bookOdds.__awayOdds.__american";
            System.out.println(s);
            WebElement moneyline = driver.findElement(By.cssSelector(s));
            excelBuilder.buildExcel(sportDataWorkbook, dataEventId, excelLineNumberIndex, dataCollector.getGameIdentifierMap().get(dataEventId));
            System.out.println("Main93 END MAIN LOOP--------------------------------------------------------------END MAIN LOOP FOR dataEventId/dataGame " + dataEventId + "/" + xRefMap.get(dataEventId) + "-------------------------------------------------------------------------------------------END MAIN LOOP");
        }
        driver.close();
        excelWriter.openOutputStream();
        excelWriter.writeSportData(sportDataWorkbook);
        System.out.println("Main100........Writing to " + sportDataWorkbook);
        excelWriter.closeOutputStream();
        System.out.println("Main102......Completed GreatCovers Successfully...Hooray...Quitting Safari WebDriver");
    }
    private static void clickCookies(int sourceLineNumber)
    {
        try
        {
            WebElement cookieButton = driver.findElement(By.cssSelector("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));//Click Cookies button
            js.executeScript("arguments[0].click();", cookieButton);
            System.out.println("Clicked on Cookie Button, line# " + sourceLineNumber);
        }
        catch (Exception e)
        {
            System.out.println("NO...Click on Cookie Button, line# " + sourceLineNumber);
        }
    }
    public static class CityNameMapBuilder//#matchup_group_top > div.cmg_game_container.cmg_matchup_game.cmg_ingame.cmg_matchups_football > div.cmg_game_data.cmg_matchup_game_box > div.cmg_l_row.cmg_matchup_list_gamebox > a:nth-child(3)
    {
        public CityNameMapBuilder()
        {
            cityNameMap.put("Minneapolis", "Minnesota");//Minnesota Vikings
            cityNameMap.put("Tampa", "Tampa Bay");//Tampa Bay Buccaneers
            cityNameMap.put("Tampa Bay", "Tampa Bay");//Tampa Bay Buccaneers
            cityNameMap.put("Arlington", "Dallas");//Dallas Cowboys
            cityNameMap.put("Dallas", "Dallas");//Dallas Cowboys
            cityNameMap.put("Orchard Park", "Buffalo");//Buffalo Bills
            cityNameMap.put("Buffalo", "Buffalo");//Buffalo Bills
            cityNameMap.put("Charlotte", "Carolina");//Carolina Panthers
            cityNameMap.put("Carolina", "Carolina");//Carolina Panthers
            cityNameMap.put("Arizona", "Arizona");//Arizona Cardinals
            cityNameMap.put("Tempe", "Arizona");//Arizona Cardinals
            cityNameMap.put("Foxborough", "New England");//New England Patriots
            cityNameMap.put("New England", "New England");//New England Patriots
            cityNameMap.put("East Rutherford", "New York");//New York Giants and New York Jets
            cityNameMap.put("New York", "New York");//New York Giants and New York Jets
            cityNameMap.put("Landover", "Washington");//Washington Football Team
            cityNameMap.put("Washington", "Washington");//Washington Football Team
            cityNameMap.put("Nashville", "Tennessee");//Tennessee Titans
            cityNameMap.put("Miami", "Miami");//Miami Dolphins
            cityNameMap.put("Baltimore", "Baltimore");//Baltimore Ravens
            cityNameMap.put("Cincinnati", "Cincinnati");//Cincinnati Bengals
            cityNameMap.put("Cleveland", "Cleveland");//Cleveland Browns
            cityNameMap.put("Pittsburgh", "Pittsburgh");//Pittsburgh Steelers
            cityNameMap.put("Houston", "Houston");//Houston Texans
            cityNameMap.put("Indianapolis", "Indianapolis");//Indianapolis Colts
            cityNameMap.put("Jacksonville", "Jacksonville");//Jacksonville Jaguars
            cityNameMap.put("Tennessee", "Tennessee");//Tennessee Titans
            cityNameMap.put("Denver", "Denver");//Denver Broncos
            cityNameMap.put("Kansas City", "Kansas City");//Kansas City Chiefs
            cityNameMap.put("Las Vegas", "Las Vegas");//Los Angeles Chargers and Los Angeles Rams
            cityNameMap.put("Philadelphia", "Philadelphia");//Philadelphia Eagles
            cityNameMap.put("Chicago", "Chicago");//Chicago Bears
            cityNameMap.put("Detroit", "Detroit");//Detroit Lions
            cityNameMap.put("Green Bay", "Green Bay");//Green Bay Packers
            cityNameMap.put("Minnesota", "Minnesota");
            cityNameMap.put("Atlanta", "Atlanta");//Atlanta Falcons
            cityNameMap.put("New Orleans", "New Orleans");//New Orleans Saints
            cityNameMap.put("Los Angeles", "Los Angeles");//Los Angeles Rams
            cityNameMap.put("San Francisco", "San Francisco");//San Francisco 49ers
            cityNameMap.put("Seattle", "Seattle");//Seattle Seahawks
        }
    }
    public static class WeekDateMapBuilder
    {
        public WeekDateMapBuilder()
        {
            weekDateMap.put("1", "2022-09-08");//Season 2022 start...Week 1
            weekDateMap.put("2", "2022-09-15");//Weeks start on Thursdays
            weekDateMap.put("3", "2022-09-22");
            weekDateMap.put("4", "2022-09-29");
            weekDateMap.put("5", "2022-10-06");
            weekDateMap.put("6", "2022-10-13");
            weekDateMap.put("7", "2022-10-20");
            weekDateMap.put("8", "2022-10-27");
            weekDateMap.put("9", "2022-11-03");
            weekDateMap.put("10", "2022-11-10");
            weekDateMap.put("11", "2022-11-17");
            weekDateMap.put("12", "2022-11-24");
            weekDateMap.put("13", "2022-12-01");
            weekDateMap.put("14", "2022-12-08");
            weekDateMap.put("15", "2022-12-15");
            weekDateMap.put("16", "2022-12-22");
            weekDateMap.put("17", "2022-12-29");
            weekDateMap.put("18", "2023-01-08");
            weekDateMap.put("19", "2023-02-05");
        }
    }
    public static HashMap<String, String> buildXref(Elements weekElements)
    {
        for (Element e : weekElements)
        {
            String dataLinkString = e.attr("data-link");
            String[] dlsa = dataLinkString.split("/");
            String dataLink = dlsa[5];
            String dataEvent = e.attr("data-event-id");
            xRefMap.put(dataEvent, dataLink);
        }
        return xRefMap;
    }
}