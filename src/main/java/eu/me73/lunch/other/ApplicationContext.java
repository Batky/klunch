/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author batky
 */
public final class ApplicationContext {
    
 private static final ApplicationContext ourInstance = new ApplicationContext();

    Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    private final int actualMonth;
    private int lastWorkingMonth;
    private String pathToOldData;
    private String pathToNewData;
    private String exportToOlymp;
    private String exportToExcel;
    private final String pathToIniFile;

    public static ApplicationContext getInstance() {
        return ourInstance;
    }

    private ApplicationContext() {
        log.trace("Setting application context");
        actualMonth = LocalDate.now().getMonth().getValue();
        pathToIniFile = "configuration.properties";
        lastWorkingMonth = 1;
        pathToOldData = "data/";
        pathToNewData = "newData/";
        exportToOlymp = "olymp/olymp.txt";
        exportToExcel = "excel/export.xlsx";
        File file = new File(pathToIniFile);
        if (file.exists()) {
            log.debug("Context file exists {}", pathToIniFile);
            loadContextFromFile();
        } else {
            log.debug("Context file does not exists {} creating", pathToIniFile);
            saveContextToFile();
        }
    }

    private void loadContextFromFile(){
        log.trace("Loading application properties from file {}", pathToIniFile);
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(pathToIniFile)) {
            properties.load(inputStream);
            lastWorkingMonth = Integer.parseInt(properties.getProperty("lastWorkingMonth"));
            pathToNewData = properties.getProperty("pathToNewData");
            pathToOldData = properties.getProperty("pathToOldData");
            exportToOlymp = properties.getProperty("exportToOlymp");
            exportToExcel = properties.getProperty("exportToExcel");
        } catch (FileNotFoundException e) {
            log.warn("Context file not found");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.warn("Unknown IO Error:");
            log.error(e.getMessage());
        }
    }

    public void saveContextToFile() {
        log.trace("Saving application properties to file {}", pathToIniFile);
        Properties properties = new Properties();
        try (OutputStream outputStream = new FileOutputStream(pathToIniFile)) {
            properties.setProperty("lastWorkingMonth", String.valueOf(lastWorkingMonth));
            properties.setProperty("pathToNewData", pathToNewData);
            properties.setProperty("pathToOldData", pathToOldData);
            properties.setProperty("exportToOlymp", exportToOlymp);
            properties.setProperty("exportToExcel", exportToExcel);

            properties.store(outputStream, "Created by system");

        } catch (FileNotFoundException e) {
            log.warn("Context file not found");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.warn("Unknown IO Error:");
            log.error(e.getMessage());
        }
    }

    public int getActualMonth() {
        return actualMonth;
    }

    public int getLastWorkingMonth() {
        return lastWorkingMonth;
    }

    public void setLastWorkingMonth(int lastWorkingMonth) {
        this.lastWorkingMonth = lastWorkingMonth;
    }

    public String getPathToOldData() {
        return pathToOldData;
    }

    public void setPathToOldData(String pathToOldData) {
        this.pathToOldData = pathToOldData;
    }

    public String getPathToNewData() {
        return pathToNewData;
    }

    public void setPathToNewData(String pathToNewData) {
        this.pathToNewData = pathToNewData;
    }

    public String getExportToOlymp() {
        return exportToOlymp;
    }

    public void setExportToOlymp(String exportToOlymp) {
        this.exportToOlymp = exportToOlymp;
    }

    public String getExportToExcel() {
        return exportToExcel;
    }

    public void setExportToExcel(String exportToExcel) {
        this.exportToExcel = exportToExcel;
    }    

    public String getPathToIniFile() {
        return pathToIniFile;
    }
        
}
