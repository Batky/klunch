/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.exports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        
import eu.me73.lunch.classes.DailyMealSummary;
import eu.me73.lunch.classes.MonthSummary;
import eu.me73.lunch.classes.PersonType;
import eu.me73.lunch.inout.Import;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Jozef Bacigal
 */
public class Printings {
    
    private final Logger log = LoggerFactory.getLogger(Printings.class);
    
    Import lastImport;

    /**
     * Constructor
     * @param lastImport
     */
    public Printings(Import lastImport) {
        log.trace("Printings constructor with import {}", lastImport.toString());
        this.lastImport = lastImport;
    }    
    
    private Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        
        log.trace("Creating styles");
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setFontName("Trebuchet MS");
        style = wb.createCellStyle();
        style.setFont(titleFont);
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        styles.put("title", style);
        log.trace("Created style {}", style.toString());

        Font itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)9);
        itemFont.setFontName("Trebuchet MS");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(itemFont);
        styles.put("item_left", style);
        log.trace("Created style {}", style.toString());
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        styles.put("item_right", style);
        log.trace("Created style {}", style.toString());
        
        style = wb.createCellStyle();
        Font mainFont = wb.createFont();
        mainFont.setFontHeightInPoints((short)10);
        mainFont.setFontName("Trebuchet MS");
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(mainFont);
        styles.put("styleMain", style);
        log.trace("Created style {}", style.toString());

        style = wb.createCellStyle();
        Font mainFontright = wb.createFont();
        mainFontright.setFontHeightInPoints((short)10);
        mainFontright.setFontName("Trebuchet MS");
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(mainFontright);
        styles.put("styleMainRight", style);
        log.trace("Created style {}", style.toString());

        style = wb.createCellStyle();
        Font headingFont = wb.createFont();
        headingFont.setFontHeightInPoints((short)16);
        headingFont.setFontName("Trebuchet MS");
        headingFont.setBold(true);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(headingFont);
        styles.put("styleHeading", style);
        log.trace("Created style {}", style.toString());

        style = wb.createCellStyle();
        Font boldFont = wb.createFont();
        boldFont.setFontHeightInPoints((short)10);
        boldFont.setFontName("Trebuchet MS");
        boldFont.setBold(true);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(boldFont);
        styles.put("styleBold", style);
        log.trace("Created style {}", style.toString());

        style = wb.createCellStyle();
        Font boldFontRight = wb.createFont();
        boldFontRight.setFontHeightInPoints((short)10);
        boldFontRight.setFontName("Trebuchet MS");
        boldFontRight.setBold(true);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(boldFontRight);
        styles.put("styleBoldRight", style);
        log.trace("Created style {}", style.toString());
                
        style = wb.createCellStyle();
        Font boldRedFont = wb.createFont();
        boldRedFont.setFontHeightInPoints((short)10);
        boldRedFont.setFontName("Trebuchet MS");
        boldRedFont.setBold(true);
        boldRedFont.setColor(IndexedColors.RED.getIndex());
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(boldRedFont);
        styles.put("styleBoldRed", style);
        log.trace("Created style {}", style.toString());

        style = wb.createCellStyle();
        Font boldRedFontRight = wb.createFont();
        boldRedFontRight.setFontHeightInPoints((short)10);
        boldRedFontRight.setFontName("Trebuchet MS");
        boldRedFontRight.setBold(true);
        boldRedFontRight.setColor(IndexedColors.RED.getIndex());
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(boldRedFontRight);
        styles.put("styleBoldRedRight", style);
        log.trace("Created style {}", style.toString());
        
        
        return styles;
    }
    
    /**
     * Creating excel file, daily
     * @param fileName Name of the excel file, without extension
     * @param day Day in LocalDate format
     * @return true if file successfully created
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean Daily(String fileName, LocalDate day) throws FileNotFoundException, IOException {
        
        boolean result = false;
        ArrayList<DailyMealSummary> summary = lastImport.getDailySummary(day);
        
        if (!summary.isEmpty()) {
                
            Workbook wb;
            wb = new XSSFWorkbook();                
            Map<String, CellStyle> styles = this.createStyles(wb);
            DateTimeFormatter formaterr = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
            Sheet sheet = wb.createSheet("Objednávky jedál " + day.format(formaterr));
            sheet.setColumnWidth(0, 35 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 8 * 256);
            sheet.setColumnWidth(4, 8 * 256);
            sheet.setColumnWidth(5, 10 * 256);

            Row titleRow = sheet.createRow(0);        
            titleRow.createCell(0).setCellStyle(styles.get("styleHeading"));
            Cell titleCell = titleRow.getCell(0);
            titleCell.setCellValue("Zoznam obedov na: " + day.format(formaterr));

            Row headerRow = sheet.createRow(2);
            Cell [] headerCells = new Cell[3];
            headerRow.createCell(0).setCellStyle(styles.get("styleBoldRed"));
            headerCells[0] = headerRow.getCell(0);
            for (int i=1;i<3;i++) {
                headerRow.createCell(i).setCellStyle(styles.get("styleBoldRedRight"));
                headerCells[i] = headerRow.getCell(i);
            }
            headerCells[0].setCellValue("Meno stravníka");
            headerCells[1].setCellValue("Polievka");
            headerCells[2].setCellValue("Hlavné jedlo");

            int count = lastImport.getOrdersByDate(day).size();

            Row [] infoRow = new Row[count];
            for (int i=0;i<count;i++){
                infoRow[i] = sheet.createRow(3+i);
                Cell [] infoCells = new Cell[3];

                infoRow[i].createCell(0).setCellStyle(styles.get("styleMain"));
                infoCells[0] = infoRow[i].getCell(0);
                infoRow[i].createCell(1).setCellStyle(styles.get("styleMainRight"));
                infoCells[1] = infoRow[i].getCell(1);
                infoRow[i].createCell(2).setCellStyle(styles.get("styleMainRight"));
                infoCells[2] = infoRow[i].getCell(2);

                infoCells[0].setCellValue(lastImport.getOrdersByDate(day).get(i).getPerson().toString());
                infoCells[1].setCellValue(lastImport.getOrdersByDate(day).get(i).getSelectedSoup());
                infoCells[2].setCellValue(lastImport.getOrdersByDate(day).get(i).getSelectedMainMeal());
            }        

            int sCount = summary.size();
            int sI = sCount + 2;
            int j = 0;
            boolean changed = true;

            Row [] sumRow = new Row[sI];
            for (int i=0;i<sI;i++){
                sumRow[i] = sheet.createRow(3 + i + count + 2);
                Cell [] infoCells = new Cell[2];

                sumRow[i].createCell(3).setCellStyle(styles.get("styleMainRight"));
                infoCells[1] = sumRow[i].getCell(3);

                if (changed) {
                    sumRow[i].createCell(0).setCellStyle(styles.get("styleBoldRed"));
                    infoCells[0] = sumRow[i].getCell(0);
                    if (summary.get(j).getMeal().isSoup()) {
                        infoCells[0].setCellValue("Polievky:");
                    } else {
                        infoCells[0].setCellValue("Hlavné jedlá:");
                    }
                    changed = false;
                } else {
                    sumRow[i].createCell(0).setCellStyle(styles.get("styleMain"));
                    infoCells[0] = sumRow[i].getCell(0);
                    infoCells[0].setCellValue(summary.get(j).getOrderNumber() + " - " + summary.get(j).getMeal().toString());
                    infoCells[1].setCellValue(summary.get(j).getCount());
                    j++;
                    if (j < sCount) {
                        changed = !(summary.get(j).getMeal().equals(summary.get(j-1).getMeal()));
                    }
                }

            }


            try (FileOutputStream out = new FileOutputStream(fileName + ".xlsx")) {
                wb.write(out);
            }
            result = true;
        }
        return result;
    }

    /**
     * Creating excel file, month summary
     * @param fileName Name of excel file, without extension
     * @param month Month in LocalDate format
     * @param detailedVisitors if its false, in export are the visitors hidden and is printed only summary
     */
    //TODO: Add logs
    public void Monthly(String fileName, LocalDate month, boolean detailedVisitors) {
        Workbook wb;
        wb = new XSSFWorkbook();                        
        Map<String, CellStyle> styles = this.createStyles(wb);
        DateTimeFormatter formaterr = DateTimeFormatter.ofPattern("MM.yyyy", Locale.forLanguageTag("sk"));
        log.trace("Creating first line of excel file");
        Sheet sheet = wb.createSheet("Mesačný prehľad z " + month.format(formaterr));
        log.trace("Setting column {} width {}", 0, (35 * 256));
        sheet.setColumnWidth(0, 35 * 256);
        log.trace("Setting column {} width {}", 1, (20 * 256));
        sheet.setColumnWidth(1, 20 * 256);
        log.trace("Setting column {} width {}", 2, (20 * 256));
        sheet.setColumnWidth(2, 20 * 256);
        log.trace("Setting column {} width {}", 3, (8 * 256));
        sheet.setColumnWidth(3, 8 * 256);
        log.trace("Setting column {} width {}", 4, (8 * 256));
        sheet.setColumnWidth(4, 8 * 256);
        log.trace("Setting column {} width {}", 5, (10 * 256));
        sheet.setColumnWidth(5, 10 * 256);
        
        log.trace("Created title and summary cells");        
        Row titleRow = sheet.createRow(0);        
        titleRow.createCell(0).setCellStyle(styles.get("styleHeading"));
        Cell titleCell = titleRow.getCell(0);
        titleCell.setCellValue("Zoznam obedov za " + month.format(formaterr));        
        
        Row headerRow = sheet.createRow(2);
        Cell [] headerCells = new Cell[3];
        headerRow.createCell(0).setCellStyle(styles.get("styleBoldRed"));
        headerCells[0] = headerRow.getCell(0);
        for (int i=1;i<3;i++) {
            headerRow.createCell(i).setCellStyle(styles.get("styleBoldRedRight"));
            headerCells[i] = headerRow.getCell(i);
        }
        headerCells[0].setCellValue("Meno zamestnanca");
        headerCells[1].setCellValue("Počet obedov");
        headerCells[2].setCellValue("Suma [€]");
        log.trace("Created column header cells");
        
        log.trace("Trying to create month summary of month {}", month.getMonth().toString());
        ArrayList<MonthSummary> summary = lastImport.getMonthSummary(month);
        int count = summary.size();
        log.debug("Created summary, number of records: {}", count);
        lastImport.setReload(false);
        boolean changedTypeOfPerson = false;        
        PersonType lastType = summary.get(0).getPerson().getType();
        double lastPrice = lastImport.getPrices().get(lastType);
        log.trace("Changing type of person {} and price {}", lastType.name(), lastPrice);
        int plusRow = 3;
        int mealsCount = 0;
        double mealsSum = 0;
        int totalCount = 0;
        double totalSum = 0;
        
        Row [] infoRow = new Row[count];
        for (int i=0;i<count;i++){
            
            log.debug("Creating row {}", i);
                       
            infoRow[i] = sheet.createRow(i+plusRow);
            Cell [] infoCells = new Cell[3];
            
            infoRow[i].createCell(0).setCellStyle(styles.get("styleMain"));
            infoCells[0] = infoRow[i].getCell(0);
            infoRow[i].createCell(1).setCellStyle(styles.get("styleMainRight"));
            infoCells[1] = infoRow[i].getCell(1);
            infoRow[i].createCell(2).setCellStyle(styles.get("styleMainRight"));
            infoCells[2] = infoRow[i].getCell(2);
            
            int mealCount = summary.get(i).getMealCount();
            mealsCount += mealCount;
            mealsSum += (mealCount * lastPrice);
            totalCount += mealCount;
            totalSum += (mealCount * lastPrice);
            if (!summary.get(i).getPerson().getType().equals(PersonType.visitor)) {
                infoCells[0].setCellValue(summary.get(i).getPerson().toString());
                infoCells[1].setCellValue(mealCount);            
                infoCells[2].setCellValue(mealCount * lastPrice);
            } else {
                if (detailedVisitors) {
                    infoCells[0].setCellValue(summary.get(i).getPerson().toString());
                    infoCells[1].setCellValue(mealCount);            
                    infoCells[2].setCellValue(mealCount * lastPrice);                    
                } else {
                    plusRow--;
                }
            }
            
            if ((i+1) < count) {
                PersonType beforeLastType = lastType;                
                lastType = summary.get(i + 1).getPerson().getType();                
                if (!beforeLastType.equals(lastType)) {
                    lastPrice = lastImport.getPrices().get(lastType);
                    changedTypeOfPerson = true;
                    log.trace("Changing type of person {} and price {}", lastType.name(), lastPrice);
                }
            } else {
                changedTypeOfPerson = true;
            }
            
            if (changedTypeOfPerson) {
                log.trace("Creating summary for person type");
                plusRow++;
                Row infoHeaderRow = sheet.createRow(i + plusRow);
                infoHeaderRow.createCell(0).setCellStyle(styles.get("styleBold"));
                infoHeaderRow.createCell(1).setCellStyle(styles.get("styleBoldRight"));
                infoHeaderRow.createCell(2).setCellStyle(styles.get("styleBoldRight"));
                Cell infoHeaderCell1 = infoHeaderRow.getCell(0);
                Cell infoHeaderCell2 = infoHeaderRow.getCell(1);
                Cell infoHeaderCell3 = infoHeaderRow.getCell(2);
                switch (summary.get(i - 1).getPerson().getType()) {
                    case employee: {
                        infoHeaderCell1.setCellValue("Zamestnanci spolu:");
                        infoHeaderCell2.setCellValue(mealsCount);
                        infoHeaderCell3.setCellValue(mealsSum);
                        break;
                    }
                    case partial: {
                        infoHeaderCell1.setCellValue("Dohodári spolu:");
                        infoHeaderCell2.setCellValue(mealsCount);
                        infoHeaderCell3.setCellValue(mealsSum);
                        break;                        
                    }
                    case visitor: {
                        infoHeaderCell1.setCellValue("Návštevy spolu:");
                        infoHeaderCell2.setCellValue(mealsCount);
                        infoHeaderCell3.setCellValue(mealsSum);
                        break;                        
                    }                    
                }                
                changedTypeOfPerson = false;
                plusRow++;
                mealsCount = 0;
                mealsSum = 0;            
            }
        }
        
        Row infoHeaderRow = sheet.createRow(count + plusRow);
        infoHeaderRow.createCell(0).setCellStyle(styles.get("styleBold"));
        infoHeaderRow.createCell(1).setCellStyle(styles.get("styleBoldRight"));
        infoHeaderRow.createCell(2).setCellStyle(styles.get("styleBoldRight"));
        Cell infoHeaderCell1 = infoHeaderRow.getCell(0);
        Cell infoHeaderCell2 = infoHeaderRow.getCell(1);
        Cell infoHeaderCell3 = infoHeaderRow.getCell(2);
        infoHeaderCell1.setCellValue("Spolu:");
        infoHeaderCell2.setCellValue(totalCount);
        infoHeaderCell3.setCellValue(totalSum);
        log.trace("Trying to create excel file {}.xlsx", fileName);
        try (FileOutputStream out = new FileOutputStream(fileName + ".xlsx")) {
            wb.write(out);
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        log.debug("Excel file created {}.xlsx", fileName);
    }
    
    /**
     * Creating export file for "Olymp" application
     * @param fileName Name of the export file, has to be with file extension (txt)
     * @param month Month in LocalDate format 
     */
    public void ExportToOlymp(String fileName, LocalDate month) {
        log.trace("Starting export to olymp");
        log.debug("Trying to create export file {}", fileName);
        try(BufferedWriter myFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"))){
            myFile.write("##&\tOSCISLO\tZR_STRAVNE");
            myFile.newLine();
            log.trace("Trying to get month {} summary", month.getMonth().toString());
            ArrayList<MonthSummary> summary = lastImport.getMonthSummary(month);
            log.debug("Getting month summary, number of objects: {}", summary.size());
            summary.stream().forEach((ms)->{
                try {                    
                    PersonType pt = ms.getPerson().getType();
                    if (pt == PersonType.employee) {
                        String newLine = " \t" + ms.getPerson().getId() + "\t";
//                        int mealCount = ms.getMealCount();
//                        double price = lastImport.getPrices().get(pt);
                        double sum = (double)Math.round((ms.getMealCount() * lastImport.getPrices().get(pt)) * 100) / 100d;
                        newLine += Double.toString(sum);
                        myFile.write(newLine);                    
                        myFile.newLine();
                        log.debug("Writting line: {}", newLine);
                    }
                } catch (IOException e) {
                    log.warn("IO Exception error by creating export file {}", fileName);
                    log.error(e.getLocalizedMessage());
                }
            });
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

}
