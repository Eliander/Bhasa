package persistence;

import java.util.ArrayList;
import java.util.*;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class Timetable {
    
    private ArrayList<Lesson> courses;
    private String graduation;
    
    private final Utilities utility = new Utilities();

    public Timetable() {
       this.courses = new ArrayList();
    }

    public Timetable(ArrayList<Lesson> courses, String graduation) {
        this.courses = courses;
        this.graduation = graduation;
    }

    public ArrayList<Lesson> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Lesson> courses) {
        this.courses = courses;
    }
    
    public void addCourses(Lesson courses) {
        ArrayList<Lesson> c = this.getCourses();
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
        for(Lesson c : courses){
            result += c.toString();
        }
        return result;
    }    
    
    public void sortTimetable(){
        ArrayList<Lesson> newArray = new ArrayList();
        Map<Float, Lesson> map = new HashMap();
        
        for(int i = 0; i < courses.size(); i ++){
            map.put(Float.parseFloat(utility.normalizeForCompare(courses.get(i).getStart())), courses.get(i));
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
