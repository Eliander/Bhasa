/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import postRequest.UNIVRequest;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Elia
 */
public class SchedulerVALUES extends TimerTask{
    
    UNIVRequest http = new UNIVRequest();
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(SchedulerVALUES.class);

    
    public void run() {
        String[] values = http.getValues();
    }
}
