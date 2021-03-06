package postRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import images.ImageCreator;
import utilities.Utilities;
import org.apache.logging.log4j.LogManager;
import persistence.Lesson;
import persistence.Timetable;

/**
 *
 * @author Elia
 */
public class UNIVRequest {
    // HTTP POST request: combo_call.php, contains values dictionary

    public static StringBuilder data, values;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(UNIVRequest.class);
    private final Utilities utility = new Utilities();
    private final String urlData = "https://logistica.univr.it/aule/Orario/grid_call.php";

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
    public Timetable getData(String graduation, String year, Calendar c) {
        String arr[] = null;
        year = utility.normalizeModule(year);
        try {
            URL url = new URL(urlData);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //set type of connection
            connection.setRequestMethod("POST");
            //set body parameters (not a get)

            //Old params: dont remove. they are son of devil, they could change this every day
            //String urlParameters = "form-type=corso&aa=2017&cdl="+ lesson + "&anno=2018&corso=" + lesson + "&anno2=" + module + "&date=" + utility.normalizeDate(c) + "&_lang=it&all_events=0";
            String urlParameters = "form-type=corso&anno=2017&corso=" + graduation + "&anno2=" + year + "&date=" + utility.normalizeDate(c) + "&_lang=it&all_events=0";
            //String urlParameters = "form-type=corso&anno=2017&corso=420&anno2=999%7C2&date=28-02-2018&_lang=it&all_events=0";
            log.info("Send POST request - data");

            // Send post request
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeUTF(urlParameters);
            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = connection.getResponseCode();

            /*to-do deve essere cacheata
                inoltre se qualcuno mi chiede questa particolare laurea - corso ok
                altrimenti non lo chiedo neanche
             */
            System.out.println("\nSending 'POST' request to URL : " + urlData);
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
            Timetable timetable = find(str, c);
            System.out.println(timetable);

            timetable.sortTimetable();

            /*ImageCreator i = new ImageCreator();
            i.drawImage(timetable);*/
            return timetable;
        } catch (Exception ex) {
            log.error(UNIVRequest.class.getName(), ex);
            return null;
        }
    }

    /*
    nella chiamata vengono passati TUTTE le lezioni, da settembre a fine semestre.
    Questo metodo prende la data di oggi e estrae le lezioni del giorno.
    Parsando alla stringa celle ottengo tutte le materie del corso e 
    le relative lezioni
     */
    public Timetable find(String data, Calendar calendar) {

        Timetable timetable = new Timetable();

        Map<Object, Object> map = new HashMap<Object, Object>(new Gson().fromJson(data, Map.class));
        ArrayList<LinkedTreeMap> array = new ArrayList<LinkedTreeMap>((ArrayList) map.get("celle"));

        int getDay;
        String month = utility.MONTHS[calendar.get(GregorianCalendar.MONTH)];

        for (LinkedTreeMap lesson : array) {
            //prima di creare il corso fai il controllo sul giorno> se e lo stesso giorno in cui puo esserci lezione
            try {
                getDay = Integer.parseInt((String) lesson.get("giorno"));

                //if (getDay == affectedDay) { necessario per le lezioni fuori orario
                /*
                    * informazioni_lezione:{
                            contenuto:{
                                    8:[
                                        {0}
                                        {1:{
                                            contenuto: dates
                                            }
                                        }
                                        ...
                    *
                 */
                //controllo se la data di calendar e segnata come lezione> oggi potrebbe esserci lezione, controlla se e presente nel calendario
                LinkedTreeMap info = (LinkedTreeMap) lesson.get("informazioni_lezione");
                if (info!=null) {
                    LinkedTreeMap content = (LinkedTreeMap) info.get("contenuto");
                    ArrayList<LinkedTreeMap> dates = (ArrayList<LinkedTreeMap>) content.get("8");
                    ArrayList<LinkedTreeMap> graduation = (ArrayList<LinkedTreeMap>) content.get("7");

                    String d = (String) dates.get(1).get("contenuto");

                    if (d.contains(" " + calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + month)) {
                        String soughtDay = (calendar.get(GregorianCalendar.DAY_OF_MONTH) + " " + month + " " + calendar.get(GregorianCalendar.YEAR)); //rischio di avere lezioni fuori orario di una durata diversa dalle solite
                        Lesson c = new Lesson();
                        //alcune lezioni non hanno docenti/aule assegnate
                        if (!(lesson.get("titolo_lezione").equals(""))) {
                            c.setLabel(utility.normalizeString((String) (lesson.get("titolo_lezione"))));
                        } else {
                            c.setLabel("");
                        }
                        if (!(lesson.get("docente").equals(""))) {
                            c.setTeacher(utility.normalizeString((String) (lesson.get("docente"))));
                        } else {
                            c.setTeacher("");
                        }
                        if (!(lesson.get("aula").equals(""))) {
                            c.setClassroom(utility.normalizeString((String) (lesson.get("aula"))));
                        } else {
                            c.setClassroom("");
                        }
                        String code = (String) lesson.get("codice_insegnamento");
                        c.setlessonCode(code);

                        //String time = (String)(lesson.get("orario"));
                        int index = d.indexOf(soughtDay);
                        String substringTime = d.substring(index + soughtDay.length(), index + soughtDay.length() + 14);
                        String[] normalizedTime = utility.normalizeTime(substringTime);
                        c.setStart(normalizedTime[0]);
                        c.setEnd(normalizedTime[1]);
                        timetable.addlessons(c);

                        String grade = (String) graduation.get(0).get("contenuto");
                        int start = grade.indexOf("[");
                        int end = grade.indexOf("]");
                        timetable.setGraduation(grade.substring(start + 1, end));
                    }
                }
            } catch (Exception e) {
                log.error(e);
                return null;
            }
        }
        return timetable;
    }

    public String getGraduation(String values) {
        values.replace("[", "$");
        values.replace("[", "$");
        values = values.replaceAll("\\[.*?\\]", "");
        return values;
    }

}
