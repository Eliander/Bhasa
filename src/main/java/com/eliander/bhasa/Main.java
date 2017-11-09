/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eliander.bhasa;

import task.Timed;

/**
 *
 * @author Elia
 */
public class Main {
    //soppiantata dal main del bot
    public static void main(String[] args) throws Exception {
        //24 volte al giorno
        //msec in un giorno: 86400000
        //msecondi in un ora: 3600000
        int intDATA = 3600000;
        int intVALUES = 86400000;
        
        Timed start = new Timed(intDATA, intVALUES);
    }
}
