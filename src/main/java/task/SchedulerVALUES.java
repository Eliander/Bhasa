/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import com.eliander.bhasa.postRequest.UNIVRequest;
import java.util.TimerTask;

/**
 *
 * @author Elia
 */
public class SchedulerVALUES extends TimerTask{
    UNIVRequest http = new UNIVRequest();
    
    public void run() {
        System.out.println("POST request");
        http.getValues();
    }
}
