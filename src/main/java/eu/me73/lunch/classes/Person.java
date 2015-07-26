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
public class Person implements Serializable{
    
    private static final long serialVersionUID = 2L;
    
    String name;
    String barcode;
    String id;
    String lastName;
    PersonType type;

    public Person(String id, String name, String lastName, String barcode, PersonType type) {
        this.name = name;
        this.barcode = barcode;
        this.id = id;
        this.lastName = lastName;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }
    
    public String getLongName(){
        return lastName + " " + name;
    }

    @Override
    public String toString() {
        return this.getLongName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Person other = (Person) obj;
        return Objects.equals(this.id, other.id);
    }    
    
}
