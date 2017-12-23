/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package normalizer;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Elia
 */
public class Normalizer {

    //se un ora viene prima di un'altra
    public int firstOf(String s1, String s2) {
        Date d1 = dateFromString(s1);
        Date d2 = dateFromString(s2);
        return d1.compareTo(d2);
    }

    //normalizzatore orario
    public String[] normalizeTime(String s) {
        s = s.replace(" ", "");
        s = s.replace(".", ":");
        String[] split = s.split("-");
        return split;
    }

    public String normalizeModule(String s) {
        s.replace("|", "%7C");
        return s;
    }

    public String normalizeDate(Calendar c) {
        return "" + c.get(GregorianCalendar.DAY_OF_MONTH) + "-" + c.get(GregorianCalendar.MONTH) + "-" + c.get(GregorianCalendar.YEAR);
    }

    public Date dateFromString(String s) {
        String[] normalizeTime = normalizeTime(s);
        int hours = Integer.parseInt(normalizeTime[0].split(":")[0]);
        int minutes = Integer.parseInt(normalizeTime[0].split(":")[1]);
        Date date = new Date();
        date.setHours(hours);
        date.setMinutes(minutes);
        return date;
    }

    public String normalizeString(String s){
        if(!((s.charAt(0)>='A' && s.charAt(0)<='Z') || (s.charAt(0)>='a' && s.charAt(0)<='z'))){
            return normalizeString(new String(s.substring(1)));
        }
        return s;
    }
    
    
}
