package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 22101 GreatCovers
 * Read large SportData excel work book (SportData.xlsx) on user's desktop and return workBook
 *******************************************************************/
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
public class ExcelReader
{
    private String deskTopPath = "/Users/vicwintriss/Desktop/SportData.xlsx";
    private XSSFWorkbook sportDataWorkbook;
    private InputStream is;
    public XSSFWorkbook readSportDataWorkbook()
    {
        try
        {
            is = new FileInputStream(deskTopPath);
            sportDataWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            System.out.println("Rading => " + sportDataWorkbook);
            ExcelBuilder.sportDataWorkbook = sportDataWorkbook;
            is.close();
        }
        catch (Exception e)
        {
            System.out.println("Can't read sportDataWorkbook");
        }
        System.out.println("ER31 read sportDataWorkbook => " + sportDataWorkbook);
        return sportDataWorkbook;
    }
}