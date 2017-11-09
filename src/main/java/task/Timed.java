/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Elia
 */
public class Timed {

    public Timed(int intDATA, int intVALUES){
        
        Timer timerDATA = new Timer();
        Timer timerVALUES = new Timer();
        TimerTask taskDATA = new SchedulerDATA();
        TimerTask taskVALUES = new SchedulerVALUES();

        // aspetta 0 secondi prima dell'esecuzione,poi
        // viene eseguita ogni 2 secondi
        System.out.println("Data");
        timerDATA.schedule(taskDATA, 0, intDATA);
        System.out.println("Values");
        timerVALUES.schedule(taskVALUES, 0, intVALUES);
    }
}
