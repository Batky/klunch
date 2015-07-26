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

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jbacigal
 */
public interface ExportInterface {
    
    /**
     * Save the Persons list, from ArrayList, if the overwrite is false the 
     * ArrayList will be added to actual list and the duplicates will be
     * updated.
     * @param persons
     */
    public void savePersons(ArrayList<Person> persons);

    /**
     * Save the orders of the person. If there exists data for Person for
     * actual day, the data will be overwriten
     * @param orders
     */
    public void saveOrders(ArrayList<Order> orders);

    /**
     * Save meals for date range
     * @param days
     */
    public void saveDays(ArrayList<Day> days);   
    
    /**
     * Save the actual prices to file
     * @param prices
     */
    public void savePrices(Map<PersonType, Double> prices);
    
}
