/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.classes;

/**
 *
 * @author jbacigal
 */
public class MonthSummary {
    
    Person person;
    int mealCount;

    public MonthSummary(Person person, int mealCount) {
        this.person = person;
        this.mealCount = mealCount;
    }

    public Person getPerson() {
        return person;
    }

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    @Override
    public String toString() {
        return "MonthSummary{" + "person=" + person + ", mealCount=" + mealCount + '}';
    }            
}
