package persistence;

import java.util.Objects;

public class Teacher {

    private String surname;
    private String name;
    private String valore;

    public Teacher(String surname, String name, String value) {
        this.surname = surname;
        this.name = name;
        this.valore = value;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String value) {
        this.valore = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.valore);
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Teacher) {
            Teacher otherTeacher = (Teacher) other;
            return valore == otherTeacher.valore;
        } else {
            return false;
        }
    }
    
    
}
