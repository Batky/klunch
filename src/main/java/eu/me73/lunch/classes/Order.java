/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbacigal
 */
public class Order implements Serializable{
    
    private static final Logger log = LoggerFactory.getLogger(Order.class);

    private static final long serialVersionUID = 1L;    
    
    LocalDate day;
    Person person;
    int selectedSoup;
    int selectedMainMeal;    
    private final static DateTimeFormatter formaterr = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);

    public Order(LocalDate day, Person person) {
        this.day = day;
        this.person = person;
        this.selectedMainMeal = 0;
        this.selectedSoup = 0;
    }

    public Order(LocalDate day, Person person, int selectedSoup, int selectedMainMeal) {
        this.day = day;
        this.person = person;
        this.selectedSoup = selectedSoup;
        this.selectedMainMeal = selectedMainMeal;
    }

    public LocalDate getDay() {
        return day;
    }

    public Person getPerson() {
        return person;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getSelectedSoup() {
        return selectedSoup;
    }

    public void setSelectedSoup(int selectedSoup) {
        this.selectedSoup = selectedSoup;
    }

    public int getSelectedMainMeal() {
        return selectedMainMeal;
    }

    public void setSelectedMainMeal(int selectedMainMeal) {
        this.selectedMainMeal = selectedMainMeal;
    }

    @Override
    public String toString() {
        return day.format(formaterr)
                + " - "
                + person.getLongName()
                + " - Polievka: "
                + selectedSoup
                + " Hlavné jedlo: "
                + selectedMainMeal;
    }        

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.day);
        hash = 67 * hash + Objects.hashCode(this.person);
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.day, other.day)) {
            return false;
        }
        return Objects.equals(this.person, other.person);
    }
    
    
    
}
