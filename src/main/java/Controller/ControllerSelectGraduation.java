package Controller;

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

    public ControllerSelectGraduation() {
        super("/setGraduation");
    }

    public ControllerSelectGraduation(String option) {
        super("/setGraduation", option);
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message;
        if (this.option == null) {
            message = new SendMessage(chatId, "Scrivi accanto al comando /setGraduation il tuo corso di laurea: " + "\n" + "es: /setGraduation informatica");
        } else {
            HashMap<Integer, String> graduations = getPossibleGraduation("informatica");
            message = new SendMessage(chatId, printGraduation(graduations));
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
        //remove all courses
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

    private String printGraduation(HashMap<Integer, String> values) {
        String result = "";
        for (int i : values.keySet()) {
            result = result + "/" + values.get(i) + "\n";
        }
        return result;
    }

}
