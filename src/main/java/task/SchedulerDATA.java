/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import postRequest.UNIVRequest;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Timetable;

/**
 *
 * @author Elia
 */
public class SchedulerDATA extends TimerTask{
    
    private static Logger log = LogManager.getLogger(SchedulerDATA.class);

    UNIVRequest http = new UNIVRequest();
    
    public void run() {
        //to-do deve prendere in input 2 stringhe, il corso e il modulo
        Calendar c = new GregorianCalendar();
        c.set(2017, GregorianCalendar.DECEMBER,13);
        
        Timetable data = http.getData("360", "725|1", c);
    }
}
