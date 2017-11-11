/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsonParser;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import persistence.Courses;

/**
 *
 * @author Elia
 */
public class GsonFormatter {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(GsonFormatter.class);

    public ArrayList<Courses> formatData(String[] data) {
        ArrayList<Courses> courses = null;
        return courses;
    }

    public ArrayList<Courses> formatValues(String[] values) {
        ArrayList<Courses> courses = getCourses(values[1]);
        return courses;

    }

    private ArrayList<Courses> getCourses(String values) {
        ArrayList<Courses> courses = new ArrayList();
        try {
            values = values.replace("}","");
            values = values.replace("{", " ");
            values = values.replace(":", " ");
            values = values.replace("\"", "");
            String[] strCourses = values.split("label");
            for (int i = 2; i < strCourses.length; i++) {
                if (strCourses[i].contains("elenco_anni")) {
                    courses.add(parseCourses(strCourses[i]));
                } else {
                    System.out.println("modulo");
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    private Courses parseCourses(String strCourse){
        String[] strings = strCourse.split(",");
        strings[1] = strings[1].replace("valore", "");
        strings[2] = strings[2].replace("pub_type", "");
        Courses course = new Courses(strings[0], strings[1], strings[2]);
        return course;
    }
}
