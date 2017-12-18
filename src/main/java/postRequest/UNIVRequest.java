/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.net.ssl.HttpsURLConnection;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Elia
 */
public class UNIVRequest {
    // HTTP POST request: combo_call.php, contains values dictionary

    public static Date dataDate, valuesDate;
    public static StringBuilder data, values;
    private final String[] months = {"gennaio","febbraio","marzo","aprile","maggio","giugno","luglio","agosto","settembre","ottobre","novembre","dicembre"};
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(UNIVRequest.class);

    public String[] getValues() {
        String arr[] = null;
        try {
            String url = "https://logistica.univr.it/aule/Orario/combo_call.php";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            log.info("Send POST request - values");
            con.setRequestMethod("POST");
            String urlParameters = "_=111111";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            log.info("Sending 'POST' request to URL : " + url);
            log.info("Post parameters : " + urlParameters);
            log.info("Response Code : " + responseCode);

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
            arr = str.split("var ");
            return arr;
        } catch (Exception ex) {
            log.error(UNIVRequest.class.getName(), ex);
            return arr;
        }
    }

    // HTTP POST request: grid_call.php, contains data
    public String getData() {
        String arr[] = null;
        try {
            String stringUrl = "https://logistica.univr.it/aule/Orario/grid_call.php";
            URL url = new URL(stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //set type of connection
            connection.setRequestMethod("POST");
            //set body parameters (not a get)
            String urlParameters = "form-type=corso&aa=2017&cdl=420&anno=2017&corso=420&anno2=999%7C2&date=23-10-2017&_lang=it&all_events=0";
            log.info("Send POST request - data");

            // Send post request
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = connection.getResponseCode();
            
            /*to-do deve essere cacheata
                inoltre se qualcuno mi chiede questa particolare laurea - corso ok
                altrimenti non lo chiedo neanche
            */
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
            /*Calendar c1 = GregorianCalendar.getInstance();
            c1.set(2017, Calendar.DECEMBER, 19);
            String findData = findData(str, c1);*/
            String findData = findData(str);
            return findData;
        } catch (Exception ex) {
            log.error(UNIVRequest.class.getName(), ex);
            return "ERROR";
        }
    }
    
    /*
    nella chiamata vengono passati TUTTE le lezioni, da settembre a ine semestre.
    Questo metodo prende la data di oggi e estrae le lezioni del giorno.
    Parsando alla stringa Docenti (o a informazioni_lezione) ottengo tutte le materie del corso e 
    le relative lezioni
    */
    private String findData(String data){
        String result = "";
        Calendar calendar = new GregorianCalendar();
        String month = months[calendar.get(GregorianCalendar.MONTH)];
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        //divido a docenti per trovare le diverse materie
        String[] dataSplit = data.split("Docenti");
        dataSplit[0] = "NULL"; //dati di intestazione
        //elimino tutte le materie che non ho oggi
        for (int i = 1; i < dataSplit.length; i++) {
           int indexOfGiorno = dataSplit[i].indexOf("giorno");
           if(Integer.parseInt(dataSplit[i].charAt(indexOfGiorno + 9) + "")!= calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1){
               dataSplit[i] = "NULL";
           }else{//to do: da rimuovere
               System.out.println("ok");
           }
        }
        for(int i = 0; i < dataSplit.length; i ++){
            //tengo dalla data al <br>
            String substring = "";
            if(!(dataSplit[i].equals("NULL")) && dataSplit[i].contains(day + " " + month)){//di solito fa lezione ma oggi no!
                String[] splitAtDay = dataSplit[i].split(day + " " + month);
                //faccio un po di pulizia per gli indici
                substring = splitAtDay[0].substring(0, splitAtDay[0].indexOf("<br>"));
                substring = substring.replace("contenuto", "$");
                substring = substring.replace("nome_insegnamento", "£");
                substring = substring.replace("codice_aula", "null"); //altrimenti trova il codice aula
                substring = substring.replace("aula", "#");
                substring = substring.replace("orario", "%");
                //salvo gli indici
                int indexOfTeacher = substring.indexOf("$");
                int indexOfCourse = substring.indexOf("£");
                int indexOfLocation = substring.indexOf("#");
                int indexOfTime = substring.indexOf("%");
                //mi estraggo i valori
                String teacher = substring.substring(indexOfTeacher + 4, substring.indexOf(",", indexOfTeacher)- 1);
                String course = substring.substring(indexOfCourse + 4, substring.indexOf(",", indexOfCourse)- 1);
                String location = substring.substring(indexOfLocation + 4, substring.indexOf(",", indexOfLocation)- 1);
                String time = substring.substring(indexOfTime + 4, substring.indexOf(",", indexOfTime)- 1);
                result = result + teacher + " " + course + " " + location + " " + time + "\n";
            }
            
        }
        return result;
    }
    
    //se voglio provare altri giorni
    private String findData(String data, Calendar calendar){
        String result = "";
        String month = months[calendar.get(GregorianCalendar.MONTH)];
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        //divido a docenti per trovare le diverse materie
        String[] dataSplit = data.split("Docenti");
        dataSplit[0] = "NULL"; //dati di intestazione
        //elimino tutte le materie che non ho oggi
        for (int i = 1; i < dataSplit.length; i++) {
           int indexOfGiorno = dataSplit[i].indexOf("giorno");
           if(Integer.parseInt(dataSplit[i].charAt(indexOfGiorno + 9) + "")!= calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1){
               dataSplit[i] = "NULL";
           }else{//to do: da rimuovere
               System.out.println("ok");
           }
        }
        for(int i = 0; i < dataSplit.length; i ++){
            //tengo dalla data al <br>
            String substring = "";
            if(!(dataSplit[i].equals("NULL")) && dataSplit[i].contains(day + " " + month)){//di solito fa lezione ma oggi no!
                String[] splitAtDay = dataSplit[i].split(day + " " + month);
                //faccio un po di pulizia per gli indici
                substring = splitAtDay[0].substring(0, splitAtDay[0].indexOf("<br>"));
                substring = substring.replace("contenuto", "$");
                substring = substring.replace("nome_insegnamento", "£");
                substring = substring.replace("codice_aula", "null"); //altrimenti trova il codice aula
                substring = substring.replace("aula", "#");
                substring = substring.replace("orario", "%");
                //salvo gli indici
                int indexOfTeacher = substring.indexOf("$");
                int indexOfCourse = substring.indexOf("£");
                int indexOfLocation = substring.indexOf("#");
                int indexOfTime = substring.indexOf("%");
                //mi estraggo i valori
                String teacher = substring.substring(indexOfTeacher + 4, substring.indexOf(",", indexOfTeacher)- 1);
                String course = substring.substring(indexOfCourse + 4, substring.indexOf(",", indexOfCourse)- 1);
                String location = substring.substring(indexOfLocation + 4, substring.indexOf(",", indexOfLocation)- 1);
                String time = substring.substring(indexOfTime + 4, substring.indexOf(",", indexOfTime)- 1);
                result = result + teacher + " " + course + " " + location + " " + time + "\n";
            }
            
        }
        return result;
    }

}
