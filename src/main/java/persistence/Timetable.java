package persistence;

import java.util.ArrayList;
import java.util.*;
import normalizer.Normalizer;

/**
 *
 * @author Elia
 */
public class Timetable {
    
    private ArrayList<Courses> courses;
    private String graduation;
    
    private final Normalizer normalizer = new Normalizer();

    public Timetable() {
       this.courses = new ArrayList();
    }

    public Timetable(ArrayList<Courses> courses, String graduation) {
        this.courses = courses;
        this.graduation = graduation;
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

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    @Override
    public String toString() {
        String result = "";
        for(Courses c : courses){
            result += c.toString();
        }
        return result;
    }    
    
    public void sortTimetable(){
        ArrayList<Courses> newArray = new ArrayList();
        Map<Float, Courses> map = new HashMap();
        
        for(int i = 0; i < courses.size(); i ++){
            map.put(Float.parseFloat(normalizer.normalizeForCompare(courses.get(i).getStart())), courses.get(i));
        }
        float min;
        while(map.size() > 0){
            min = Collections.min(map.keySet());
            newArray.add(map.get(min));
            map.remove(min);
        }
        this.setCourses(newArray);
    }
}
