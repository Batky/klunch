/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author jbacigal
 */
public class Day implements Serializable{
    
    private static final long serialVersionUID = 1L;

    ArrayList<Meal> mealOfTheDay;
    LocalDate date;

    public Day(LocalDate date) {
        this.date = date;
        this.mealOfTheDay = new ArrayList<>();
    }

    public ArrayList<Meal> getMealOfTheDay() {
        return mealOfTheDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
        
    public ArrayList<Meal> getSoupsOfTheDay() {
        return new ArrayList<>(this.mealOfTheDay.stream().filter((meal)->(meal.isSoup())).collect(Collectors.toList()));
    }

    public ArrayList<Meal> getMainMealsOfTheDay() {
        return new ArrayList<>(this.mealOfTheDay.stream().filter((meal)->(meal.isMainMeal())).collect(Collectors.toList()));
    }
    
    public void addMeal(MealType mealType, String mealName){
        this.mealOfTheDay.add(new Meal(mealType, mealName));
    }
    
    public void addSoup(String name){
        this.addMeal(MealType.Soup, name);
    }
    
    public void addMainMeal(String name){
        this.addMeal(MealType.MainMeal, name);
    }

    public void setMealOfTheDay(ArrayList<Meal> mealOfTheDay) {
        this.mealOfTheDay = mealOfTheDay;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.mealOfTheDay);
        hash = 41 * hash + Objects.hashCode(this.date);
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
        final Day other = (Day) obj;
        return Objects.equals(this.date, other.date);
    }

    @Override
    public String toString() {
        return "Day{" + "mealOfTheDay=" + mealOfTheDay + ", date=" + date + '}';
    }
    
}
