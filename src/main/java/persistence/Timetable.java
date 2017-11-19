package persistence;

import java.util.HashSet;

/**
 *
 * @author Elia
 */
public class Timetable {
    
    private HashSet<Courses> courses;
    private HashSet<Teacher> teacher;
    private HashSet<Location> location;

    public Timetable() {
    }
    
    public Timetable(HashSet<Courses> courses, HashSet<Teacher> teacher, HashSet<Location> location) {
        this.courses = courses;
        this.teacher = teacher;
        this.location = location;
    }

    public HashSet<Courses> getCourses() {
        return courses;
    }

    public void setCourses(HashSet<Courses> courses) {
        this.courses = courses;
    }

    public HashSet<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(HashSet<Teacher> teacher) {
        this.teacher = teacher;
    }

    public HashSet<Location> getLocation() {
        return location;
    }

    public void setLocation(HashSet<Location> location) {
        this.location = location;
    }
    
}
