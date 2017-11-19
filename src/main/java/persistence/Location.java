/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.Objects;

/**
 *
 * @author Elia
 */
public class Location {
    
    private String label;
    private String valore;

    public Location(String label, String valore) {
        this.label = label;
        this.valore = valore;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.valore);
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return valore == otherLocation.valore;
        } else {
            return false;
        }
    }
    
    
    
}
