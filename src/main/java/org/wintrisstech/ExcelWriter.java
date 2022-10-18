package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 221018 GreatCovers
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class ExcelWriter
{
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private OutputStream os;
    public void writeSportData(XSSFWorkbook sportDataWorkbook)
    {
        System.out.println("EW20 Writing to desktop");
        try
        {
            sportDataWorkbook.write(os);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void closeOutputStream() throws IOException
    {
        os.close();
    }
    public void openOutputStream() throws FileNotFoundException
    {
        os = new FileOutputStream(deskTopPath + "/SportData.xlsx");
    }
}
