/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.classes;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author jbacigal
 */
public class Meal implements Serializable{

    private static final long serialVersionUID = 1L;
    
    MealType mealType;
    String name;

    public Meal(MealType mealType, String name) {
        this.mealType = mealType;
        this.name = name;
    }

    public Meal(String name) {
        this.mealType = MealType.MainMeal;
        this.name = name;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSoup(){
        return (this.mealType == MealType.Soup);
    }
    
    public boolean isMainMeal(){
        return (this.mealType == MealType.MainMeal);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.mealType);
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final Meal other = (Meal) obj;
        return this.mealType == other.mealType;
    }
        
}
