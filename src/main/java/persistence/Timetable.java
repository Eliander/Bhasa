package persistence;

import java.util.ArrayList;
import java.util.*;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class Timetable {
    
    private ArrayList<Lesson> lessons;
    private String graduation;
    
    private final Utilities utility = new Utilities();

    public Timetable() {
       this.lessons = new ArrayList();
    }

    public Timetable(ArrayList<Lesson> lessons, String graduation) {
        this.lessons = lessons;
        this.graduation = graduation;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    public void addlessons(Lesson lessons) {
        ArrayList<Lesson> c = this.getLessons();
        c.add(lessons);
        setLessons(c);
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
        for(Lesson c : lessons){
            result += c.toString();
        }
        return result;
    }    
    
    public void sortTimetable(){
        ArrayList<Lesson> newArray = new ArrayList();
        Map<Float, Lesson> map = new HashMap();
        
        for(int i = 0; i < lessons.size(); i ++){
            map.put(Float.parseFloat(utility.normalizeForCompare(lessons.get(i).getStart())), lessons.get(i));
        }
        float min;
        while(map.size() > 0){
            min = Collections.min(map.keySet());
            newArray.add(map.get(min));
            map.remove(min);
        }
        this.setLessons(newArray);
    }
}
