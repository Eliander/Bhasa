package utilities;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author Elia
 */
public class Utilities {
    
    //commands
    public static final String start_command = "/start";
    public static final String set_graduation = "/setGraduation";
    public static final String home = "/home";
    public static final String today = "/today";
    public static final String tomorrow = "/tomorrow";
    //end commands
    private Random random = new Random();
    public final String NAME_PROJECT = "Bhasa";
    public final String[] months = {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"};
    public final String[] colours = {"#FFFCB1","#FFE6BB","#B9F4FF","#F3BAF5","#F1D0D0",
        "#D5FFA4","#FDCBFE","#A0F3A2","#FFE8A4","#FFC6A4","#EEC0C0","#A7C7D3",
        "#80D886","#A6E2FF","#DDFF75","#FFBFCF","#8CB1FF","#FEE080","#DC8080",
        "#FFB480","#A9F580","#A6F9A2","#C6DBE1","#F99ECD","#C5C6FC","#FDC100",
        "#56C878","#E9D6B2","#FFA100","#DBBEC9","#DB7BAE","#FFFF72","#F2CDA5",
        "#CABD00","#A9D8B9","#EBB2BB","#BFCFCF","#59B726","#DE7083","#68BBD9",
        "#FF8147","#87D21E","#44D36F","#CA9D00","#5D94E1","#CB78CE","#00BD09",
        "#4EC300","#F99A9A","#FFFF72","#C175D6","#6BBAFF","#FF8D01","#CAC300",
        "#5AA800","#FF8F2E","#3EC1A7","#FF9C39","#E7698F","#D9AC1A","#3BCA8C",
        "#E06E96","#E9AF5D","#6699CC","#FF99CC","#99CCFF","#DCB02C","#FF7F5B",
        "#29D984","#99FF99","#9999FF","#47BAAD","#FF9FFA","#00C161","#B093FF",
        "#57B3FF","#CAFF7A","#EDE053","#5ECCB1","#5AB8D2","#7A95FF","#FFD83D",
        "#FF6A34","#5AE09B","#E4CA95","#66C5BA","#CC7E7E","#E6AFEE","#8DD7B1",
        "#DE8EB6","#FFA3A3","#FFD1A3","#FFA3FF","#C5FFB1","#FFFCB1","#FFE6BB",
        "#B9F4FF","#F3BAF5","#F1D0D0","#D5FFA4","#FDCBFE","#A0F3A2","#FFE8A4",
        "#FFC6A4","#EEC0C0","#A7C7D3","#80D886","#A6E2FF","#DDFF75","#FFBFCF",
        "#8CB1FF","#FEE080","#DC8080","#FFB480","#A9F580","#A6F9A2","#C6DBE1",
        "#F99ECD","#C5C6FC","#FDC100","#56C878","#E9D6B2","#FFA100","#DBBEC9",
        "#DB7BAE","#FFFF72","#F2CDA5","#CABD00","#A9D8B9","#EBB2BB","#BFCFCF",
        "#59B726","#DE7083","#68BBD9","#FF8147","#87D21E","#44D36F","#CA9D00",
        "#5D94E1","#CB78CE","#00BD09","#4EC300","#F99A9A","#FFFF72","#C175D6",
        "#6BBAFF","#FF8D01","#CAC300","#5AA800","#FF8F2E","#3EC1A7","#FF9C39",
        "#E7698F","#D9AC1A","#3BCA8C","#E06E96","#E9AF5D","#6699CC","#FF99CC",
        "#99CCFF","#DCB02C","#FF7F5B","#29D984","#99FF99","#9999FF","#47BAAD",
        "#FF9FFA","#00C161","#B093FF","#57B3FF","#CAFF7A","#EDE053","#5ECCB1",
        "#5AB8D2","#7A95FF","#FFD83D","#FF6A34"};
    
    //genera un colore casuale
    public Color randomColor() {
        int r, g, b;
        r = random.nextInt(256);
        g = random.nextInt(256);
        b = random.nextInt(256);
        return new Color(r, g, b);
    }
    //genera un colore casuale dalla lista
    public Color randHEXColor(){
        String colorStr = colours[random.nextInt(colours.length)];
        return new Color(
            Integer.valueOf(colorStr.substring(1,3),16),
            Integer.valueOf(colorStr.substring(3,5),16),
            Integer.valueOf(colorStr.substring(5,7),16));
    }

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
    
    public String normalizeForCompare(String s){
        s = s.replace(":", ".");
        return s;
    }

    public String normalizeModule(String s) {
        s.replace("|", "%7C");
        return s;
    }

    public String normalizeDate(Calendar c) {
        String month;
        if ((c.get(GregorianCalendar.MONTH) + 1) < 10){
            month = "0" + (c.get(GregorianCalendar.MONTH) + 1);
        }else{
            month = "" + (c.get(GregorianCalendar.MONTH) + 1);
        }
        return "" + c.get(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + c.get(GregorianCalendar.YEAR);
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

    public String normalizeString(String s) {
        if (!((s.charAt(0) >= 'A' && s.charAt(0) <= 'Z') || (s.charAt(0) >= 'a' && s.charAt(0) <= 'z'))) {
            return normalizeString(new String(s.substring(1)));
        }
        return s;
    }

}
