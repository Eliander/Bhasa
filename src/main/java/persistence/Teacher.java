package persistence;

import java.util.Objects;

public class Teacher {

    private String name;
    private String valore;

    public Teacher(String name, String value) {
        this.name = name;
        this.valore = value;
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
