/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhasa;

import images.ImageCreator;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Courses;
import persistence.Timetable;
import task.Timed;

/**
 *
 * @author Elia
 */
public class Main {
    //soppiantata dal main del bot

    private static Logger log = LogManager.getLogger(Main.class);
    public static final String nameProject = "Bhasa";
    private Properties config = new Properties();

    public static void main(String[] args) {
        //24 volte al giorno
        //msec in un giorno: 86400000
        //msecondi in un ora: 3600000
        int intDATA = 3600000;
        int intVALUES = 86400000;
        images.ImageCreator i = new ImageCreator();
        
        try {
            log.info("Start timer");
            Timed start = new Timed(intDATA, intVALUES);
        } catch (Exception e) {
            log.error(e);
        }

    }
}
