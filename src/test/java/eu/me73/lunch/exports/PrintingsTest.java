/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.exports;

import eu.me73.lunch.inout.Import;
import eu.me73.lunch.inout.ImportFromFile;
import eu.me73.lunch.inout.ImportFromOldVersion;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author batky
 */
public class PrintingsTest {
    
    private static Import testImportFromFile;
    private static Import testImportFromOldVersion;
    
    public PrintingsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testImportFromFile = new ImportFromFile("");
        testImportFromOldVersion = new ImportFromOldVersion("");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Daily method, of class Printings.
     * @throws java.lang.Exception
     */
    @Test
    public void testDaily() throws Exception {
        System.out.println("Daily from file");
        String fileName = "test";
        LocalDate day = LocalDate.now();
        Printings instance = new Printings(testImportFromFile);
        boolean expResult = false;
        boolean result = instance.Daily(fileName, day);
        assertEquals(expResult, result);
        instance = new Printings(testImportFromOldVersion);
        expResult = false;
        result = instance.Daily(fileName, day);
        assertEquals(expResult, result);
    }

    /**
     * Test of Monthly method, of class Printings.
     * @throws java.lang.Exception
     */
    @Test
    public void testMonthly() throws Exception {
        System.out.println("Monthly");
        String fileName = "";
        LocalDate month = null;
        boolean detailedVisitors = false;
        Printings instance = null;
        instance.Monthly(fileName, month, detailedVisitors);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ExportToOlymp method, of class Printings.
     * @throws java.lang.Exception
     */
    @Test
    public void testExportToOlymp() throws Exception {
        System.out.println("ExportToOlymp");
        String fileName = "";
        LocalDate month = null;
        Printings instance = null;
        instance.ExportToOlymp(fileName, month);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
