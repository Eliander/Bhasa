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
        HashSet<Courses> courses = getCourses(values[1]);
        return courses;
        //return getCourses(values[1]);

    }

    //data la stringa di tutti i valori restituisce un arraylist di Courses
    private HashSet<Courses> getCourses(String values) {
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
        for(int i = 1; i < split.length; i ++){
            split[i] = split[i].replace("valore", "");
            //splitto per ottenere i campi
            String[] splitModule = split[i].split(",");
            Modules m = new Modules(splitModule[0], splitModule[1]);
            course.addModule(m);
        }
        return course;
    }

    private ArrayList<Teacher> getTeacher(String values){

        return null;
    }
}
