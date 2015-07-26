/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.classes;

import java.util.Objects;

/**
 *
 * @author jbacigal
 */
public class DailyMealSummary {
    
    Meal meal;
    int orderNumber;
    int count;

    public DailyMealSummary(Meal meal, int orderNumber) {
        this.meal = meal;
        this.orderNumber = orderNumber;
        this.count = 0;
    }      

    public DailyMealSummary(Meal meal) {
        this.meal = meal;
    }
   
    public Meal getMeal() {
        return meal;
    }

    public int getCount() {
        return count;
    }        

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void addCount() {
        this.count++;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.meal);
        hash = 29 * hash + this.orderNumber;
        hash = 29 * hash + this.count;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DailyMealSummary other = (DailyMealSummary) obj;
        return Objects.equals(this.meal, other.meal);
    }

    @Override
    public String toString() {
        return orderNumber + " - " + meal;
    }
    
}
