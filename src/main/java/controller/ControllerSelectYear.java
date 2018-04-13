/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bhasa.MainBot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import utilities.Utilities;

/**
 *
 * @author Elia
 */
public class ControllerSelectYear extends Controller {

    private static final Logger LOG = LogManager.getLogger(ControllerSelectYear.class);
    private final Utilities utility = new Utilities();

    private String option = null;

    public ControllerSelectYear() {
        super(Utilities.SET_YEAR_COMMAND);
    }

    public ControllerSelectYear(String option) {
        super(Utilities.SET_YEAR_COMMAND);
        this.option = option;
    }
    //to do cancellare tutto quando si arresta il bot
    //to do sistemare ordine di visualizzazione dehli anni
    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        SendMessage message = null;
        if (this.option == null) {
            HashMap<String, String> years = getYears(chatId);
            message = new SendMessage(chatId, "Seleziona sulla tastiera il tuo anno di laurea: ");
            //aggiorno nel db i dati dell'ultimo comando
            message.setReplyMarkup(getKeyboard(years));
            MainBot.dao.getCommandDAO().updateLastCommand(chatId + "", this.command);

        } else {
            //hanno mandato un anno dal percorso principale
            //mi prendo il corso di laurea
            int graduation = MainBot.dao.getGraduationDAO().getGraduation("" + chatId);
            //mi prendo gli anni possibili
            HashMap<String, String> possibleYears = MainBot.years.get(graduation);
            message = new SendMessage(chatId, "error");
            for (String s : possibleYears.keySet()) {
                if (s.contains(option)) {
                    //esiste l'anno selezionato
                    message = new SendMessage(chatId, "Hai settato " + option);
                    MainBot.dao.getGraduationDAO().insertYear("" + chatId, possibleYears.get(s));
                    MainBot.dao.getCommandDAO().clearLastCommand("" + chatId);
                    message.setReplyMarkup(getKeyboardMenu());
                    break;
                }
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

    private HashMap<String, String> getYears(long chatId) {
        HashMap<String, String> years = MainBot.years.get(MainBot.dao.getGraduationDAO().getGraduation(chatId + ""));
        return years;
    }

    private ReplyKeyboardMarkup getKeyboard(HashMap<String, String> years) {
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        TreeSet<String> keySet = new TreeSet(years.keySet());
        for (String value : keySet) {
            row = new KeyboardRow();
            row.add(utility.normalizeForSetYears(value));
            keyboard.add(row);
        }
        // Add the first row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup getKeyboardMenu() {
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add(Utilities.TODAY_COMMAND);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(Utilities.TOMORROW_COMMAND);
        // Add the first row to the keyboard
        keyboard.add(row);
        
        row = new KeyboardRow();
        row.add(Utilities.HOME_COMMAND);
        keyboard.add(row);
        
        row = new KeyboardRow();
        row.add(Utilities.SET_GRADUATION_COMMAND);
        keyboard.add(row);
        
        row = new KeyboardRow();
        row.add(Utilities.SET_YEAR_COMMAND);
        keyboard.add(row);
        
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        return keyboardMarkup;
    }

}
