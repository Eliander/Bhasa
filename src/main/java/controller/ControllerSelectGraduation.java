package controller;

import bhasa.MainBot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
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
    private static Utilities utility = new Utilities();

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
        SendMessage message = null;
        if (this.option == null) {
            message = new SendMessage(chatId, "Scrivi il tuo corso di laurea: " + "\n" + "es: informatica");
            //aggiorno nel db i dati dell'ultimo comando
            MainBot.dao.getCommandDAO().updateLastCommand(chatId + "", this.command);
        } else {
            HashMap<String, Integer> graduations = getPossibleGraduation(option.toLowerCase());
            message = new SendMessage(chatId, printGraduation(graduations, chatId));
            if (graduations.size() == 1) {
                message.setReplyMarkup(getKeyboard(getYears(chatId)));
            }
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

    private HashMap<String, Integer> getPossibleGraduation(String grad) {
        HashMap<String, Integer> result = new HashMap();
        for (String graduation : MainBot.graduations.keySet()) {
            if (graduation.contains(" " + grad)) {
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
            //controllo se l'utente era presente nel db
            if (MainBot.dao.getGraduationDAO().existUser(chatId + "")) {
                MainBot.dao.getGraduationDAO().updateUser(chatId + "", valueOfGraduation + "");
            } else {
                MainBot.dao.getGraduationDAO().insertUser(chatId + "", valueOfGraduation + "");
            }
        } else {
            result = "Esistono più corsi che contengono quel nome... Cerca di essere più preciso!" + "\n";
            for (String i : values.keySet()) {
                result = result + i + "\n";
            }
        }
        return result;
    }

    public HashMap<String, String> getYears(long chatId) {
        HashMap<String, String> years = MainBot.years.get(MainBot.dao.getGraduationDAO().getGraduation(chatId + ""));
        return years;
    }

    public ReplyKeyboardMarkup getKeyboard(HashMap<String, String> years) {
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList();
        // Create a keyboard row
        KeyboardRow row = null;
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        TreeSet<String> keySet = new TreeSet(years.keySet());
        for (String value : keySet) {
            row = new KeyboardRow();
            row.add(utility.normalizeForSetYears(value));
            keyboard.add(row);
        }
        // Add the first row to the keyboard
        //keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        return keyboardMarkup;
    }
    //to do preparare per il set anno accademico
}
