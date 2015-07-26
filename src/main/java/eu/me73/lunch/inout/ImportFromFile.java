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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ImportFromFile extends Import{

    public ImportFromFile(String personFile, String lunchesFile, String ordersFile, String pricesFile) {
        super(personFile, lunchesFile, ordersFile, pricesFile);
    }

    public ImportFromFile(String path) {
        super(path, "dat");
    }

    @Override
    public ArrayList<Person> getAllPersons() {
        if (users == null) {
            users = new ArrayList<>();
        } else {
            users.clear();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(personFile))){
            users = (ArrayList<Person>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ImportFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public ArrayList<Day> getAllDays() {
        if (days == null) {
            days = new ArrayList<>();
        } else {
            days.clear();
        }        
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(lunchesFile))){
            days = (ArrayList<Day>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ImportFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return days;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        } else {
            this.orders.clear();
        }        
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ordersFile))){
            orders = (ArrayList<Order>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ImportFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }
    
    private Map<PersonType, Double> loadPrices() {
        try (BufferedReader myFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.pricesFile), "windows-1250"))) {
            String line;
            while((line = myFile.readLine()) != null){
                String[] s = line.split("=");
                if (s.length >= 2) {
                    try {
                        double price = Double.parseDouble(s[1]);
                        if (s[0].toUpperCase().equals("KMENOVY")) {
                            this.prices.put(PersonType.employee, price);
                        }
                        if (s[0].toUpperCase().equals("BRIGADNIK")) {
                            this.prices.put(PersonType.partial, price);
                        }
                        if (s[0].toUpperCase().equals("NAVSTEVA")) {
                            this.prices.put(PersonType.visitor, price);
                        }
                    } catch(Exception e) {
                        System.err.println(e);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }        
        return this.prices;        
    }

    @Override
    public Map<PersonType, Double> getPrices() {
        if (this.prices == null) {
            this.prices = new HashMap<>();
            return this.loadPrices();
        } 
        
        if (this.reload) {
            this.prices.clear();
            this.loadPrices();
        }
       
        return this.prices;
    }

}
