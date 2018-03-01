/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhasa;

import images.ImageCreator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Timetable;
import postRequest.UNIVRequest;

/**
 *
 * @author Elia
 */
public class Main {
    //soppiantata dal main del bot

    private static Logger log = LogManager.getLogger(Main.class);
    
    private Properties config = new Properties();

    public static void main(String[] args) {
        //24 volte al giorno
        //msec in un giorno: 86400000
        //msecondi in un ora: 3600000
        int intDATA = 3600000;
        int intVALUES = 86400000;
        images.ImageCreator i = new ImageCreator();

        //da rimuovere dopo debug
        UNIVRequest http = new UNIVRequest();
        //end rimozione

        try {
            log.info("Start timer");
            //Timed start = new Timed(intDATA, intVALUES);
            //non serve ora continuare a chiedere
            //da rimuovere dopo debug
            Calendar c = new GregorianCalendar();
            c.set(2018, GregorianCalendar.MARCH, 1);

            Timetable data = http.getData("420", "999%7C2", c);
            //Timetable data = http.getData("385", "715|1", c); // del 4 dicembre non funziona
            //end rimozione
        } catch (Exception e) {
            log.error(e);
        }

    }
}
