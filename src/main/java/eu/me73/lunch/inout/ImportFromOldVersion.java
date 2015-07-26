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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbacigal
 */
public class ImportFromOldVersion extends Import{

    private static final Logger log = LoggerFactory.getLogger(ImportFromOldVersion.class);

    public ImportFromOldVersion(String personFile, String lunchesFile, String ordersFile, String pricesFile) {
        super(personFile, lunchesFile, ordersFile, pricesFile);
    }

    public ImportFromOldVersion(String path) {
        super(path, "txt");
    }

    private ArrayList<Person> loadAllPersons(){
        log.trace("Trying to read users from file {}", this.personFile);
        try (BufferedReader myFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.personFile),"windows-1250"))) {
                String line;
                //users.clear();
                while ((line = myFile.readLine()) != null){
                    log.trace("Readed line from file: {}", line);
                    String[] s = line.split(";");
                    if (s.length == 4) {
                        String nameSplit[] = s[1].split(" ");
                        PersonType type = PersonType.employee;
                        if (s[3].equals("true")) {
                            type = PersonType.partial;
                        }
                        if (nameSplit[1].equals("Návšteva")) {
                            type = PersonType.visitor;
                        }
                        Person user = new Person(s[0], nameSplit[0], nameSplit[1], s[2], type);
                        log.trace("Adding user {}", user);
                        users.add(user);
                    }
                }
        }
        catch(IOException e){
            log.warn("IOException:");
            log.error(e.getMessage());
        }
        log.trace("Returning {} users", users.size());
        return users;        
    }
    
    @Override
    public ArrayList<Person> getAllPersons() {                
        if (users == null) {
            log.debug("Users are null: creating array list");
            users = new ArrayList<>();
            return this.loadAllPersons();
        } 
        
        log.trace("Reload is set to ", this.reload);
        
        if (this.reload) {
            log.debug("Users containing {} users, clearing array list", users.size());
            users.clear();
            this.loadAllPersons();
        }
        
        return this.users;
    }

    @Override
    public ArrayList<Day> getAllDays() {
        if (days == null) {
            days = new ArrayList<>();
        } else {
            days.clear();
        }
        try (BufferedReader myFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.lunchesFile), "windows-1250"))) {
            String line;
            while((line = myFile.readLine()) != null){
                String[] s = line.split(";");
                if (s.length == 8) {
                    DateTimeFormatter formaterr = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);                    
                    Day d = new Day(LocalDate.parse(s[0], formaterr));
                    d.addSoup(s[1].trim());
                    d.addSoup(s[2].trim());
                    d.addMainMeal(s[3].trim());
                    d.addMainMeal(s[4].trim());
                    d.addMainMeal(s[5].trim());
                    d.addMainMeal(s[6].trim());
                    d.addMainMeal(s[7].trim());
                    days.add(d);
                }
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
        return this.days;
    }
   
    //TODO:Add logs for load all orders
    public ArrayList<Order> loadAllOrders() {
        log.trace("Trying to read orders from file {}", this.ordersFile);
        try (BufferedReader myFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.ordersFile), "windows-1250"))) {
            String line;
            while((line = myFile.readLine()) != null){
                String[] s = line.split(";");
                if (s.length >= 4) {
                    DateTimeFormatter formaterr = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.ENGLISH);
                    try {
                        int iSoup = Integer.parseInt(s[2]);
                        int iMainMeal = Integer.parseInt(s[3]);
                        LocalDate ld = LocalDate.parse(remakeOldDateString(s[0]), formaterr);
                        if ((iMainMeal != 99) && (ld.getYear() > 2014)) {                        
                            Order order = new Order(ld, this.getPersonByID(s[1]), iSoup, iMainMeal);
                            if (orders.contains(order)) {
                                orders.remove(order);
                            }
                            orders.add(order);
                        } else {
                            if ((iMainMeal == 99) && (ld.getYear() > 2014)) {
                                Order order = new Order(ld, this.getPersonByID(s[1]), iSoup, iMainMeal);
                                if (orders.contains(order)) {
                                    orders.remove(order);
                                }
                            }
                        }                       
                    } catch(Exception e) {
                        log.warn("IOException:");
                        log.error(e.getMessage());

                    }
                }
            }
        } catch (IOException e) {
            log.warn("IOException:");
            log.error(e.getMessage());
        }
        log.debug("Returning {} orders", orders.size());
        return this.orders;
    }
    
    @Override
    public ArrayList<Order> getAllOrders(){
        
        if (orders == null) {
            log.debug("Orders are null: creating array list");
            orders = new ArrayList<>();
            return this.loadAllOrders();
        } 
        
        log.trace("Reload is set to ", this.reload);
        
        if (this.reload) {
            log.debug("Orders containing {} orders, clearing array list", orders.size());
            orders.clear();
            this.loadAllOrders();
        }
        
        return this.orders;        
        
    }
    
    private Map<PersonType, Double> loadPrices(){
        try (BufferedReader myFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.pricesFile), "windows-1250"))) {
            String line;
            while((line = myFile.readLine()) != null){
                String[] s = line.split("=");
                if (s.length >= 2) {
                    try {
                        String [] separateValue = s[1].split(",");
                        double price = Double.parseDouble(separateValue[0]);
                        price += Double.parseDouble(separateValue[1]) / Math.pow(10, separateValue[1].length());
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
