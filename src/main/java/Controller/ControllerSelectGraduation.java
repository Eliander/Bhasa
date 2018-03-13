package Controller;

import bhasa.MainBot;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import postRequest.UNIVRequest;

/**
 *
 * @author Elia
 */
public class ControllerSelectGraduation extends Controller {

    private static final Logger log = LogManager.getLogger(ControllerSelectGraduation.class);
    private static final UNIVRequest request = new UNIVRequest();

    private String option = null;

    public ControllerSelectGraduation() {
        super("/setGraduation");
    }

    public ControllerSelectGraduation(String option) {
        super("/setGraduation");
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
            HashMap<Integer, String> graduations = getPossibleGraduation(" " + option);
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

    //TODO: renderla una hashmap statica da caricare all'inizio e a cui accedere. Creare le strutture coi corsi e salvare gli ID
    private HashMap<Integer, String> getPossibleGraduation(String grad) {
        String value = request.getValues()[1];
        //remove all lessons
        value = value.replace("[", "$");
        value = value.replace("[", "$");
        value = value.replaceAll("\\[.*?\\]", "");
        value = value.replaceAll("\"", "");
        value = value.replaceAll(":", "");
        String[] split = value.split("label");
        //extract graduations
        HashMap<Integer, String> graduations = new HashMap();

        for (String s : split) {
            if ((s.contains("Laurea")) && s.contains(grad)) {
                graduations.put(Integer.parseInt(s.split("valore")[1].substring(0, 3)), s.split(",")[0]);
            }
        }
        return graduations;
    }

    private String printGraduation(HashMap<Integer, String> values, long chatId) {
        String result = "";
        if (values.isEmpty()) {
            result = "Spiacente, non è stato trovato nessun corso di laurea con quel nome. Sicuro di aver scritto giusto?";
        } else if (values.size() == 1) {
            for (int i : values.keySet()) {
                result = "Ottimo, hai settato " + values.get(i);
            }
            MainBot.dao.getCommandDAO().clearLastCommand(chatId + "");
        } else {
            result = "Esistono più corsi che contengono quel nome... Cerca di essere più preciso!" + "\n";
            for (int i : values.keySet()) {
                result = result + values.get(i) + "\n";
            }
        }
        return result;
    }

}
