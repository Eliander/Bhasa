/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import postRequest.UNIVRequest;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Elia
 */
public class SchedulerDATA extends TimerTask{
    
    private static Logger log = LogManager.getLogger(SchedulerDATA.class);

    UNIVRequest http = new UNIVRequest();
    
    public void run() {
        http.getData();
    }
}
