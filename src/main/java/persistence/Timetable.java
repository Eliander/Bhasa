package persistence;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Elia
 */
public class Timetable {
    
    private ArrayList<Courses> courses;

    public Timetable() {
        
       this.courses = new ArrayList();
    }
    
    public Timetable(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    public ArrayList<Courses> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }
    
    public void addCourses(Courses courses) {
        ArrayList<Courses> c = this.getCourses();
        c.add(courses);
        setCourses(c);
    }

    @Override
    public String toString() {
        String result = "";
        for(Courses c : courses){
            result += c.toString();
        }
        return result;
    }    
    
}
