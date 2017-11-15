package persistence;

public class Teacher {

    private String surname;
    private String name;
    private String value;

    public Teacher(String surname, String name, String value) {
        this.surname = surname;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
