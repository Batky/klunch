/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.inout;

import eu.me73.lunch.classes.Day;
import eu.me73.lunch.classes.Order;
import eu.me73.lunch.classes.Person;

import java.util.ArrayList;

/**
 *
 * @author jbacigal
 */
public class Export {
    
   
//    private void updatePersons(ArrayList<Person> actualList, ArrayList<Person> newList){
//        actualList.stream().forEach((Person p) -> {
//            Person updatePerson = null;
//            updatePerson = newList.stream().filter((pNew)->pNew.equals(p)).findFirst().get();
//            if (updatePerson != null) {
//                p.setBarcode(updatePerson.getBarcode());
//                p.setName(updatePerson.getName());
//                p.setLastName(updatePerson.getLastName());
//            }
//        });
//        newList.stream().forEach((pNew) -> {
//            if (actualList.stream().noneMatch((p)->p.equals(pNew))) {
//                actualList.add(pNew);                
//            }        
//        });
//    }
    
    public void updatePerson(ArrayList<Person> actualList, Person person){
        if (actualList.stream().noneMatch((p)->p.equals(person))) {
            actualList.add(person);
        } else {
            actualList.stream().filter((p)->p.equals(person)).forEach((p)->{
                p.setBarcode(person.getBarcode());
                p.setName(person.getName());
                p.setLastName(person.getLastName());
            });
        }
    }
    
//    private void updateDays(ArrayList<Day> actualDay, ArrayList<Day> newDay){
//        actualDay.stream().forEach((d) -> {
//            Day updateDay = newDay.stream().filter((dNew)->dNew.equals(d)).findFirst().get();
//            if (updateDay != null) {
//                d.getMealOfTheDay().clear();
//                d.setMealOfTheDay(updateDay.getMealOfTheDay());
//            }
//        });
//        newDay.stream().forEach((dNew)-> {
//            if (actualDay.stream().noneMatch((d)->d.equals(dNew))) {
//                actualDay.add(dNew);
//            }
//        });
//    }
    
    public void updateDay(ArrayList<Day> actualDay, Day newDay){
        if (actualDay.stream().noneMatch((p)->p.equals(newDay))) {
            actualDay.add(newDay);
        } else {
            actualDay.stream().filter((p)->p.equals(newDay)).forEach((p)->{
                p.setMealOfTheDay(newDay.getMainMealsOfTheDay());
            });
        }
    }    
    
//    private void updateOrders(ArrayList<Order> actualOrders, ArrayList<Order> newOrders){
//        actualOrders.stream().forEach((o) -> {
//            Order updateOrder = newOrders.stream().filter((oNew)->oNew.equals(o)).findFirst().get();
//            if (updateOrder != null) {                
//                o.setSelectedMainMeal(updateOrder.getSelectedMainMeal());
//                o.setSelectedSoup(updateOrder.getSelectedSoup());
//            }
//        });
//        newOrders.stream().forEach((oNew)-> {
//            if (actualOrders.stream().noneMatch((o)->o.equals(oNew))) {
//                actualOrders.add(oNew);
//            }
//        });        
//    }
    
    public void updateOrder(ArrayList<Order> actualOrders, Order newOrder){
        if (actualOrders.stream().noneMatch((p)->p.equals(newOrder))) {
            actualOrders.add(newOrder);
        } else {
            actualOrders.stream().filter((Order p)->p.equals(newOrder)).forEach((Order p)->{
                p.setSelectedMainMeal(newOrder.getSelectedMainMeal());
                p.setSelectedSoup(newOrder.getSelectedSoup());
            });
        }      
    }       
}
