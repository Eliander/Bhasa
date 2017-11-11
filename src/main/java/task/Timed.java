/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Elia
 */
public class Timed {
    
        private static Logger log = LogManager.getLogger(Timed.class);


    public Timed(int intDATA, int intVALUES){
        
        Timer timerDATA = new Timer();
        Timer timerVALUES = new Timer();
        TimerTask taskDATA = new SchedulerDATA();
        TimerTask taskVALUES = new SchedulerVALUES();

        // aspetta 0 secondi prima dell'esecuzione,poi
        // viene eseguita ogni 2 secondi
        timerDATA.schedule(taskDATA, 0, intDATA);
        timerVALUES.schedule(taskVALUES, 0, intVALUES);
    }
}
