/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsonParser;

import java.util.ArrayList;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import persistence.Courses;
import persistence.Location;
import persistence.Modules;
import persistence.Teacher;

/**
 *
 * @author Elia
 */
public class GsonFormatter {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(GsonFormatter.class);

    public HashSet<Courses> formatData(String[] data) {
        HashSet<Courses> courses = null;
        return courses;
    }

    public HashSet<Courses> formatValues(String[] values) {
        HashSet<Courses> coursesAndModues = popolateCourses(values[1]);
        HashSet<Teacher> teachers = getTeacher(values[3]);
        System.out.println("$$$$$$");
        for(Teacher th : teachers){
            System.out.println(th.getName() + th.getValore());
        }
        
        System.out.println("$$$$$$");
        return coursesAndModues;
        //return getCourses(values[1]);

    }

    //data la stringa di tutti i valori restituisce un arraylist di Courses
    private HashSet<Courses> popolateCourses(String values) {
        HashSet<Courses> courses = new HashSet();
        String modules = "";
        try {
            values = values.replace("}", "");
            values = values.replace("{", " ");
            values = values.replace(":", " ");
            values = values.replace("\"", "");
            String[] strCourses = values.split("label");
            int i;
            for (i = 2; i < strCourses.length - 1;) {
                if (strCourses[i].contains("elenco_anni")) {
                    Courses course = parseCourses(strCourses[i]);
                    i++;
                    while (!strCourses[i].contains("elenco_anni") && (i < strCourses.length - 1)) {//se i successivi non hanno elenco_anni sono moduli
                        modules = modules + "$" + strCourses[i]; //creo una stringa
                        i++;
                    }
                    course = addModules(course, modules);//aggiungo i moduli
                    courses.add(course);
                    modules = "";
                } else {
                    log.error("ERROR, I haven't a course");
                }
            }
            log.info("All courses successfully created");
        } catch (Exception e) {
            log.error(e);
        }
        return courses;
    }

    //data una stringa restituisce un corso - usato da getCourses
    private Courses parseCourses(String strCourse) {
        String[] strings = strCourse.split(",");
        strings[1] = strings[1].replace("valore", "");
        strings[2] = strings[2].replace("pub_type", "");
        //normalize first space
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].charAt(0) == ' ') {
                strings[i] = strings[i].substring(1);
            }
        }
        Courses course = new Courses(strings[0], strings[1], strings[2]);
        return course;
    }

    //dato un corso e una stringa contenente i moduli, restituisce un corso
    //con tutti i moduli assegnati - usato da getCourses
    private Courses addModules(Courses course, String modules) {
        //tolgo caratteri sporchi
        modules = modules.replace("}", "");
        modules = modules.replace("{", " ");
        modules = modules.replace(":", " ");
        modules = modules.replace("\"", "");
        modules = modules.replace("[", " ");
        modules = modules.replace("]", "");
        //ottendo i diversi moduli
        String[] split = modules.split("\\$");
        for (int i = 1; i < split.length; i++) {
            split[i] = split[i].replace("valore", "");
            //splitto per ottenere i campi
            String[] splitModule = split[i].split(",");
            Modules m = new Modules(splitModule[0], splitModule[1]);
            course.addModule(m);
        }
        return course;
    }

    private HashSet<Teacher> getTeacher(String values) {
        HashSet<Teacher> teachers = new HashSet();
        try {
            values = values.replace("}", "");
            values = values.replace("{", " ");
            values = values.replace(":", " ");
            values = values.replace("\"", "");
            values = values.replace(",valore", "$");
            values = values.replace("\\\\", "\\");
            String[] strTeachers = values.split("label");
            for (int i = 2; i < strTeachers.length; i++) {
                teachers.add(parseTeacher(strTeachers[i]));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return teachers;
    }

    private Teacher parseTeacher(String strTeacher) {
        /*if(strTeacher.contains("\\")){
            int index = strTeacher.indexOf("\\");
            String specialChar = strTeacher.substring(index, index + 6);
            char toInsert = (char) Integer.parseInt(specialChar.substring(1), 16 );
            strTeacher = new StringBuilder(strTeacher).insert(index, toInsert).toString();
        }*/
        String[] parse = strTeacher.split("\\$");
        //tolgo lo spazio iniziale e la virgola del corso
        
        return new Teacher(parse[0].substring(1), parse[1].substring(1).replace(",", ""));
    }

    private HashSet<Location> getLocation(String values) {
        //12
        return null;
    }

}
