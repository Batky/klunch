/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.inout;

import eu.me73.lunch.classes.Day;
import eu.me73.lunch.classes.Order;
import eu.me73.lunch.classes.Person;
import eu.me73.lunch.classes.PersonType;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbacigal
 */
public class ExportToFile extends Export implements ExportInterface{
    
    String personFile;
    String lunchesFile;
    String ordersFile;
    String pricesFile;

    public ExportToFile(String personFile, String lunchesFile, String ordersFile, String pricesFile) {
        this.personFile = personFile;
        this.lunchesFile = lunchesFile;
        this.ordersFile = ordersFile;
        this.pricesFile = pricesFile;
    }

    public ExportToFile(String path) {
        this.personFile = path + "//zam.dat";
        this.lunchesFile = path + "//obed.dat";
        this.ordersFile = path + "//objednavky.dat";
        this.pricesFile = path + "//ceny.ini";
    }

    @Override
    public void savePersons(ArrayList<Person> persons) {
        try(ObjectOutputStream outputStream= new ObjectOutputStream(new FileOutputStream(this.personFile))) {
            outputStream.writeObject(persons);
        } catch (IOException ex) {
            Logger.getLogger(ExportToFile.class.getName()).log(Level.SEVERE, null, ex);
        }                    
    }

    @Override
    public void saveOrders(ArrayList<Order> orders) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.ordersFile))) {
            outputStream.writeObject(orders);
        } catch (IOException ex) {
            Logger.getLogger(ExportToFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void saveDays(ArrayList<Day> days) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.lunchesFile))) {
            outputStream.writeObject(days);
        } catch (IOException ex) {
            Logger.getLogger(ExportToFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void savePrices(Map<PersonType, Double> prices) {
        try(BufferedWriter myFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.pricesFile), "windows-1250"))) {            
            myFile.write("KMENOVY=");
            myFile.write(prices.get(PersonType.employee).toString());
            myFile.newLine();
            myFile.write("BRIGADNIK=");
            myFile.write(prices.get(PersonType.partial).toString());
            myFile.newLine();
            myFile.write("NAVSTEVA=");
            myFile.write(prices.get(PersonType.visitor).toString());
            myFile.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ExportToFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
