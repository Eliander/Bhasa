/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eliander.bhasa.postRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Elia
 */
public class UNIVRequest {
    // HTTP POST request: combo_call.php, contains values dictionary

    public static Date dataDate, valuesDate;
    public static StringBuilder data, values;

    public void call() {

        //time control for data
        if (dataDate.equals(null)) {
            dataDate = new Date();
        } else if (dataDate.getTime() - (new Date().getTime()) > (10 * 1000 * 60)) { //10 min
            getData();
        }
        //time control for values
        if (valuesDate.equals(null)) {
            valuesDate = new Date();
        } else if (valuesDate.getTime() - (new Date().getTime()) > (10 * 1000 * 60)) { //10 min
            getValues();
        }
    }

    public void getValues() {

        try {
            String url = "https://logistica.univr.it/aule/Orario/combo_call.php";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            String urlParameters = "_=111111";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            String str = response.toString();
            String arr[] = str.split("var ");
            for (String s : arr) {
                System.out.println(s);
                System.out.println('\n');;
            }
        } catch (Exception ex) {
            Logger.getLogger(UNIVRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // HTTP POST request: grid_call.php, contains data
    public void getData() {

        try {
            String stringUrl = "https://logistica.univr.it/aule/Orario/grid_call.php";
            URL url = new URL(stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //set type of connection
            connection.setRequestMethod("POST");
            //set body parameters (not a get)
            String urlParameters = "form-type=corso&aa=2017&cdl=420&anno=2017&corso=420&anno2=999%7C2&date=23-10-2017&_lang=it&all_events=0";

            // Send post request
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + stringUrl);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print results
            String str = response.toString();
            String arr[] = str.split("var ");
            for (String s : arr) {
                System.out.println(s);
                System.out.println('\n');;
            }
        } catch (Exception ex) {
            Logger.getLogger(UNIVRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
