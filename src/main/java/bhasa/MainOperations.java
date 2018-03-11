package bhasa;

import DAO.DAOSettings;
import images.ImageCreator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.Timetable;
import postRequest.UNIVRequest;

/**
 *
 * @author Elia
 */
public class MainOperations {
    //soppiantata dal main del bot

    private static Logger log = LogManager.getLogger(MainOperations.class);
    
    private Properties config = new Properties();
    
    private static DAOSettings dao = DAOSettings.DAO; 

    public static void main(String[] args) {
        //24 volte al giorno
        //msec in un giorno: 86400000
        //msecondi in un ora: 3600000
        int intDATA = 3600000;
        int intVALUES = 86400000;
        images.ImageCreator i = new ImageCreator();

        //da rimuovere dopo debug
        UNIVRequest http = new UNIVRequest();
        //end rimozione

        int graduation = dao.getGraduationDAO().getGraduation("103775908");
        String telegramID = dao.getImageDAO().getTelegramCode(graduation);
        
        try {
            log.info("Start timer");
            //Timed start = new Timed(intDATA, intVALUES);
            //non serve ora continuare a chiedere
            //da rimuovere dopo debug
            Calendar c = new GregorianCalendar();
            c.set(2018, GregorianCalendar.MARCH, 1);

            http.getValues();
            Timetable data = http.getData(graduation + "", "999%7C2", c);
            //Timetable data = http.getData("385", "715|1", c); // del 4 dicembre non funziona
            //end rimozione
        } catch (Exception e) {
            log.error(e);
        }

    }
}
