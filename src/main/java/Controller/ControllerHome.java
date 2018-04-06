/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import bhasa.MainBot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author Elia
 */
public class ControllerHome extends Controller {

    private static final Logger log = LogManager.getLogger(ControllerHome.class);

    public ControllerHome() {
        super("/home");
    }

    @Override
    public boolean send(long chatId, DefaultAbsSender bot) {
        MainBot.dao.getCommandDAO().clearLastCommand(chatId + "");
        SendMessage message = new SendMessage(chatId, "Menù");
        message.setReplyMarkup(getKeyboard());
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
        return "Menù";
    }

    private ReplyKeyboardMarkup getKeyboard() {
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("/setGraduation");
        row.add("/todayTime");
        row.add("/tomorrowTime");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        return keyboardMarkup;
    }
}
