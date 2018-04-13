package controller;

import bhasa.MainBot;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import postRequest.UNIVRequest;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class ControllerSelectGraduation extends Controller {

    private static final Logger LOG = LogManager.getLogger(ControllerSelectGraduation.class);
    private static final UNIVRequest REQUEST = new UNIVRequest();

    private String option = null;

    public ControllerSelectGraduation() {
        super(Utilities.SET_GRADUATION_COMMAND);
    }

    public ControllerSelectGraduation(String option) {
        super(Utilities.SET_GRADUATION_COMMAND);
        this.option = option;
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message;
        if (this.option == null) {
            message = new SendMessage(chatId, "Scrivi il tuo corso di laurea: " + "\n" + "es: informatica");
            //aggiorno nel db i dati dell'ultimo comando
            MainBot.dao.getCommandDAO().updateLastCommand(chatId + "", this.command);
        } else {
            HashMap<String, Integer> graduations = getPossibleGraduation(option);
            message = new SendMessage(chatId, printGraduation(graduations, chatId));
        }
        try {
            bot.execute(message);
            return true;
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected String setText() {
        return "ok";
    }
    
    private HashMap<String, Integer> getPossibleGraduation(String grad){
        HashMap<String, Integer> result = new HashMap();
        for(String graduation : MainBot.graduations.keySet()){
            if(graduation.contains(" " + grad)){
                result.put(graduation, MainBot.graduations.get(graduation));
            }
        }
        return result;
    }

    private String printGraduation(HashMap<String, Integer> values, long chatId) {
        String result = "";
        if (values.isEmpty()) {
            result = "Spiacente, non è stato trovato nessun corso di laurea con quel nome. Sicuro di aver scritto giusto?";
        } else if (values.size() == 1) {
            int valueOfGraduation = 0;
            for (String i : values.keySet()) {
                result = "Ottimo, hai settato " + i + ". Ora seleziona dalla tastiera il tuo anno di laurea: ";
                valueOfGraduation = values.get(i);
            }
            MainBot.dao.getCommandDAO().updateLastCommand(chatId + "", utilities.Utilities.SET_YEAR_COMMAND);
            
            MainBot.dao.getGraduationDAO().insertUser(chatId + "", valueOfGraduation + "");
        } else {
            result = "Esistono più corsi che contengono quel nome... Cerca di essere più preciso!" + "\n";
            for (String i : values.keySet()) {
                result = result + i + "\n";
            }
        }
        return result;
    }
    
    //to do preparare per il set anno accademico

}
