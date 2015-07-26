/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.inout;

import eu.me73.lunch.classes.*;
import java.text.Collator;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author jbacigal
 */
public interface ImportInterface {
    
    Person getPersonByID(String id);
    Person getPersonByBarCode(String barCode);
    ArrayList<Person> getAllPersons();

    /**
     * Returnig all person sorted by Collation
     * @param collator
     * @return ArrayList sorted by name
     */
    ArrayList<Person> getAllPersonsSorted(Collator collator);
    
    Day getDay(LocalDate aDay);
    ArrayList<Day> getAllDays();
    
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getOrdersByDate(LocalDate day);
    Order getOrderByPersonIDAndDate(String id, LocalDate day);
    ArrayList<Order> getOrdersByMonth(LocalDate month);
    ArrayList<Order> getOrdersByYear(LocalDate year);
    
    ArrayList<DailyMealSummary> getDailySummary(LocalDate day);
    ArrayList<MonthSummary> getMonthSummary(LocalDate month);
    
}
